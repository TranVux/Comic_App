package com.example.assignment.uis.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.assignment.data.dto.AuthDTO
import com.example.assignment.databinding.ActivityLoginBinding
import com.example.assignment.viewmodels.AuthViewModel
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity() {
    private val TAG: String = LoginActivity::class.java.simpleName;
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private val viewModel: AuthViewModel by lazy {
        ViewModelProvider(
            this,
            AuthViewModel.AuthViewModelFactory(this.application)
        )[AuthViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        addEvent();
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun addEvent() {
        binding.gotoRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }
        binding.buttonLogin.setOnClickListener {
            if (binding.emailEditText.editText?.text.toString()
                    .isEmpty() || binding.passwordEditText.editText?.text.toString().isEmpty()
            ) {
                Toast.makeText(
                    this@LoginActivity,
                    "Không được để trống mật khẩu hay email",
                    Toast.LENGTH_LONG
                ).show();
            } else {
//                GlobalScope.launch(Dispatchers.IO) {
                val result = viewModel.login(
                    AuthDTO.LoginRequest(
                        getText(binding.emailEditText),
                        getText(binding.passwordEditText)
                    ),
                    { res ->
                        Log.d(TAG, "addEvent: $res")
                        startActivity(
                            Intent(
                                this@LoginActivity,
                                NavigationActivity::class.java
                            )
                        )
                        finishAffinity()
                    },
                    { error ->
                        Log.d(TAG, "addEvent: $error")
                        Toast.makeText(
                            this@LoginActivity,
                            "Lỗi đăng nhập vui lòng thử lại",
                            Toast.LENGTH_LONG
                        ).show();
                    }
                )

                Log.d(TAG, "addEvent: $result")
//                }
            }
        }
        binding.forgotPassword.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SendMail::class.java))
        }
    }

    private fun getText(editTextLayout: TextInputLayout): String {
        return editTextLayout.editText?.text.toString();
    }
}