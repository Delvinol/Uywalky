package com.delvin.uywalkyp.LoginSchema

data class LoginResponse(
    val apellidos: String,
    val id: Int,
    val idTipoUsuario: Int,
    val nombres: String,
    val token: String
)