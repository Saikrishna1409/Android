package com.example.cards;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CardGridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create RecyclerView dynamically
        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        // Set GridLayoutManager (2 columns)
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Prepare sample data
        List<CardItem> cardList = new ArrayList<>();
        cardList.add(new CardItem("Ravindra", "Android Dev "));
        cardList.add(new CardItem("Ankit", "Web Dev"));
        cardList.add(new CardItem("Karthik", ".Net Dev"));
        cardList.add(new CardItem("Devarshi", "Back end Dev"));

        // Set adapter with data (pass `this` as context)
        recyclerView.setAdapter(new CardAdapter(this, cardList));

        // Set RecyclerView as the main content view
        setContentView(recyclerView);
    }

    // Data Model Class
    static class CardItem {
        String title;
        String description;

        public CardItem(String title, String description) {
            this.title = title;
            this.description = description;
        }
    }

    // Adapter Class
    static class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
        private final List<CardItem> cardList;
        private final int screenHeight;  // Store screen height

        public CardAdapter(Context context, List<CardItem> cardList) {
            this.cardList = cardList;

            // Get screen height for dynamic card sizing
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((AppCompatActivity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            screenHeight = displayMetrics.heightPixels;
        }

        @NonNull
        @Override
        public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Create CardView dynamically
            CardView cardView = new CardView(parent.getContext());
            int cardHeight = (screenHeight / 2) - 40; // Half screen height minus margin

            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, // Full width
                    cardHeight  // Adjust height dynamically
            );
            layoutParams.setMargins(20, 20, 20, 20); // Padding around cards
            cardView.setLayoutParams(layoutParams);
            cardView.setRadius(20);
            cardView.setCardElevation(8);
            cardView.setContentPadding(30, 30, 30, 30);
            cardView.setCardBackgroundColor(Color.LTGRAY);

            // Create Layout for Text Views
            LinearLayout layout = new LinearLayout(parent.getContext());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setGravity(Gravity.CENTER);
            layout.setPadding(20, 20, 20, 20);

            // Create Title TextView
            TextView titleTextView = new TextView(parent.getContext());
            titleTextView.setTextSize(20);
            titleTextView.setTextColor(Color.BLACK);
            titleTextView.setGravity(Gravity.CENTER);

            // Create Description TextView
            TextView descTextView = new TextView(parent.getContext());
            descTextView.setTextSize(16);
            descTextView.setTextColor(Color.DKGRAY);
            descTextView.setGravity(Gravity.CENTER);

            // Add TextViews to Layout
            layout.addView(titleTextView);
            layout.addView(descTextView);

            // Add Layout to CardView
            cardView.addView(layout);

            return new CardViewHolder(cardView, titleTextView, descTextView);
        }

        @Override
        public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
            CardItem item = cardList.get(position);
            holder.titleTextView.setText(item.title);
            holder.descTextView.setText(item.description);
        }

        @Override
        public int getItemCount() {
            return cardList.size();
        }

        // ViewHolder Class
        static class CardViewHolder extends RecyclerView.ViewHolder {
            TextView titleTextView, descTextView;

            public CardViewHolder(@NonNull CardView itemView, TextView titleTextView, TextView descTextView) {
                super(itemView);
                this.titleTextView = titleTextView;
                this.descTextView = descTextView;
            }
        }
    }
}
