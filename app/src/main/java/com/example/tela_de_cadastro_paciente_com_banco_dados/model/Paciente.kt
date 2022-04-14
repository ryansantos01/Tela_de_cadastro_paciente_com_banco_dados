package com.example.tela_de_cadastro_paciente_com_banco_dados.model

data class Paciente(
   //Classes da tabela do mednow
    var idPaciente: Int=0,
    var nomePaciente: String="",
    var idadePaciente: String="",
    var sexoPaciente: String="",
    var enderecoPaciente: String="",
    var numerodaresidenciaPaciente: String="",
    var complementoPaciente: String="",
    var cidadePaciente: String="",
    var estadoPaciente: String="",
    var cepPaciente: String="",
    var planodesaudePaciente: String="",
    var telefonePaciente: String="",
    var estadocivilPaciente: String="",
    var rgPaciente: String="",
    var cpfPaciente: String="",
    /*
    var codsusPaciente: String="",
    var nomeplanosaudePaciente: String="",
     */
)
