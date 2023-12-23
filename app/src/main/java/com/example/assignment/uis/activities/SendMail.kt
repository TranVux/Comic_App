package com.example.assignment.uis.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.assignment.data.dto.AuthDTO
import com.example.assignment.databinding.ActivityOptBinding
import com.example.assignment.viewmodels.AuthViewModel

class SendMail : AppCompatActivity() {
    private val TAG = SendMail::class.java.simpleName;
    private val binding: ActivityOptBinding by lazy {
        ActivityOptBinding.inflate(layoutInflater)
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
        binding.buttonConfirm.setOnClickListener {
            val email = binding.emailEditText.editText?.text.toString();

            if (email.isEmpty()) {
                Toast.makeText(this@SendMail, "Vui lòng nhập email của bạn", Toast.LENGTH_SHORT)
                    .show();
            } else {
                viewModel.forgotPassword(
                    AuthDTO.EmailRequest(email), {
                        error ->
                        Log.e(TAG, "ForgotPassword: ${error.message}")
                        Toast.makeText(this@SendMail, "Có lỗi, vui lòng thử lại", Toast.LENGTH_LONG)
                            .show();
                    },
                    {
                        Toast.makeText(this@SendMail, "Chúng tôi đã gửi mail khôi phục mật khẩu tới $email\nVui lòng kiểm tra mail của bạn!", Toast.LENGTH_LONG)
                            .show();
                        startActivity(Intent(this@SendMail, LoginActivity::class.java))
                    })
            }
        }
    }
}