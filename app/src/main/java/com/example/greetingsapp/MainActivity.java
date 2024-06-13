package com.example.greetingsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

/*
        TextView tvHappy2U = findViewById(R.id.tvHappy2U);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        tvHappy2U.startAnimation(fadeIn);
*/

        ImageView imageView = findViewById(R.id.image1);
        imageView.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, GreetingAnniversary.class);
            startActivity(intent);

        });
    }
}