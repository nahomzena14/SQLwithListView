package com.example.listviewwithsql.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.listviewwithsql.databinding.ActivityDetailsBinding;
import com.example.listviewwithsql.model.User;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        User user = getIntent().getParcelableExtra("user");

        if(user != null){
            binding.userDetailsTextView.setText(user.toString());
        }
    }
}