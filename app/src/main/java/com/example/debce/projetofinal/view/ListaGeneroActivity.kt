package com.example.debce.projetofinal.view

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.debce.projetofinal.R
import com.example.debce.projetofinal.adapter.GeneroRecyclerAdapter
import com.example.debce.projetofinal.db.Genero
import com.example.debce.projetofinal.viewmodel.GeneroViewModel
import kotlinx.android.synthetic.main.activity_lista_genero.*
import kotlinx.android.synthetic.main.activity_lista_seriado.*

class ListaGeneroActivity : AppCompatActivity() {

    private lateinit var generoViewModel: GeneroViewModel

    private  val requestCodeGenero = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_genero)

        val recyclerView = rvListaGenero
        val adapter = GeneroRecyclerAdapter(this)
        /**
         *  ao clicar no item da lista, ele acessará o onItemClick
         *  que atribuirá o objeto do item da lista à intent
         *  e abrirá a nova activity passando o objeto
         *  e receberá um retorno quando a activity for fechada
         */
        adapter.onItemClick = {
            val intent = Intent( this@ListaGeneroActivity,
                    NovoGeneroActivity::class.java)
            intent.putExtra(NovoGeneroActivity.EXTRA_REPLY, it)
            startActivityForResult(intent, requestCodeGenero)
            }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        generoViewModel = ViewModelProviders.of(this).
                get(GeneroViewModel::class.java)

        generoViewModel.allGeneros.observe(this, Observer { generos->
            generos?.let { adapter.setGeneroList(it) }
        })

        fbAddSeriado.setOnClickListener {
            val intent = Intent(this@ListaGeneroActivity, NovoGeneroActivity::class.java)
            // abre uma nova activity, mas espera um resultado que será validado com a chave
            // que estou enviand - requestCodeAddAmigo
            startActivityForResult(intent, requestCodeGenero)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == requestCodeGenero && resultCode == Activity.RESULT_OK){
            data.let {
                try {
                    /** caso exista o objeto recebido, adicione-o em um objeto do tipo
                     * Friend. Para isso precisaremos pegar o dado serializado
                     * e feito cast de Friend para dizer que ele de fato é o objeto
                     * que pretendo receber
                     */
                    val genero: Genero = data?.getSerializableExtra(
                            NovoGeneroActivity.EXTRA_REPLY) as Genero
                    )
                }
            }
        }
    }
}
