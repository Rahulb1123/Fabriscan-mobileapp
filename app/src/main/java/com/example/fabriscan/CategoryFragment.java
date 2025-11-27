package com.example.fabriscan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_category, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.categoryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<CategoryItem> categories = new ArrayList<>();
        categories.add(new CategoryItem("Silk", "Smooth and shiny", R.drawable.red_silk));
        categories.add(new CategoryItem("Cotton", "Soft, breathable and natural", R.drawable.yellow_cotton));
        categories.add(new CategoryItem("Wool", "Warm and insulating", R.drawable.blue_wool));
        categories.add(new CategoryItem("Rayon", "Soft and versatile", R.drawable.grey_rayon));
        categories.add(new CategoryItem("Linen", "Light, breathable, and elegant", R.drawable.ic_linen));
        categories.add(new CategoryItem("Denim", "Durable and timeless", R.drawable.ic_denim));
        categories.add(new CategoryItem("Velvet", "Luxurious and soft to touch", R.drawable.ic_velvet));
        categories.add(new CategoryItem("Polyester", "Wrinkle-resistant and long-lasting", R.drawable.ic_polyester));
        CategoryAdapter adapter = new CategoryAdapter(categories);
        recyclerView.setAdapter(adapter);
        return view;
    }
}