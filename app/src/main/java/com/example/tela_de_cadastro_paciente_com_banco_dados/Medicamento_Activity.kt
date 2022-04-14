package com.example.tela_de_cadastro_paciente_com_banco_dados

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.View
import android.widget.*
import androidx.core.view.ViewCompat
import androidx.databinding.adapters.ViewBindingAdapter.setPadding
import com.example.tela_de_cadastro_paciente_com_banco_dados.db.DatabaseHandler
import com.example.tela_de_cadastro_paciente_com_banco_dados.function.Mask
import com.example.tela_de_cadastro_paciente_com_banco_dados.function.PeriodClass
import com.example.tela_de_cadastro_paciente_com_banco_dados.model.Medicamento
import com.example.tela_de_cadastro_paciente_com_banco_dados.model.Paciente
import kotlinx.android.synthetic.main.activity_medicamento.*

class Medicamento_Activity : AppCompatActivity() {
    var paciente: Paciente? = null
    var medicamento: Medicamento? = null
    val databaseHandler1 = DatabaseHandler(this)//Não estava privado
    var pacienteList = ArrayList <Paciente>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicamento)

        initSpinnernomepacientemedicamento()
        //Faz com que crie h e min na caixa de hora
        caixamedhorainicial.addTextChangedListener(Mask.mask("##h : ##min", caixamedhorainicial))
        //Faz com que Crie barras ao digitar a data de nascimento
        etValidade.addTextChangedListener(Mask.mask("##/##/####", etValidade))

        var unidadedosagem = arrayOf("ATOMIZAÇÃO(S) ('ESPIRRADA')", "CÁPSULA(S)", "COMPRIMIDO(S)",
            "COLHER DE CAFÉ - (2,5 ml)", "COLHER DE CHÁ - (5,0 ml)", "COLHER DE SOBREMESA - (10 ml)",
            "COLHER DE SOPA - (15 ml)", "GOTA(S)", "MICROGOTA(S)", "Mg", "Ml")
        spinnerdosagem.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, unidadedosagem)
        var unidadetotal = arrayOf("G", "GOTAS", "CÁPSULAS", "COMPRIMIDOS", "Mg", "Ml", "UI")
        spinnerunidadetotal.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, unidadetotal)
        var localdeaplicacao = arrayOf("ORAL", "NASAL", "INJETADO", "AURICULAR", "OLHOS", "PELE", "ANAL")
        spinnerlocaldeaplicacao.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, localdeaplicacao)
        var embalagemmed = arrayOf("AMPOLA", "FRASCO", "BILÍSTER", "BISNAGA", "SERINGA", "BOLSA")
        spinnerembalagemmedicamento.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, embalagemmed)


