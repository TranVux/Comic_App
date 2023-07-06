package com.example.assignment.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.assignment.R;
import com.example.assignment.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());
    }
}