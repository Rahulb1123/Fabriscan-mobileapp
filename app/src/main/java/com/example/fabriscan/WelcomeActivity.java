package com.example.fabriscan;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.content.Intent;

public class WelcomeActivity extends AppCompatActivity {
    private FrameLayout sliderContainer;
    private FrameLayout sliderButtonBg;
    private int buttonMarginPx; // 4dp margin in pixels

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

        // Convert 4dp to pixels
        float scale = getResources().getDisplayMetrics().density;
        buttonMarginPx = (int) (4 * scale + 0.5f);

        // Make status bar transparent and draw behind it
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(android.graphics.Color.TRANSPARENT);
            window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
        }

        sliderContainer = findViewById(R.id.sliderContainer);
        sliderButtonBg = (FrameLayout) sliderContainer.getChildAt(0); // The white button background
        final ImageView sliderButton = sliderButtonBg.findViewById(R.id.sliderButton);

        sliderButton.setOnTouchListener(new View.OnTouchListener() {
            float dX = 0;
            float startX = 0;
            boolean isSliding = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float containerWidth = sliderContainer.getWidth();
                float buttonWidth = sliderButtonBg.getWidth();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = sliderButtonBg.getX() - event.getRawX();
                        startX = sliderButtonBg.getX();
                        isSliding = true;
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        if (!isSliding) return false;
                        float newX = event.getRawX() + dX;
                        // Clamp within container, respecting margin
                        newX = Math.max(buttonMarginPx, Math.min(newX, containerWidth - buttonWidth - buttonMarginPx));
                        sliderButtonBg.setX(newX);
                        return true;
                    case MotionEvent.ACTION_UP:
                        isSliding = false;
                        // If slid to end, trigger action
                        if (sliderButtonBg.getX() > containerWidth - buttonWidth - buttonMarginPx - 20) {
                            // Launch AuthActivity when swiped to end
                            Intent intent = new Intent(WelcomeActivity.this, AuthActivity.class);
                            startActivity(intent);
                        } else {
                            // Animate back to start (margin)
                            sliderButtonBg.animate().x(buttonMarginPx).setDuration(200).start();
                        }
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sliderButtonBg != null) {
            sliderButtonBg.setX(buttonMarginPx);
        }
    }
} 