package com.example.assignment.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private var activityRegisterBinding: ActivityRegisterBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegisterBinding = ActivityRegisterBinding.inflate(
            layoutInflater
        )
        setContentView(activityRegisterBinding!!.root)
    }
}