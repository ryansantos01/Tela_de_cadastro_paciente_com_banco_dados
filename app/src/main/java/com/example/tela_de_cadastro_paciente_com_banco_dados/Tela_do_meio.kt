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

import android.widget.Toast
import com.example.tela_de_cadastro_paciente_com_banco_dados.db.DatabaseHandler
import com.example.tela_de_cadastro_paciente_com_banco_dados.model.Paciente
import com.example.tela_de_cadastro_paciente_com_banco_dados.model.Responsavel
import kotlinx.android.synthetic.main.activity_responsavel.*
import kotlinx.android.synthetic.main.activity_tela_do_meio.*
import kotlinx.android.synthetic.main.content_responsavel.*
import java.net.URLEncoder
import kotlinx.android.synthetic.main.activity_tela_do_meio.btnEmerg as btnEmerg1

class Tela_do_meio : AppCompatActivity() {

    val databaseHandler1 = DatabaseHandler(this)
    var responsavel: Responsavel? = null
    var pacienteList = ArrayList<Paciente>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_do_meio)
        btnPac1.setOnClickListener {
            val intent = Intent(this, Main_Paciente_tela_e_registro::class.java)
            startActivity(intent)
        }

        btnMedicamento.setOnClickListener {
            val intent = Intent(this, Main_Medicamento_tela_e_registro::class.java)
            startActivity(intent)
        }
        btnResponsavel.setOnClickListener {
            val intent = Intent(this, Main_Responsavel_tela_e_registro::class.java)
            startActivity(intent)
        }
        btnPatologia.setOnClickListener {
            val intent = Intent(this, Main_Patologia_tela_e_registro::class.java)
            startActivity(intent)
        }
        btnLogout.setOnClickListener {
            finishAffinity();
            System.exit(0);
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