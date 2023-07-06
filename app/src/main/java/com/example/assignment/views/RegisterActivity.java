package com.example.assignment.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.assignment.R;
import com.example.assignment.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding activityRegisterBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(activityRegisterBinding.getRoot());
    }
}