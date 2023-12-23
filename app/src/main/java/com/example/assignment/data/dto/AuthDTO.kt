package com.example.assignment.data.dto

import com.example.assignment.models.User

class AuthDTO {
    class Response(val user: User, val status: String, val message: String) {
        override fun toString(): String {
            return "Email: $user"
        }
    }
    class LoginRequest(val email: String, val password: String) {}
    class RegisterRequest(val email: String, val password: String, val user_name: String) {}
    class VerifyResponse(val status: Int, val message: String){}
    class EmailRequest (val email: String){}
}