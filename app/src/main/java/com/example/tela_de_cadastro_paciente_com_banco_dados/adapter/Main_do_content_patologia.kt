package com.example.tela_de_cadastro_paciente_com_banco_dados.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tela_de_cadastro_paciente_com_banco_dados.R
import com.example.tela_de_cadastro_paciente_com_banco_dados.model.Patologia
import kotlinx.android.synthetic.main.content_patologia.view.*
import org.w3c.dom.Text

class Main_do_content_patologia(patologiaList: ArrayList<Patologia>, internal var ctx: Context, private val callbackpatologia:(Int)-> Unit):
    RecyclerView.Adapter<Main_do_content_patologia.ViewHolder>() {
    private var patologiaList = ArrayList<Patologia>()

    init {
        this.patologiaList = patologiaList

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewPat = LayoutInflater.from(ctx).inflate(R.layout.content_patologia, parent, false)
        return ViewHolder(viewPat)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val patologia = patologiaList[position]
        holder.idpacientepatologia.text= patologia.idpacientePatologia.toString()
        holder.nomepacientepatologia.text = patologia.nomePacientePatologia
        holder.nomemedicopatologia.text = patologia.nomeMedicoPatologia
        holder.nomepatologia.text = patologia.nomePatologia
        holder.descPatologia.text = patologia.descPatologia
        holder.dataultimoexamePatologia.text = patologia.dataUltimoExame

//Quando clicar no layout já feito volto para a tela de cadastro
        holder.layPatologia.setOnClickListener {
            callbackpatologia(patologia.idPatologia)
        }
//Aqui acaba o comando
        if (position == patologiaList.size - 1) holder.endLinePatologia.visibility = View.VISIBLE
    }
    override fun getItemCount(): Int {
        return patologiaList.size
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var layPatologia: LinearLayout = view.layContentPatologia
        var idpacientepatologia: TextView = view.tvContenttextidpacientepatologia
        var nomepacientepatologia: TextView = view.tvContentnomepacientePat
        var nomemedicopatologia: TextView = view.tvContentnomemedicoPaT
        var nomepatologia: TextView = view.tvContentnomepatalogiaPat
        var descPatologia: TextView = view.tvContentdescricaoPat
        var dataultimoexamePatologia: TextView = view.tvContentdataultimoexamePat
        var endLinePatologia: LinearLayout = view.endLine_patologia
    }
}
