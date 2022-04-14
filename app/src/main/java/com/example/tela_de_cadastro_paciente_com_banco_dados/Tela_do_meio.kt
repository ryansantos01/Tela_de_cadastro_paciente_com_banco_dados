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
    }

}