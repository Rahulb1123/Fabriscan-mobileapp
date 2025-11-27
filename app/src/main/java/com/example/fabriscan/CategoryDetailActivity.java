package com.example.fabriscan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CategoryDetailActivity extends AppCompatActivity {

    private TextView categoryTitle;
    private TextView categorySubtitle;
    private TextView categoryDescription;
    private RecyclerView imageRecyclerView;
    private ImageDetailAdapter imageAdapter;
    private List<ImageItem> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);

        // Make status bar transparent and draw behind it
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(android.graphics.Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
        }
        // Initialize views
        categoryTitle = findViewById(R.id.categoryTitle);
        categorySubtitle = findViewById(R.id.categorySubtitle);
        categoryDescription = findViewById(R.id.categoryDescription);
        imageRecyclerView = findViewById(R.id.imageRecyclerView);
        ImageView fabricSampleImage = findViewById(R.id.fabricSampleImage);

        // Get category data from intent
        String categoryName = getIntent().getStringExtra("category_name");
        String categoryDescriptionText = getIntent().getStringExtra("category_description");
        int imageResId = getIntent().getIntExtra("category_image", R.drawable.red_silk);

        // Set category information
        categoryTitle.setText(categoryName);
        categorySubtitle.setText(categoryDescriptionText);
        fabricSampleImage.setImageResource(imageResId);
        
        // Set dynamic paragraph text based on category
        setCategoryDescription(categoryName);

        // Setup RecyclerView
        imageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        // Initialize image data based on category
        initializeImageData(categoryName);
        
        // Setup adapter
        imageAdapter = new ImageDetailAdapter(imageList);
        imageRecyclerView.setAdapter(imageAdapter);
    }

    private void setCategoryDescription(String categoryName) {
        String description = "";
        switch (categoryName) {
            case "Silk":
                description = "Silk is a natural, luxurious fiber known for its soft texture and elegant sheen. It's lightweight, breathable, and widely used in premium clothing and fabrics.";
                break;
            case "Cotton":
                description = "Cotton is a natural, soft fiber that grows around the seeds of cotton plants. It's breathable, comfortable, and perfect for everyday wear in various climates.";
                break;
            case "Wool":
                description = "Wool is a natural fiber obtained from sheep and other animals. It's warm, insulating, and naturally moisture-wicking, making it ideal for cold weather clothing.";
                break;
            case "Rayon":
                description = "Rayon is a semi-synthetic fiber made from natural cellulose. It's soft, breathable, and drapes beautifully, often used as a silk alternative in affordable clothing.";
                break;
            case "Linen":
                description = "Linen is a natural fiber made from the flax plant. It's highly breathable, moisture-wicking, and becomes softer with each wash, perfect for summer clothing.";
                break;
            case "Denim":
                description = "Denim is a sturdy cotton twill fabric, typically dyed with indigo. It's durable, versatile, and has become a timeless fabric for casual and fashion-forward clothing.";
                break;
            case "Velvet":
                description = "Velvet is a luxurious fabric with a distinctive soft, dense pile. It's elegant, warm, and adds a touch of sophistication to formal wear and home decor.";
                break;
            case "Polyester":
                description = "Polyester is a synthetic fiber known for its durability, wrinkle resistance, and quick-drying properties. It's widely used in sportswear and everyday clothing.";
                break;
            default:
                description = "This fabric offers unique properties and characteristics that make it suitable for various clothing applications and fashion styles.";
                break;
        }
        categoryDescription.setText(description);
    }

    private void initializeImageData(String categoryName) {
        imageList = new ArrayList<>();
        
        switch (categoryName) {
            case "Silk":
                // 4 different clothing images for one row
                imageList.add(new ImageItem(R.drawable.silk4, "Silk Evening Dress", "Elegant silk evening dress"));
                imageList.add(new ImageItem(R.drawable.silk3, "Silk Blouse", "Luxurious silk blouse"));
                imageList.add(new ImageItem(R.drawable.silk1, "Silk Scarf", "Soft silk scarf"));
                imageList.add(new ImageItem(R.drawable.silk2, "Silk Handbag", "Silk accessory"));
                break;
            case "Cotton":
                imageList.add(new ImageItem(R.drawable.cotton1, "Cotton T-Shirt", "Comfortable cotton t-shirt"));
                imageList.add(new ImageItem(R.drawable.cotton2, "Cotton Dress", "Casual cotton dress"));
                imageList.add(new ImageItem(R.drawable.cotton3, "Cotton Jeans", "Classic cotton jeans"));
                imageList.add(new ImageItem(R.drawable.cotton4, "Cotton Sweater", "Warm cotton sweater"));
                break;
            case "Wool":
                imageList.add(new ImageItem(R.drawable.wool1, "Wool Coat", "Warm wool coat"));
                imageList.add(new ImageItem(R.drawable.wool2, "Wool Sweater", "Cozy wool sweater"));
                imageList.add(new ImageItem(R.drawable.wool3, "Wool Scarf", "Soft wool scarf"));
                imageList.add(new ImageItem(R.drawable.wool4, "Wool Hat", "Warm wool hat"));
                break;
            case "Rayon":
                imageList.add(new ImageItem(R.drawable.rayon1, "Rayon Blouse", "Elegant rayon blouse"));
                imageList.add(new ImageItem(R.drawable.rayon2, "Rayon Skirt", "Flowing rayon skirt"));
                imageList.add(new ImageItem(R.drawable.rayon3, "Rayon Dress", "Versatile rayon dress"));
                imageList.add(new ImageItem(R.drawable.rayon4, "Rayon Top", "Comfortable rayon top"));
                break;
            case "Linen":
                imageList.add(new ImageItem(R.drawable.linen1, "Rayon Blouse", "Elegant rayon blouse"));
                imageList.add(new ImageItem(R.drawable.linen2, "Rayon Skirt", "Flowing rayon skirt"));
                imageList.add(new ImageItem(R.drawable.linen3, "Rayon Dress", "Versatile rayon dress"));
                imageList.add(new ImageItem(R.drawable.linen4, "Rayon Top", "Comfortable rayon top"));
                break;
            case "Denim":
                imageList.add(new ImageItem(R.drawable.denim1, "Rayon Blouse", "Elegant rayon blouse"));
                imageList.add(new ImageItem(R.drawable.denim2, "Rayon Skirt", "Flowing rayon skirt"));
                imageList.add(new ImageItem(R.drawable.denim3, "Rayon Dress", "Versatile rayon dress"));
                imageList.add(new ImageItem(R.drawable.denim4, "Rayon Top", "Comfortable rayon top"));
                break;
            case "Velvet":
                imageList.add(new ImageItem(R.drawable.velvet1, "Rayon Blouse", "Elegant rayon blouse"));
                imageList.add(new ImageItem(R.drawable.velvet2, "Rayon Skirt", "Flowing rayon skirt"));
                imageList.add(new ImageItem(R.drawable.velvet3, "Rayon Dress", "Versatile rayon dress"));
                imageList.add(new ImageItem(R.drawable.velvet4, "Rayon Top", "Comfortable rayon top"));
                break;
            case "Polyester":
                imageList.add(new ImageItem(R.drawable.polyester1, "Rayon Blouse", "Elegant rayon blouse"));
                imageList.add(new ImageItem(R.drawable.polyester2, "Rayon Skirt", "Flowing rayon skirt"));
                imageList.add(new ImageItem(R.drawable.polyester3, "Rayon Dress", "Versatile rayon dress"));
                imageList.add(new ImageItem(R.drawable.polyester4, "Rayon Top", "Comfortable rayon top"));
                break;
            default:
                // Default images
                imageList.add(new ImageItem(R.drawable.dress1, "Fabric Item 1", "Beautiful fabric item"));
                imageList.add(new ImageItem(R.drawable.dress2, "Fabric Item 2", "Elegant fabric item"));
                imageList.add(new ImageItem(R.drawable.dress1, "Fabric Item 3", "Stylish fabric item"));
                imageList.add(new ImageItem(R.drawable.dress2, "Fabric Item 4", "Classic fabric item"));
                break;
        }
    }

    // ImageItem model class
    static class ImageItem {
        int imageResId;
        String title;
        String description;

        ImageItem(int imageResId, String title, String description) {
            this.imageResId = imageResId;
            this.title = title;
            this.description = description;
        }
    }

    // ImageDetailAdapter for RecyclerView
    static class ImageDetailAdapter extends RecyclerView.Adapter<ImageDetailAdapter.ImageViewHolder> {
        List<ImageItem> items;

        ImageDetailAdapter(List<ImageItem> items) {
            this.items = items;
        }

        @Override
        public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_detail, parent, false);
            return new ImageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ImageViewHolder holder, int position) {
            int leftIdx = position * 4;
            int centerIdx = leftIdx + 1;
            int topRightIdx = leftIdx + 2;
            int bottomRightIdx = leftIdx + 3;

            setImageOrHide(holder.leftImage, leftIdx);
            setImageOrHide(holder.centerImage, centerIdx);
            setImageOrHide(holder.topRightImage, topRightIdx);
            setImageOrHide(holder.bottomRightImage, bottomRightIdx);
        }

        private void setImageOrHide(ImageView imageView, int idx) {
            if (idx < items.size()) {
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageResource(items.get(idx).imageResId);
            } else {
                imageView.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return (int) Math.ceil(items.size() / 4.0);
        }

        static class ImageViewHolder extends RecyclerView.ViewHolder {
            ImageView leftImage, centerImage, topRightImage, bottomRightImage;
            ImageViewHolder(View itemView) {
                super(itemView);
                leftImage = itemView.findViewById(R.id.leftImage);
                centerImage = itemView.findViewById(R.id.centerImage);
                topRightImage = itemView.findViewById(R.id.topRightImage);
                bottomRightImage = itemView.findViewById(R.id.bottomRightImage);
            }
        }
    }
}