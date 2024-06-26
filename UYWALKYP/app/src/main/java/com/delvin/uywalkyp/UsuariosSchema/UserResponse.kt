package com.delvin.uywalkyp.UsuariosSchema

data class UserResponse(
    val accountNonExpired: Boolean,
    val accountNonLocked: Boolean,
    val apellidos: String,
    val apodo: String,
    val authorities: List<Any>,
    val celular: String,
    val createdAt: Any,
    val createdBy: Any,
    val credentialsNonExpired: Boolean,
    val direccion: String,
    val dni: String,
    val edad: Int,
    val email: String,
    val enabled: Boolean,
    val estado: Any,
    val id: Int,
    val nombres: String,
    val password: String,
    val tipoUsuario: TipoUsuarioX,
    val updatedAt: Any,
    val updatedBy: Any,
    val username: String
)