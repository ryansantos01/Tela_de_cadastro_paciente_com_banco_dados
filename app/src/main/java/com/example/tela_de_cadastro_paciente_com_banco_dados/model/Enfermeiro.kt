package com.example.tela_de_cadastro_paciente_com_banco_dados.model

data class Enfermeiro(
    val id: Int = -1,
    val name: String="",
    val email: String="",
    val coren: String="",
    val password: String="",
    var tipoUsuario: String="",
)