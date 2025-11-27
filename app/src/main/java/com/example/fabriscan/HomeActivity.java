package com.example.fabriscan;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.Window;
import android.os.Build;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        // Set status bar color to match activity background (white) and use dark icons
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(android.graphics.Color.WHITE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        RecyclerView recyclerView = findViewById(R.id.cardRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Sample data for 4 cards
        List<CardItem> cardItems = new ArrayList<>();
        cardItems.add(new CardItem(R.drawable.dress1, "Long Sleeve Maxi Evening Dress", "Elegant and modern evening dress. Created from luxurious navy chiffon, offering a soft and flowy texture that drapes beautifully."));
        cardItems.add(new CardItem(R.drawable.dress2, "Traditional Velvet Sherwani", "This elegant sherwani features intricate embroidery on navy velvet, offering a regal look for special occasions."));
        cardItems.add(new CardItem(R.drawable.dress1, "Blue Gown", "A stunning blue gown perfect for formal events, crafted with attention to detail and comfort."));
        cardItems.add(new CardItem(R.drawable.dress2, "Classic Suit", "A timeless classic suit, tailored for a sharp and sophisticated appearance."));

        CardAdapter adapter = new CardAdapter(cardItems);
        recyclerView.setAdapter(adapter);

    }

    // CardItem model class
    static class CardItem {
        int imageResId;
        String title;
        String desc;
        CardItem(int imageResId, String title, String desc) {
            this.imageResId = imageResId;
            this.title = title;
            this.desc = desc;
        }
    }

    // CardAdapter for RecyclerView
    static class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
        List<CardItem> items;
        CardAdapter(List<CardItem> items) { this.items = items; }
        @Override
        public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
            return new CardViewHolder(view);
        }
        @Override
        public void onBindViewHolder(CardViewHolder holder, int position) {
            CardItem item = items.get(position);
            holder.cardImage.setImageResource(item.imageResId);
            holder.cardTitle.setText(item.title);
            holder.cardDesc.setText(item.desc);
            holder.cardViewBtn.setOnClickListener(v -> {
                // Handle button click if needed
            });
        }
        @Override
        public int getItemCount() { return items.size(); }
        static class CardViewHolder extends RecyclerView.ViewHolder {
            ImageView cardImage;
            TextView cardTitle;
            TextView cardDesc;
            Button cardViewBtn;
            CardViewHolder(View itemView) {
                super(itemView);
                cardImage = itemView.findViewById(R.id.cardImage);
                cardTitle = itemView.findViewById(R.id.cardTitle);
                cardDesc = itemView.findViewById(R.id.cardDesc);
                cardViewBtn = itemView.findViewById(R.id.cardViewBtn);
            }
        }
    }
} 