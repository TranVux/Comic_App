package com.example.assignment.apis

import com.example.assignment.data.dto.AuthDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/login.php")
    suspend fun login(@Body body: AuthDTO.LoginRequest): Response<AuthDTO.Response>

    @POST("api/register.php")
    suspend fun register(@Body body: AuthDTO.RegisterRequest): Response<AuthDTO.Response>

    @POST("/api/auth/verify.php")
    suspend fun verify(@Body body: AuthDTO.EmailRequest): Response<AuthDTO.VerifyResponse>

    @POST("/api/auth/forgot_password.php")
    suspend fun forgotPassword(@Body body: AuthDTO.EmailRequest): Response<AuthDTO.VerifyResponse>
}