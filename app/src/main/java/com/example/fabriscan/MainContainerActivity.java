package com.example.fabriscan;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_container_activity);

        // Set status bar color to match activity background (white) and use dark icons
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(android.graphics.Color.WHITE);
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        // Setup footer navigation
        setupFooterNavigation();
        
        // Setup chatbot floating action button
        setupChatbotFab();
        
        // Start with home fragment by default
        loadFragment(new HomeFragment(), "home");
    }

    private void setupFooterNavigation() {
        // Initialize navigation icons
        ImageView navHome = findViewById(R.id.navHome);
        ImageView navScan = findViewById(R.id.navScan);
        ImageView navSearch = findViewById(R.id.navSearch);
        ImageView navProfile = findViewById(R.id.navProfile);

        // Set click listeners for navigation
        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new HomeFragment(), "home");
            }
        });

        navScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ScanFragment(), "scan");
            }
        });

        navSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new CategoryFragment(), "search");
            }
        });

        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ProfileFragment(), "profile");
            }
        });
    }

    private void loadFragment(Fragment fragment, String activeTab) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        
        // Replace the content container with the new fragment
        transaction.replace(R.id.contentContainer, fragment);
        transaction.commit();
        
        // Update the active tab indicator
        setActiveTab(activeTab);
        
        // Set status bar color based on fragment type
        if (fragment instanceof ProfileFragment || fragment instanceof ScanFragment) {
            // For ProfileFragment and ScanFragment, use transparent status bar with content behind it
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(android.graphics.Color.TRANSPARENT);
                getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                );
            }
        } else {
            // For other fragments, use white status bar with dark icons
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(android.graphics.Color.WHITE);
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    private void setActiveTab(String activeTab) {
        // Hide all indicators first
        findViewById(R.id.homeIndicator).setVisibility(View.GONE);
        findViewById(R.id.scanIndicator).setVisibility(View.GONE);
        findViewById(R.id.searchIndicator).setVisibility(View.GONE);
        findViewById(R.id.profileIndicator).setVisibility(View.GONE);

        // Show the active tab indicator
        switch (activeTab) {
            case "home":
                findViewById(R.id.homeIndicator).setVisibility(View.VISIBLE);
                break;
            case "scan":
                findViewById(R.id.scanIndicator).setVisibility(View.VISIBLE);
                break;
            case "search":
                findViewById(R.id.searchIndicator).setVisibility(View.VISIBLE);
                break;
            case "profile":
                findViewById(R.id.profileIndicator).setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setupChatbotFab() {
        findViewById(R.id.chatbotFab).setOnClickListener(v -> {
            Intent intent = new Intent(this, NewChatbotActivity.class);
            startActivity(intent);
        });
    }
}