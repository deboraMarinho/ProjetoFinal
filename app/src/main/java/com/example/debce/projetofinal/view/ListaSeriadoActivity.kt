package com.example.debce.projetofinal.view

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.debce.projetofinal.R
import com.example.debce.projetofinal.adapter.SeriadoRecyclerAdapter
import com.example.debce.projetofinal.db.Seriado
import com.example.debce.projetofinal.viewmodel.SeriadoViewModel
import kotlinx.android.synthetic.main.activity_lista_seriado.*

class ListaSeriadoActivity : AppCompatActivity() {

    private lateinit var seriadoViewModel: SeriadoViewModel

    private val requestCodeSeriado = 1

    override fun onCreate(savedInstanceState: Bundle?){
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_lista_seriado)

    //incluir botão de voltar na pagina
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    val recyclerView = rvListaSeriado
    val adapter = SeriadoRecyclerAdapter(this)

    /**
     * ao clicar no item da lista, ele acessará o onItemClick
     * que atribuirá o objeto do item da lista à intent
     * e abrirá a nova activity passando o objeto
     * e receberá um retorno quando a activity for fechada
     */

    adapter.onItemClick =
    {
        val intent = Intent(this@ListaSeriadoActivity,
                NovoSeriadoActivity::class.java)
        intent.putExtra(NovoSeriadoActivity.EXTRA_REPLY, it)
        startActivityForResult(intent, requestCodeSeriado)
    }
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(this)

    seriadoViewModel = ViewModelProviders.of(this).get(SeriadoViewModel::
    class.java)


    seriadoViewModel.allSeriado.observe(this, Observer
    {
        seriados->
        seriados?.let { adapter.setSeriadoList(it) }
    })

    fbAddSeriado.setOnClickListener{
        val intent = Intent(this@ListaSeriadoActivity, NovoSeriadoActivity::class.java)
        //abre uma nova activity, mas espera um resultado que será validado com a chave
        //que estou enviando, requestCodeAddSeriado
        startActivityForResult(intent, requestCodeSeriado)
    }
}
        override fun onActivityResult(requestCode: Int, resultCode: Int,
                                      data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == requestCodeSeriado &&
                    resultCode == Activity.RESULT_OK){
                data.let {
                    try {
                        /**caso exista o obj recebido, adicione em um obj do tipo
                         * Seriado. Para isso precisamos pegar o dado serializado
                         * e feito cast de Seriado para dizer que ele de fato é o obj
                         * que pretendo receber
                         */
                        val seriado: Seriado = data?.getSerializableExtra(
                                NovoSeriadoActivity.EXTRA_REPLY) as Seriado

                        //se tiver id > 0, atualizo, caso contrario insiro
                        if (seriado.id > 0) seriadoViewModel.update(seriado)
                        else seriadoViewModel.insert(seriado)
                    } catch (e: Exception){
                        val seriado: Seriado = data?.getSerializableExtra(
                                NovoSeriadoActivity.EXTRA_DELETE) as Seriado
                        seriadoViewModel.delete(seriado)
                    }
                }
            }
        }
    }

