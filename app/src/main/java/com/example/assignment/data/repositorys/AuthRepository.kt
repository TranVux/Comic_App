package com.example.assignment.data.repositorys

import com.example.assignment.apis.AuthApi
import com.example.assignment.data.dto.AuthDTO
import retrofit2.Response

class AuthRepository(private val api: AuthApi) {
    suspend fun login(body: AuthDTO.LoginRequest): Response<AuthDTO.Response> = api.login(body);
    suspend fun register(body: AuthDTO.RegisterRequest): Response<AuthDTO.Response> = api.register(body)
    suspend fun verify(body: AuthDTO.EmailRequest): Response<AuthDTO.VerifyResponse> = api.verify(body);
    suspend fun forgotPassword(body: AuthDTO.EmailRequest): Response<AuthDTO.VerifyResponse> = api.forgotPassword(body);
}