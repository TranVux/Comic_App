package com.example.assignment.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private var activityLoginBinding: ActivityLoginBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = ActivityLoginBinding.inflate(
            layoutInflater
        )
        setContentView(activityLoginBinding!!.root)
    }
}