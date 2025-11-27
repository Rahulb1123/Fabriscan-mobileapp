package com.example.fabriscan;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.welcome_activity);

        FrameLayout sliderCircle = findViewById(R.id.sliderCircle);
        FrameLayout sliderContainer = findViewById(R.id.sliderContainer);

        sliderCircle.setOnTouchListener(new View.OnTouchListener() {
            private float initialX;
            private boolean isSwiping = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = event.getX();
                        isSwiping = true;
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        if (isSwiping) {
                            float newX = v.getX() + event.getX() - initialX;
                            if (newX > 0 && newX < sliderContainer.getWidth() - v.getWidth()) {
                                v.setX(newX);
                            }
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        if (isSwiping) {
                            if (v.getX() > sliderContainer.getWidth() * 0.6) {
                                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
                                finish();
                            } else {
                                v.animate().x(0).setDuration(200).start();
                            }
                        }
                        isSwiping = false;
                        return true;
                }
                return false;
            }
        });
    }
}