package com.example.assignment.uis.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private val activityRegisterBinding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityRegisterBinding.root)
    }
}