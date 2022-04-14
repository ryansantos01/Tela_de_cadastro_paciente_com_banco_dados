package com.example.tela_de_cadastro_paciente_com_banco_dados

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.tela_de_cadastro_paciente_com_banco_dados.db.DatabaseHandler
import com.example.tela_de_cadastro_paciente_com_banco_dados.function.Mask
import com.example.tela_de_cadastro_paciente_com_banco_dados.function.PeriodClass
import com.example.tela_de_cadastro_paciente_com_banco_dados.model.Paciente
import com.example.tela_de_cadastro_paciente_com_banco_dados.model.Patologia
import kotlinx.android.synthetic.main.activity_patologia.*

class Patologia_Activity : AppCompatActivity() {
    var patologia: Patologia? = null
    var paciente: Paciente? = null
    val databaseHandler1 = DatabaseHandler(this)
    var pacienteList = ArrayList <Paciente>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patologia)
        //Configuração dos botões na tela
initSpinnernomepacientepatologia()
//Faz com que crie barras ao digitar a data do último exame
        caixapatdataultimoexame.addTextChangedListener(Mask.mask("##/##/####", caixapatdataultimoexame))

        if (intent.getStringExtra("modePat") == "EditPat") {
            patologia = databaseHandler1.getPatologia(intent.getIntExtra("idPat", 0))
            textonomepacientepatologia.setText(patologia!!.idpacientePatologia.toString())
            fun initSpinnernomepacientepatologia() {
                pacienteList = databaseHandler1.getPacienteList()!!
                var nomepacienteList: String
                var PacienteLista = ArrayList<String>()
                var tam = pacienteList.size - 1
                for (i in 0..tam) {
                    nomepacienteList = pacienteList[i].nomePaciente
                    PacienteLista.add(nomepacienteList)

                }
                spinnerpatnomepaciente.setSelection(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,PacienteLista).getPosition(patologia!!.nomePacientePatologia))
            }
            caixapatnomemedico.setText(patologia!!.nomeMedicoPatologia)
            caixapatnomepatologia.setText(patologia!!.nomePatologia)
            caixapatdescricaopatologia.setText(patologia!!.descPatologia)
            caixapatdataultimoexame.setText(patologia!!.dataUltimoExame)

//Deletar Pessoa
            btnDelPat.setOnClickListener {
                databaseHandler1.delPatologia(patologia!!.idPatologia)
                finish()
            }
        } else {
            btnDelPat.visibility = View.GONE
        }
        //Salvar Pessoa
        btnSavePat.setOnClickListener {

            if (testePat()) {
                if (intent.getStringExtra("modePat") == "EditPat") {
                    patologia = populatePatologia(patologia)
                    databaseHandler1.updatePatologia(patologia!!)
                }else{
                    patologia = populatePatologia(null)
                    databaseHandler1.addPatologia(patologia!!)
                    aposapertarsalvar()
                }
                finish()

            }
            else {
                Toast.makeText(this, "Dados não correspondem", Toast.LENGTH_SHORT).show()
            }

        }
        btnCancelPat.setOnClickListener {
            finish()
        }
        spinnerpatnomepaciente?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                textonomepacientepatologia.setText(pacienteList[position].idPaciente.toString())

            }
        }
    }
    private fun aposapertarsalvar(){
        val intent= Intent(this,Main_Patologia_tela_e_registro::class.java)
        startActivity(intent)
    }
    private fun testePat(): Boolean{

        return caixapatnomepatologia.text.toString() != ""
                && PeriodClass().checkPeriod(caixapatdataultimoexame.text.toString())
    }
    private fun populatePatologia(p0: Patologia?): Patologia{
        val patologia = Patologia()
        if (p0 != null) patologia.idPatologia = p0!!.idPatologia
        patologia.idpacientePatologia=Integer.parseInt(textonomepacientepatologia.text.toString())
        patologia.nomeMedicoPatologia = caixapatnomemedico.text.toString()
        patologia.nomePatologia = caixapatnomepatologia.text.toString()
        patologia.descPatologia = caixapatdescricaopatologia.text.toString()
        patologia.dataUltimoExame = caixapatdataultimoexame.text.toString()
        return patologia
    }
    private fun initSpinnernomepacientepatologia() {
        pacienteList = databaseHandler1.getPacienteList()!!
        var nomepacienteList: String
        var PacienteLista = ArrayList<String>()
        var tam = pacienteList.size - 1
        for (i in 0..tam) {
            nomepacienteList = pacienteList[i].nomePaciente
            PacienteLista.add(nomepacienteList)

        }
        spinnerpatnomepaciente.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,PacienteLista)

    }
}

