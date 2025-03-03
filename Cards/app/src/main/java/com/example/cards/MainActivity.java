package com.example.cards;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create Button dynamically
        Button button = new Button(this);
        button.setText("Open Card Grid");
        button.setTextSize(18);
        button.setPadding(20, 20, 20, 20);

        // Set Click Listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CardGridActivity.class);
                startActivity(intent);
            }
        });

        // Set Button as the main content view
        setContentView(button);
    }
}
