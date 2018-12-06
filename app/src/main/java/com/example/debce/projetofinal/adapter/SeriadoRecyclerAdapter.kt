package com.example.debce.projetofinal.adapter

import android.content.Context
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.debce.projetofinal.R
import com.example.debce.projetofinal.db.Seriado
import kotlinx.android.synthetic.main.item_lista_seriado.view.*

class SeriadoRecyclerAdapter internal constructor(context: Context):
RecyclerView.Adapter<SeriadoRecyclerAdapter.ViewHolder>(){

    //Novo, usado para add o método onClick no item da lista na "ListaSeriadoActivity"
    var onItemClick: ((Seriado) -> Unit)?= null

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    //Cachear os elementos
    private var seriados = emptyList<Seriado>()

    override fun onCreateViewHolder(holder: ViewGroup, position: Int): ViewHolder{
        val view = inflater.inflate(R.layout.item_lista_seriado, holder, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = seriados.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = seriados[position]
        holder.nomeSeriado.text = current.nome
    }

    //classe para mapear os componentes do item da lista
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nomeSeriado: TextView = itemView.txtSeriadoListaNome

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(seriados[adapterPosition])
            }
        }
    }

    fun setSeriadoList(seriadoList: List<Seriado>){
        this.seriados = seriadoList
        notifyDataSetChanged()
    }
}