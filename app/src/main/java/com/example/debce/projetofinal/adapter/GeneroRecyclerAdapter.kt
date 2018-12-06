package com.example.debce.projetofinal.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.debce.projetofinal.R
import com.example.debce.projetofinal.db.Genero
import kotlinx.android.synthetic.main.item_lista_genero.view.*


class GeneroRecyclerAdapter internal constructor(context: Context):
RecyclerView.Adapter<GeneroRecyclerAdapter.ViewHolder>(){

    //novo, usado para add o metodo onClick no item da lista na "ListaGeneroActivity"
    var onItemClick: ((Genero) -> Unit)?= null

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var generos = emptyList<Genero>()//cachear os elementos

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): GeneroRecyclerAdapter.ViewHolder {
        val view = inflater.inflate(R.layout.item_lista_genero, holder,
                false)
        return ViewHolder(view)
         }

    override fun getItemCount() = generos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = generos[position]
        holder.nomeGenero.text = current.nome
    }
    //classe para mapear os componentes do item da lista
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nomeGenero: TextView = itemView.txtGeneroLista

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(generos[adapterPosition])
            }
        }
    }
    fun setGeneroList(generoList: List<Genero>){
        this.generos = generoList
        notifyDataSetChanged()
    }
}