botaodahora.setOnClickListener {
if(testeDatabotaodahora()) {
        DeleteWidgetAuto(container)
        var tam = Integer.parseInt(spinnernumerodevezesnodia.text.toString())
        for (i in 1..tam) {
            CreateWidgetAuto(i, container)
        }
    }
else {
    Toast.makeText(this, "O número de vezes não é compativél", Toast.LENGTH_SHORT).show()
}
}



        if (intent.getStringExtra("modeMed") == "EditMed") {
            medicamento = databaseHandler1.getMedicamento(intent.getIntExtra("idMed", 0))
            textonomepacientemedicamento.setText(medicamento!!.idpacienteMedicamento.toString())
            fun initSpinnernomepacientemedicamento() {
                pacienteList = databaseHandler1.getPacienteList()!!
                var nomepacienteList: String
                var PacienteLista = ArrayList<String>()
                var tam = pacienteList.size - 1
                for (i in 0..tam) {
                    nomepacienteList = pacienteList[i].nomePaciente
                    PacienteLista.add(nomepacienteList)

                }
                spinnernomepacientemedicamento.setSelection(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, PacienteLista).getPosition(medicamento!!.nomepacienteMedicamento))
            }
            caixamednomemedicamento.setText(medicamento!!.nomeMed)
            etValidade.setText(medicamento!!.dataValidadeMed)
            caixameddescricaomedicamento.setText(medicamento!!.descMed)
            caixamedqnttotalmedicamento.setText(medicamento!!.qtdTotalMed)
            spinnerunidadetotal.setSelection(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, unidadetotal).getPosition(medicamento!!.unidadeqtdtotalMed))
            spinnerlocaldeaplicacao.setSelection(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, localdeaplicacao).getPosition(medicamento!!.tipoLocalMed))
            spinnerembalagemmedicamento.setSelection(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, embalagemmed).getPosition(medicamento!!.embalagemMed))
            caixamedhorainicial.setText(medicamento!!.horaInicialMed)
            spinnernumerodevezesnodia.setText(medicamento!!.numVezesMed)
            caixamedqntdadosagem.setText(medicamento!!.dosagemMed)
            spinnerdosagem.setSelection(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, unidadedosagem).getPosition(medicamento!!.unidadedosagemMed))


            btnDelMed.setOnClickListener {
                databaseHandler1.delMedicamento(medicamento!!.idMedicamento)
                finish()
            }
        } else {
            btnDelMed.visibility = View.GONE
        }


        btnSaveMed.setOnClickListener {
            if (testeDataMedicamento()) {
                if (intent.getStringExtra("modeMed") == "EditMed") {
                    medicamento = populateMedicamentoMed(medicamento)
                    databaseHandler1.updateMedicamento(medicamento!!)

                } else {
                    medicamento = populateMedicamentoMed(null)
                    databaseHandler1.addMedicamento(medicamento!!)//Comando está funcionado, o problema é que ele não coloca no Main_Medicameno_tela_e_registro
                    aposapertarsalvar()
                }
                finish()
            } else {
                Toast.makeText(this, "Dados não correspondem", Toast.LENGTH_SHORT).show()
            }
        }


        btnCancelMed.setOnClickListener {
            finish()
        }

        spinnernomepacientemedicamento?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                textonomepacientemedicamento.setText(pacienteList[position].idPaciente.toString())
            }
        }

    }
    private fun aposapertarsalvar(){
        val intent=Intent(this,Main_Medicamento_tela_e_registro::class.java)
        startActivity(intent)
    }

    private fun testeDataMedicamento(): Boolean{

        return caixamednomemedicamento.text.toString() != ""
                && etValidade.text.toString().length == 10//Esta função serve para limitar os caracteres para 10, pode tirar o mask já faz esta função.
                && PeriodClass().checkPeriod(etValidade.text.toString())
                && Integer.parseInt(spinnernumerodevezesnodia.text.toString()) <= 24
                && spinnernumerodevezesnodia.text.toString() != ""
                && Integer.parseInt(spinnernumerodevezesnodia.text.toString()) >1
    }
    private fun testeDatabotaodahora(): Boolean {
       return  Integer.parseInt(spinnernumerodevezesnodia.text.toString()) <= 24
                && spinnernumerodevezesnodia.text.toString() != ""
                && Integer.parseInt(spinnernumerodevezesnodia.text.toString()) >1
    }

    private fun populateMedicamentoMed(p0: Medicamento?): Medicamento {
        val medicamento = Medicamento()
        if (p0 != null) medicamento.idMedicamento = p0!!.idMedicamento
        medicamento.idpacienteMedicamento = Integer.parseInt(textonomepacientemedicamento.text.toString())
        medicamento.nomeMed = caixamednomemedicamento.text.toString()
        medicamento.dataValidadeMed = etValidade.text.toString()
        medicamento.descMed = caixameddescricaomedicamento.text.toString()
        medicamento.qtdTotalMed = caixamedqnttotalmedicamento.text.toString()
        medicamento.unidadeqtdtotalMed = spinnerunidadetotal.selectedItem.toString()
        medicamento.tipoLocalMed = spinnerlocaldeaplicacao.selectedItem.toString()
        medicamento.embalagemMed = spinnerembalagemmedicamento.selectedItem.toString()
        medicamento.horaInicialMed = caixamedhorainicial.text.toString()
        medicamento.numVezesMed = spinnernumerodevezesnodia.text.toString()
        medicamento.unidadedosagemMed = spinnerdosagem.selectedItem.toString()
        medicamento.dosagemMed = caixamedqntdadosagem.text.toString()
        return medicamento
    }
    private fun initSpinnernomepacientemedicamento() {
        pacienteList = databaseHandler1.getPacienteList()!!
        var nomepacienteList: String
        var PacienteLista = ArrayList<String>()
        var tam = pacienteList.size - 1
        for (i in 0..tam) {
            nomepacienteList = pacienteList[i].nomePaciente
            PacienteLista.add(nomepacienteList)

        }
        spinnernomepacientemedicamento.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,PacienteLista)
    }
    private fun CreateWidgetAuto(Id : Int,  mContainer : LinearLayout) {
        val addedEditText = EditText(this)
        val addedTextView = TextView(this)
        addedTextView.setText( Id.toString() + "° Hora do Dia")
        addedTextView.setId(Id)
        addedTextView.layoutParams =
        LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
         LinearLayout.LayoutParams.WRAP_CONTENT)
        addedTextView.setTextColor(getResources().getColor(R.color.black))
        addedTextView.setTextAppearance(R.style.TamanhoLetra)
        mContainer.addView(addedTextView)
        addedEditText.setId(Id+24)

        addedEditText.layoutParams=LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        addedEditText.addTextChangedListener(Mask.mask("##h : ##min",addedEditText))
        addedEditText.inputType= InputType.TYPE_CLASS_NUMBER
addedEditText.setBackgroundResource(R.drawable.background_caixa)
        addedEditText.setTextAppearance(R.style.TamanhoLetracaixa)
        mContainer.addView(addedEditText)
    }
    private fun DeleteWidgetAuto(  mContainer : LinearLayout) {
        mContainer.removeAllViewsInLayout()
    }

}