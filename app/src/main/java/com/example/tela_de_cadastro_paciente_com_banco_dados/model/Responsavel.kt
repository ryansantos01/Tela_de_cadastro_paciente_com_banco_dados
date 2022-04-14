package com.example.tela_de_cadastro_paciente_com_banco_dados.model

data class Responsavel(
    var idResponsavel: Int=0,
    var idpacienteResponsavel: Int=0,
    var nomepacienteResponsavel: String="",
    var nomeResponsavel: String="",
    var emailResponsavel: String="",
    var celResponsavel: String="",
    var whatsResponsavel: String="",
    var tipoResponsavel: String="",
    var caixamensagememergenciaResponsavel: String="",
)
