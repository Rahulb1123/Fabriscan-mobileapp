package com.example.fabriscan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.cardRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Sample data for 4 cards
        List<CardItem> cardItems = new ArrayList<>();
        cardItems.add(new CardItem(R.drawable.dress1, "Long Sleeve Maxi Evening Dress", "Elegant and modern evening dress. Created from luxurious navy chiffon, offering a soft and flowy texture that drapes beautifully."));
        cardItems.add(new CardItem(R.drawable.dress2, "Traditional Velvet Sherwani", "This elegant sherwani features intricate embroidery on navy velvet, offering a regal look for special occasions."));
        cardItems.add(new CardItem(R.drawable.dress3, "Blue Embroidered Asymmetric Sherwani", "A regal blue sherwani featuring an asymmetric layered design, embellished with delicate floral embroidery and a jeweled collar for a modern royal look."));
        cardItems.add(new CardItem(R.drawable.dress4, "Soft Blue Tulle Midi Dress", "A dreamy, pastel blue midi dress with sheer floral lace detailing and a flowing tulle skirt, perfect for an elegant and graceful appearance."));

        CardAdapter adapter = new CardAdapter(cardItems);
        recyclerView.setAdapter(adapter);

        return view;
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
            android.widget.ImageView cardImage;
            android.widget.TextView cardTitle;
            android.widget.TextView cardDesc;
            android.widget.Button cardViewBtn;
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