package com.example.fabriscan;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.ViewTreeObserver;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.content.Intent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.widget.EditText;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);
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
        TextView linkSignIn = findViewById(R.id.linkSignIn);

        toggleFrame.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int padding = toggleFrame.getPaddingLeft() + toggleFrame.getPaddingRight();
                int width = (toggleFrame.getWidth() - padding) / 2;
                ViewGroup.LayoutParams pillParams = togglePill.getLayoutParams();
                pillParams.width = width;
                togglePill.setLayoutParams(pillParams);

                ViewGroup.LayoutParams signInParams = tabSignIn.getLayoutParams();
                signInParams.width = width;
                tabSignIn.setLayoutParams(signInParams);

                ViewGroup.LayoutParams signUpParams = tabSignUp.getLayoutParams();
                signUpParams.width = width;
                tabSignUp.setLayoutParams(signUpParams);

                // Set pill to right for Sign Up selected
                float moveX = toggleFrame.getWidth() - width - toggleFrame.getPaddingRight();
                togglePill.setTranslationX(moveX);

                toggleFrame.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        // Initial state: Sign Up selected
        tabSignIn.setTextColor(android.graphics.Color.BLACK);
        tabSignUp.setTextColor(android.graphics.Color.WHITE);
        tabSignIn.setTypeface(null, android.graphics.Typeface.BOLD);
        tabSignUp.setTypeface(null, android.graphics.Typeface.BOLD);

        tabSignIn.setOnClickListener(v -> {
            togglePill.animate().translationX(0).setDuration(200).start();
            tabSignIn.setTextColor(android.graphics.Color.BLACK); // Black on white
            tabSignUp.setTextColor(android.graphics.Color.WHITE); // White on blue
            tabSignIn.setTypeface(null, android.graphics.Typeface.BOLD);
            tabSignUp.setTypeface(null, android.graphics.Typeface.BOLD);
            // Open AuthActivity
            Intent intent = new Intent(SignUpActivity.this, AuthActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        });

        tabSignUp.setOnClickListener(v -> {
            int frameWidth = toggleFrame.getWidth();
            int pillWidth = togglePill.getWidth();
            float moveX = frameWidth - pillWidth - toggleFrame.getPaddingRight();
            togglePill.animate().translationX(moveX).setDuration(200).start();
            tabSignIn.setTextColor(android.graphics.Color.BLACK);
            tabSignUp.setTextColor(android.graphics.Color.WHITE);
            tabSignIn.setTypeface(null, android.graphics.Typeface.BOLD);
            tabSignUp.setTypeface(null, android.graphics.Typeface.BOLD);
        });

        linkSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, AuthActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        });

        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        EditText confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
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
        confirmPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                confirmPasswordEditText.setBackgroundResource(R.drawable.edittext_bg);
                if (s.length() > 0) {
                    confirmPasswordEditText.setError(null);
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });
        findViewById(R.id.btnSignUp).setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();
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
            if (confirmPassword.isEmpty()) {
                confirmPasswordEditText.setBackgroundResource(R.drawable.edittext_bg_error);
                confirmPasswordEditText.setError("Please confirm your password");
                hasError = true;
            }
            if (!password.equals(confirmPassword) && !password.isEmpty() && !confirmPassword.isEmpty()) {
                confirmPasswordEditText.setBackgroundResource(R.drawable.edittext_bg_error);
                confirmPasswordEditText.setError("Passwords do not match");
                hasError = true;
            }
            if (hasError) return;
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(this, "Congratulations! You have successfully signed up.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, AuthActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Sign up failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        });
    }
} 