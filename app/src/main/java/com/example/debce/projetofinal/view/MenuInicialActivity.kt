package com.example.debce.projetofinal.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.debce.projetofinal.R
import kotlinx.android.synthetic.main.activity_menu_inicial.*

class MenuInicialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_inicial)

        btnSeriado.setOnClickListener {
            val intent = Intent(this, ListaSeriadoActivity::class.java)
            startActivity(intent)


        }

        btnGenero.setOnClickListener {
           /* val intent = Intent(this, )

             */
        }
    }
}
