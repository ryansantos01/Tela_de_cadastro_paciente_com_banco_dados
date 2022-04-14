package com.example.tela_de_cadastro_paciente_com_banco_dados.model

data class Patologia(
    var idPatologia: Int=0,
    var idpacientePatologia: Int=0,
    var nomePacientePatologia: String="",
    var nomeMedicoPatologia: String="",
    var nomePatologia: String="",
    var descPatologia: String="",
    var dataUltimoExame: String="",
)