package com.example.tela_de_cadastro_paciente_com_banco_dados.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tela_de_cadastro_paciente_com_banco_dados.R
import com.example.tela_de_cadastro_paciente_com_banco_dados.model.Responsavel
import kotlinx.android.synthetic.main.content_responsavel.view.*

class Main_do_content_responsavel(responsavelList: ArrayList<Responsavel>, internal var ctxResp: Context, private val callbackresponsavel: (Int) -> Unit) :
    RecyclerView.Adapter<Main_do_content_responsavel.ViewHolderResponsavel>() {
    private var responsavelList = ArrayList<Responsavel>()

    init {
        this.responsavelList = responsavelList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderResponsavel {
        val viewResp = LayoutInflater.from(ctxResp).inflate(R.layout.content_responsavel, parent, false)
        return ViewHolderResponsavel(viewResp)
    }

    override fun onBindViewHolder(holderResponsavel:ViewHolderResponsavel, positionResponsavel: Int) {
        val responsavel = responsavelList[positionResponsavel]
        holderResponsavel.idpacienteresponsavel.text = responsavel.idpacienteResponsavel.toString()
        holderResponsavel.nomepacienteresp.text = responsavel.nomepacienteResponsavel
        holderResponsavel.nomeResponsavelresp.text = responsavel.nomeResponsavel
        holderResponsavel.emailResponsavelresp.text = responsavel.emailResponsavel
        holderResponsavel.celResponsavelresp.text = responsavel.celResponsavel
        holderResponsavel.whatsappResponsavelresp.text = responsavel.whatsResponsavel
        holderResponsavel.tipoResponsavelresp.text = responsavel.tipoResponsavel
        holderResponsavel.caixamensagemdeemergenciaResponsavel.text = responsavel.caixamensagememergenciaResponsavel

//Quando clicar no layout já feito volta para a tela de cadastro
        holderResponsavel.layResponsavel.setOnClickListener {
            callbackresponsavel(responsavel.idResponsavel)
        }
//Aqui acaba o comando
        if (positionResponsavel == responsavelList.size - 1) holderResponsavel.endLineResponsavel.visibility = View.VISIBLE
    }

    override fun getItemCount(): Int {
        return responsavelList.size
    }

    inner class ViewHolderResponsavel(view: View) : RecyclerView.ViewHolder(view) {
        var layResponsavel: LinearLayout =view.layContentResponsavel
        var idpacienteresponsavel: TextView = view.tvContentidpacienteresponsavel
        var nomepacienteresp: TextView =view.tvContentNomePacienteResp
        var nomeResponsavelresp: TextView = view.tvContentNomeResponsavelResp
        var emailResponsavelresp: TextView = view.tvContentEmailResponsavelResp
        var celResponsavelresp: TextView = view.tvContentCelularResponsavelResp
        var whatsappResponsavelresp: TextView = view.tvContentWhatsappResponsavelResp
        var tipoResponsavelresp: TextView = view.tvContentTipoResponsavelResp
        var caixamensagemdeemergenciaResponsavel: TextView = view.tvContentmensagemdeemergenciaResp
        var endLineResponsavel: LinearLayout = view.endLine_responsavel

    }
}
