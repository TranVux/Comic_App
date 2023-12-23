package com.example.assignment.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.assignment.apis.RetrofitInstance
import com.example.assignment.data.dto.AuthDTO
import com.example.assignment.data.repositorys.AuthRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG: String = AuthViewModel::class.java.simpleName;

    private val authService by lazy {
        RetrofitInstance.authService
    }

    private val authRepository by lazy {
        AuthRepository(authService)
    }

    private val handlerError = CoroutineExceptionHandler { _, e ->
        Log.e(TAG, "Error Fetch API$e")
    }

    fun login(
        body: AuthDTO.LoginRequest,
        onSuccess: (res: AuthDTO.Response) -> Unit,
        onError: (e: Exception) -> Unit
    ) {
        viewModelScope.launch(handlerError) {
            val response = authRepository.login(body)

            if (response.isSuccessful) {
                when (response.body()?.status) {
                    "error" -> onError(Exception(response.body()?.message))
                    "success" -> response.body()?.let { onSuccess(it) }
                }
            } else {
                onError(Exception("Lỗi đăng nhập"))
            }
        }
    }

    fun register(
        body: AuthDTO.RegisterRequest,
        onSuccess: (res: AuthDTO.Response) -> Unit,
        onError: (e: Exception) -> Unit
    ) {
        viewModelScope.launch(handlerError) {
            val response = authRepository.register(body)
            if (response.isSuccessful) {
                when (response.body()?.status) {
                    "error" -> onError(Exception(response.body()?.message))
                    "success" -> response.body()?.let {
                        authRepository.verify(AuthDTO.EmailRequest(body.email))
                        onSuccess(it)
                    }
                }
            } else {
                onError(Exception("Lỗi đăng ký"))
            }
        }
    }

    fun forgotPassword(
        body: AuthDTO.EmailRequest,
        onError: (e: Exception) -> Unit,
        onSuccess: (res: AuthDTO.VerifyResponse) -> Unit
    ){
        viewModelScope.launch (handlerError){
            val response = authRepository.forgotPassword(body);
            if (response.isSuccessful) {
                when (response.body()?.status) {
                    400 -> onError(Exception(response.body()?.message))
                    200 -> response.body()?.let {
                        onSuccess(it)
                    }
                }
            } else {
                onError(Exception("Lỗi khôi phục mật khẩu"))
            }
        }
    }

    class AuthViewModelFactory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                return AuthViewModel(app) as T
            }
            throw IllegalArgumentException("Unable construct view model")
        }
    }
}