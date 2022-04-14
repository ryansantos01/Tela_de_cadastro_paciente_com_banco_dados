package com.example.tela_de_cadastro_paciente_com_banco_dados.model

data class Medicamento (
    var idMedicamento: Int=0,
    var nomepacienteMedicamento: String="",
    var nomeMed: String="",
    var dataValidadeMed: String="",
    var descMed: String="",
    var qtdTotalMed: String="",
    var unidadeqtdtotalMed: String="",
    var tipoLocalMed: String="",
    var embalagemMed: String="",
    var horaInicialMed: String="",
    var agend: String="",
    var numVezesMed: String="",
    var dosagemMed: String="",
    var unidadedosagemMed: String="",
    var idpacienteMedicamento: Int=0,
    //var imagemMed: String=""
)
