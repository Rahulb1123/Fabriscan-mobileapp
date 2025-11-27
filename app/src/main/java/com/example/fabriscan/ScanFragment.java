package com.example.fabriscan;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import org.tensorflow.lite.Interpreter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class ScanFragment extends Fragment {

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (tflite != null) {
            tflite.close();
        }
    }


    private static final int CAMERA_PERMISSION_REQUEST = 100;
    private static final int STORAGE_PERMISSION_REQUEST = 101;
    private static final int CAMERA_REQUEST = 102;
    private static final int GALLERY_REQUEST = 103;

    private ImageView previewImageView;
    private ImageView placeholderIcon;
    private TextView placeholderText;
    private String currentPhotoPath;
    private Bitmap selectedBitmap;
    private Uri selectedImageUri;
    private Interpreter tflite;
    private List<String> labels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan, container, false);

        // Initialize views
        Button captureImageBtn = view.findViewById(R.id.captureImageBtn);
        Button selectGalleryBtn = view.findViewById(R.id.selectGalleryBtn);
        Button submitBtn = view.findViewById(R.id.submitBtn);
        previewImageView = view.findViewById(R.id.previewImageView);
        placeholderIcon = view.findViewById(R.id.placeholderIcon);
        placeholderText = view.findViewById(R.id.placeholderText);

        // Set click listeners
        captureImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCameraPermissionAndCapture();
            }
        });

        selectGalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkStoragePermissionAndSelectGallery();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedBitmap != null) {
                    classifyImage(selectedBitmap);
                } else {
                    Toast.makeText(getContext(), "Please select or capture an image first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        
        try {
            tflite = new Interpreter(loadModelFile());
            labels = loadLabels();
        } catch (IOException e) {
            Log.e("ScanFragment", "Error loading TFLite model or labels", e);
            Toast.makeText(getContext(), "Model or labels failed to load.", Toast.LENGTH_LONG).show();
        }

        return view;
    }

    private void checkCameraPermissionAndCapture() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) 
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), 
                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
        } else {
            openCamera();
        }
    }

    private void checkStoragePermissionAndSelectGallery() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) 
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), 
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST);
        } else {
            openGallery();
        }
    }

    private void openCamera() {
        Log.d("ScanFragment", "Opening camera...");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
                Log.d("ScanFragment", "Created photo file: " + photoFile.getAbsolutePath());
            } catch (IOException ex) {
                Log.e("ScanFragment", "Error creating image file", ex);
                Toast.makeText(getContext(), "Error creating image file: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
            if (photoFile != null) {
                try {
                    Uri photoURI = FileProvider.getUriForFile(requireContext(),
                            "com.example.fabriscan.fileprovider", photoFile);
                    Log.d("ScanFragment", "Photo URI: " + photoURI);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                } catch (Exception e) {
                    Log.e("ScanFragment", "Error launching camera", e);
                    Toast.makeText(getContext(), "Error launching camera: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Log.e("ScanFragment", "No camera app found on device");
            Toast.makeText(getContext(), "No camera app found on device", Toast.LENGTH_SHORT).show();
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST);
    }

    private File createImageFile() throws IOException {
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = requireContext().getExternalFilesDir(null);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(getContext(), "Camera permission is required to capture images", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == STORAGE_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(getContext(), "Storage permission is required to select images", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("ScanFragment", "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);
        
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                // Handle camera capture
                Log.d("ScanFragment", "Camera result OK, currentPhotoPath: " + currentPhotoPath);
                try {
                    File photoFile = new File(currentPhotoPath);
                    Log.d("ScanFragment", "Photo file exists: " + photoFile.exists() + ", size: " + photoFile.length());
                    if (photoFile.exists()) {
                        selectedImageUri = FileProvider.getUriForFile(requireContext(),
                                "com.example.fabriscan.fileprovider", photoFile);
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), 
                                Uri.fromFile(photoFile));
                        displayImage(bitmap);
                        Toast.makeText(getContext(), "Image captured successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Error: Image file not found at " + currentPhotoPath, Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    Log.e("ScanFragment", "Error loading captured image", e);
                    Toast.makeText(getContext(), "Error loading captured image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == GALLERY_REQUEST && data != null) {
                // Handle gallery selection
                Log.d("ScanFragment", "Gallery result OK, data: " + data.getData());
                try {
                    selectedImageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), selectedImageUri);
                    displayImage(bitmap);
                    Toast.makeText(getContext(), "Image selected successfully!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Log.e("ScanFragment", "Error loading selected image", e);
                    Toast.makeText(getContext(), "Error loading selected image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.d("ScanFragment", "Operation cancelled");
            Toast.makeText(getContext(), "Operation cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private void classifyImage(Bitmap bitmap) {
        if (tflite == null || labels == null) {
            Toast.makeText(getContext(), "Model or labels not loaded", Toast.LENGTH_SHORT).show();
            return;
        }

        // Resize the bitmap to the model's input size (224x224)
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);

        // Allocate a ByteBuffer for the input
        ByteBuffer inputBuffer = ByteBuffer.allocateDirect(1 * 224 * 224 * 3 * 4); // 1 image, 224x224, 3 channels (RGB), 4 bytes per float
        inputBuffer.order(ByteOrder.nativeOrder());
        inputBuffer.rewind();

        int[] intValues = new int[224 * 224];
        resizedBitmap.getPixels(intValues, 0, resizedBitmap.getWidth(), 0, 0, resizedBitmap.getWidth(), resizedBitmap.getHeight());

        // Convert the image to a float array and normalize it
        for (int pixelValue : intValues) {
            inputBuffer.putFloat(((pixelValue >> 16) & 0xFF) / 255.0f);
            inputBuffer.putFloat(((pixelValue >> 8) & 0xFF) / 255.0f);
            inputBuffer.putFloat((pixelValue & 0xFF) / 255.0f);
        }

        // Allocate a ByteBuffer for the output
        float[][] output = new float[1][labels.size()];

        // Run inference
        tflite.run(inputBuffer, output);

        // Get results
        float maxConfidence = 0;
        int maxPos = -1;
        for (int i = 0; i < labels.size(); i++) {
            if (output[0][i] > maxConfidence) {
                maxConfidence = output[0][i];
                maxPos = i;
            }
        }

        if (maxPos != -1) {
            String detectedClass = labels.get(maxPos);

            // Pass results to ResultActivity
            Intent intent = new Intent(getActivity(), ResultActivity.class);
            intent.putExtra("RESULT_CLASS", detectedClass);
            intent.putExtra("RESULT_CONFIDENCE", maxConfidence);
            if (selectedImageUri != null) {
                intent.putExtra("IMAGE_URI", selectedImageUri.toString());
            }
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Classification failed.", Toast.LENGTH_SHORT).show();
        }
    }

    private MappedByteBuffer loadModelFile() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(requireContext().getAssets().openFd("fabric_classifier_model.tflite").getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = requireContext().getAssets().openFd("fabric_classifier_model.tflite").getStartOffset();
        long declaredLength = requireContext().getAssets().openFd("fabric_classifier_model.tflite").getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private List<String> loadLabels() throws IOException {
        List<String> labelList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(requireContext().getAssets().open("class_indices.txt")));
        String line;
        while ((line = reader.readLine()) != null) {
            labelList.add(line);
        }
        reader.close();
        return labelList;
    }

    private void displayImage(Bitmap bitmap) {
        selectedBitmap = bitmap;
        previewImageView.setImageBitmap(bitmap);
        previewImageView.setVisibility(View.VISIBLE);
        placeholderIcon.setVisibility(View.GONE);
        placeholderText.setVisibility(View.GONE);
    }
} 