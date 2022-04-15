package com.example.tela_de_cadastro_paciente_com_banco_dados


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsManager
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.tela_de_cadastro_paciente_com_banco_dados.db.DatabaseHandler
import com.example.tela_de_cadastro_paciente_com_banco_dados.function.Mask
import com.example.tela_de_cadastro_paciente_com_banco_dados.model.Paciente
import com.example.tela_de_cadastro_paciente_com_banco_dados.model.Responsavel
import kotlinx.android.synthetic.main.activity_responsavel.*
import kotlinx.android.synthetic.main.activity_tela_do_meio.*
import kotlinx.android.synthetic.main.content_responsavel.*
import java.net.URLEncoder
import kotlinx.android.synthetic.main.activity_responsavel.btnEmerg as btnEmerg1

class Responsavel_Activity : AppCompatActivity() {

    val databaseHandler1 = DatabaseHandler(this)
    var responsavel: Responsavel? = null
    var pacienteList = ArrayList <Paciente>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_responsavel)
        initSpinnernomepacienteresponsavel()

        caixarespwhatsappresponsavel.addTextChangedListener(Mask.mask("(##)#####-####", caixarespwhatsappresponsavel))
        caixarespcelularresponsavel.addTextChangedListener(Mask.mask( "(##)#####-####", caixarespcelularresponsavel))//(##)#####-####



        var tiporesponsavel = arrayOf("", "AMIGO(A)", "PARENTE", "CONJUGÊ", "RESPONSÁVEL CLÍNICO")
        spinnertiporesponsavel.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tiporesponsavel)

        //Configuração dos botões na tela


        if (intent.getStringExtra("mode") == "Edit") {
            responsavel = databaseHandler1.getResponsavel(intent.getIntExtra("id", 0))
            textonomepacienteresponsavel.setText(responsavel!!.idpacienteResponsavel.toString())
            fun initSpinnernomepacienteresponsavel() {
                pacienteList = databaseHandler1.getPacienteList()!!
                var nomepacienteList: String
                var PacienteLista = ArrayList<String>()
                var tam = pacienteList.size - 1
                for (i in 0..tam) {
                    nomepacienteList = pacienteList[i].nomePaciente
                    PacienteLista.add(nomepacienteList)

                }
                spinnernomepacienteresponsavel.setSelection(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, PacienteLista).getPosition(responsavel!!.nomepacienteResponsavel)
                )

            }
            caixarespnomeresponsavel.setText(responsavel!!.nomeResponsavel)
            caixarespemailresponsavel.setText(responsavel!!.emailResponsavel)
            caixarespcelularresponsavel.setText(responsavel!!.celResponsavel)
            caixarespwhatsappresponsavel.setText(responsavel!!.whatsResponsavel)
            spinnertiporesponsavel.setSelection(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tiporesponsavel).getPosition(responsavel!!.tipoResponsavel))
            caixamensagemEmergencia.setText(responsavel!!.caixamensagememergenciaResponsavel)
//Deletar Pessoa
            btnDelResp.setOnClickListener {
                databaseHandler1.delResponsavel(responsavel!!.idResponsavel)
                finish()
            }
        }
        else {
            btnDelResp.visibility = View.GONE
            layemerg.visibility=View.GONE

        }


        //Salvar Pessoa
        btnSaveResp.setOnClickListener {

            if (testeResp()) {
                if (intent.getStringExtra("mode") == "Edit") {
                    responsavel = populateResponsavel(responsavel)
                    databaseHandler1.updateResponsavel(responsavel!!)
                } else {
                    responsavel = populateResponsavel(null)
                    databaseHandler1.addResponsavel(responsavel!!)
                    aposapertarsalvar()
                }
                finish()

            } else {
                Toast.makeText(this, "Dados não correspondem", Toast.LENGTH_SHORT).show()
            }
        }
        btnCancelResp.setOnClickListener {
            finish()
        }
        spinnernomepacienteresponsavel?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    textonomepacienteresponsavel.setText(pacienteList[position].idPaciente.toString())

                }
            }

        btnEmerg.setOnClickListener {

            var sms = SmsManager.getDefault()
            sms.sendTextMessage(
                caixarespwhatsappresponsavel.text.toString(),
                "ME",
                caixamensagemEmergencia.text.toString(),
                null,
                null
            )
            Toast.makeText(this, "SMS enviado com Sucesso!", Toast.LENGTH_LONG).show()

            when {
                caixarespwhatsappresponsavel.text.isNullOrEmpty() -> {
                    showMessage(getString(R.string.please_enter_mobile_number))
                }
                caixarespwhatsappresponsavel.text?.length != 11 -> {
                    showMessage(getString(R.string.please_enter_valid_mobile_number))
                }
                else -> {
                    val url =
                        "https://api.whatsapp.com/send?phone=+55" + caixarespwhatsappresponsavel.text.toString() + "&text=" + URLEncoder.encode(
                            caixamensagemEmergencia.text.toString(),
                            "UTF-8"
                        )
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        setPackage("com.whatsapp")
                        data = Uri.parse(url)
                    }
                    intent.resolveActivity(packageManager)?.let {
                        startActivity(intent)
                    }
                }
            }
        }

    }

    private fun aposapertarsalvar(){
        val intent= Intent(this,Main_Responsavel_tela_e_registro::class.java)
        startActivity(intent)
    }
    private fun testeResp(): Boolean{
        return  caixarespnomeresponsavel.text.toString() != ""
    }
    private fun populateResponsavel(p0: Responsavel?): Responsavel {
        val responsavel = Responsavel()
        if (p0 != null) responsavel.idResponsavel = p0!!.idResponsavel
        responsavel.idpacienteResponsavel=Integer.parseInt(textonomepacienteresponsavel.text.toString())
        responsavel.nomeResponsavel = caixarespnomeresponsavel.text.toString()
        responsavel.emailResponsavel = caixarespemailresponsavel.text.toString()
        responsavel.celResponsavel = caixarespcelularresponsavel.text.toString()
        responsavel.whatsResponsavel = caixarespwhatsappresponsavel.text.toString()
        responsavel.tipoResponsavel = spinnertiporesponsavel.selectedItem.toString()
        responsavel.caixamensagememergenciaResponsavel = caixamensagemEmergencia.text.toString()
        return responsavel
    }
    private fun initSpinnernomepacienteresponsavel() {
        pacienteList = databaseHandler1.getPacienteList()!!
        var nomepacienteList: String
        var PacienteLista = ArrayList<String>()
        var tam = pacienteList.size - 1
        for (i in 0..tam) {
            nomepacienteList = pacienteList[i].nomePaciente
            PacienteLista.add(nomepacienteList)

        }
        spinnernomepacienteresponsavel.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,PacienteLista)

    }
    private var toast: Toast? = null

    fun showMessage(msg: String) {
        toast?.cancel()
        toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
        toast?.show()
    }

    //parte da permissão para enviar sms
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==111 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
            receiveMsg()

    }

    private fun receiveMsg(){
        var br = object: BroadcastReceiver(){

            override fun onReceive(p0: Context?, p1: Intent?) {
                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT){
                    for (sms in Telephony.Sms.Intents.getMessagesFromIntent(p1)){
                        caixarespwhatsappresponsavel.setText(sms.originatingAddress)
                        mensagemEmergencia.setText("*"+sms.displayMessageBody+"*")
                    }
                }

            }


        }

        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))


    }

}



