package com.example.fabriscan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private ImageView profileImage;
    private ImageView cameraIcon;
    private ImageView editNameIcon;
    private TextView userName;
    private Button logoutButton;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize views
        profileImage = view.findViewById(R.id.profileImage);
        cameraIcon = view.findViewById(R.id.cameraIcon);
        editNameIcon = view.findViewById(R.id.editNameIcon);
        userName = view.findViewById(R.id.userName);
        logoutButton = view.findViewById(R.id.logoutButton);
        mAuth = FirebaseAuth.getInstance();

        // Set up click listeners
        setupClickListeners();

        return view;
    }

    private void setupClickListeners() {
        // Logout button click listener
        logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();
        });

        // Camera icon click listener for changing profile picture
        cameraIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement profile picture change functionality
                Toast.makeText(getContext(), "Change profile picture", Toast.LENGTH_SHORT).show();
            }
        });

        // Edit name icon click listener
        editNameIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement name editing functionality
                Toast.makeText(getContext(), "Edit name", Toast.LENGTH_SHORT).show();
            }
        });

        // Profile image click listener
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement profile picture change functionality
                Toast.makeText(getContext(), "Change profile picture", Toast.LENGTH_SHORT).show();
            }
        });
    }
}