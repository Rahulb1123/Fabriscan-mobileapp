package com.example.fabriscan;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.net.Uri;
import java.text.DecimalFormat;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private ImageView scannedImageView;
    private ImageView backButton;
    private TextView detectedFabricTextView;
    private TextView confidenceTextView;
    private ProgressBar confidenceProgressBar;
    private TextView descriptionTextView;
    private TextView recommendationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Set status bar to transparent to extend gradient beyond
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(android.graphics.Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
        }

        // Initialize views
        scannedImageView = findViewById(R.id.scannedImageView);
        backButton = findViewById(R.id.backButton);
        detectedFabricTextView = findViewById(R.id.detectedFabricTextView);
        confidenceTextView = findViewById(R.id.confidenceTextView);
        confidenceProgressBar = findViewById(R.id.confidenceProgressBar);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        recommendationTextView = findViewById(R.id.recommendationTextView);

        // Get data from Intent
        String resultClass = getIntent().getStringExtra("RESULT_CLASS");
        float confidence = getIntent().getFloatExtra("RESULT_CONFIDENCE", 0);
        String imageUriString = getIntent().getStringExtra("IMAGE_URI");

        // Format and display results
        if (resultClass != null) {
            String[] parts = resultClass.split(":");
            String fabricName = parts.length > 1 ? parts[1] : resultClass;

            detectedFabricTextView.setText("Detected Fabric Type: " + fabricName);
            descriptionTextView.setText(FabricDetails.getDescription(fabricName));
            recommendationTextView.setText(FabricDetails.getRecommendations(fabricName));
        }

        DecimalFormat df = new DecimalFormat("#.##%");
        confidenceTextView.setText("Confidence: " + df.format(confidence));

        // Set progress bar
        confidenceProgressBar.setProgress((int) (confidence * 100));

        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);
            scannedImageView.setImageURI(imageUri);
        }

        // Setup back button click listener
        backButton.setOnClickListener(v -> onBackPressed());
    }
}
