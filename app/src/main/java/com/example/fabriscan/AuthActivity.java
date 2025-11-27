package com.example.fabriscan;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.view.ViewTreeObserver;
import android.view.ViewGroup;
import android.content.Intent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.widget.EditText;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;

public class AuthActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);
        mAuth = FirebaseAuth.getInstance();

        // Make status bar transparent and draw behind it
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(android.graphics.Color.TRANSPARENT);
            window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
        }

        FrameLayout toggleFrame = findViewById(R.id.toggleFrame);
        View togglePill = findViewById(R.id.togglePill);
        TextView tabSignIn = findViewById(R.id.tabSignIn);
        TextView tabSignUp = findViewById(R.id.tabSignUp);
        TextView linkSignUp = findViewById(R.id.linkSignUp);

        // Ensure the pill is half the width of the toggle frame after layout
        toggleFrame.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = toggleFrame.getWidth() / 2;
                ViewGroup.LayoutParams pillParams = togglePill.getLayoutParams();
                pillParams.width = width;
                togglePill.setLayoutParams(pillParams);

                ViewGroup.LayoutParams signInParams = tabSignIn.getLayoutParams();
                signInParams.width = width;
                tabSignIn.setLayoutParams(signInParams);

                ViewGroup.LayoutParams signUpParams = tabSignUp.getLayoutParams();
                signUpParams.width = width;
                tabSignUp.setLayoutParams(signUpParams);

                toggleFrame.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        // Initial state: Sign In selected
        tabSignIn.setTextColor(android.graphics.Color.WHITE);
        tabSignIn.setTypeface(null, android.graphics.Typeface.BOLD);
        tabSignUp.setTextColor(android.graphics.Color.BLACK);
        tabSignUp.setTypeface(null, android.graphics.Typeface.BOLD);

        tabSignIn.setOnClickListener(v -> {
            togglePill.animate().translationX(0).setDuration(200).start();
            tabSignIn.setTextColor(android.graphics.Color.WHITE);
            tabSignUp.setTextColor(android.graphics.Color.BLACK);
            tabSignIn.setTypeface(null, android.graphics.Typeface.BOLD);
            tabSignUp.setTypeface(null, android.graphics.Typeface.BOLD);
        });

        tabSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(AuthActivity.this, SignUpActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        });

        linkSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(AuthActivity.this, SignUpActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        });

        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailEditText.setBackgroundResource(R.drawable.edittext_bg);
                if (s.length() > 0) {
                    emailEditText.setError(null);
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordEditText.setBackgroundResource(R.drawable.edittext_bg);
                if (s.length() > 0) {
                    passwordEditText.setError(null);
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });
        findViewById(R.id.btnSignIn).setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            boolean hasError = false;
            if (email.isEmpty()) {
                emailEditText.setBackgroundResource(R.drawable.edittext_bg_error);
                emailEditText.setError("Please enter your email");
                hasError = true;
            }
            if (password.isEmpty()) {
                passwordEditText.setBackgroundResource(R.drawable.edittext_bg_error);
                passwordEditText.setError("Please enter your password");
                hasError = true;
            }
            if (hasError) return;
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(this, "Sign in successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AuthActivity.this, MainContainerActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        });
    }
} 