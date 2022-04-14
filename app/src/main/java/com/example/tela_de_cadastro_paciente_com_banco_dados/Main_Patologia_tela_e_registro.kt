package com.example.tela_de_cadastro_paciente_com_banco_dados

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tela_de_cadastro_paciente_com_banco_dados.adapter.Main_do_content_patologia
import com.example.tela_de_cadastro_paciente_com_banco_dados.db.DatabaseHandler
import com.example.tela_de_cadastro_paciente_com_banco_dados.model.Patologia
import kotlinx.android.synthetic.main.activitypatologiatelaeregistro.*

class Main_Patologia_tela_e_registro : AppCompatActivity() {
    var patologiaList = ArrayList<Patologia>()
    var maindocontentpatologia: Main_do_content_patologia? = null
    var LinearLayoutPatologia: LinearLayoutManager? = null
    var databaseHandler1 = DatabaseHandler(this )
    var searchPat: Boolean = false
    var searchStrPat: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitypatologiatelaeregistro)


        fabVoltarPat.setOnClickListener{
            val intent = Intent(this, Tela_do_meio::class.java)
            startActivity(intent)//   startActivity(Intent(this,Main_Medicamento_tela_e_registro::class.java))
        }


        fabPatologia.setOnClickListener{
            val intent = Intent(this, Patologia_Activity::class.java)
            startActivity(intent)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_view_patologia,menu)
        val searchPatologia = menu.findItem(R.id.search_patologia)
        val searchView = searchPatologia.actionView as SearchView
        searchView.queryHint = getString(R.string.searchTitle)



        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?) : Boolean{
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if(p0.toString().isNotEmpty()){
                    searchPat = true
                    searchStrPat = p0!!
                }
                else{
                    searchPat = false
                    searchStrPat = ""

                }
                initViewPatologia()
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }



    override fun onResume() {
        super.onResume()
        initViewPatologia()
    }


    private fun initViewPatologia(){
        patologiaList = if (!searchPat) databaseHandler1.getPatologiaList()
        else databaseHandler1.searchPatologia(searchStrPat)
        maindocontentpatologia = Main_do_content_patologia(patologiaList, this, this::editPatologia)
        LinearLayoutPatologia = LinearLayoutManager(this)
        recyclerViewPatologia.layoutManager = LinearLayoutPatologia
        recyclerViewPatologia.adapter = maindocontentpatologia
    }
    //Troca de activity para editar
    private fun editPatologia(id:Int){
        val intent = Intent(this, Patologia_Activity::class.java)
        intent.putExtra("modePat","EditPat")
        intent.putExtra("idPat",id)
        startActivity(intent)

    }

}