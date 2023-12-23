package com.example.assignment.uis.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.assignment.data.dto.AuthDTO
import com.example.assignment.databinding.ActivityRegisterBinding
import com.example.assignment.viewmodels.AuthViewModel
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private val TAG: String = RegisterActivity::class.java.simpleName;
    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
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

        init();
    }

    private fun init() {
        addEvent();
    }

    private fun addEvent() {
        binding.goToLogin.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java));
            finish()
        }

        binding.buttonRegister.setOnClickListener {
            val email = getText(binding.emailEditText)
            val password = getText(binding.passwordEditText)
            val rePassword = getText(binding.rePasswordEditText)
            val userName = getText(binding.userNameEditText)
            val passwordOk: Boolean = password == rePassword;
            val isOk: Boolean = email.isNotEmpty() && userName.isNotEmpty()

            if (isOk) {
                if (passwordOk) {
                    viewModel.register(AuthDTO.RegisterRequest(email, password, userName), { res ->
                        Toast.makeText(
                            this@RegisterActivity,
                            "Đăng ký tài khoản thành công\nĐã gửi mail xác thực đến mail $email",
                            Toast.LENGTH_SHORT
                        ).show();
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    }, { e ->
                        Toast.makeText(
                            this@RegisterActivity,
                            "Vui lòng thử lại",
                            Toast.LENGTH_SHORT
                        ).show();
                    })
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Mật khẩu phải khớp nhau",
                        Toast.LENGTH_SHORT
                    ).show();
                }
            } else {
                Toast.makeText(
                    this@RegisterActivity,
                    "Không được để trống trường nào",
                    Toast.LENGTH_SHORT
                ).show();
            }
        }
    }

    private fun getText(editTextLayout: TextInputLayout): String {
        return editTextLayout.editText?.text.toString();
    }
}