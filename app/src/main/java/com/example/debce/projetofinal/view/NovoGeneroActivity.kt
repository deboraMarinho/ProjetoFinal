package com.example.debce.projetofinal.view

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.example.debce.projetofinal.R
import com.example.debce.projetofinal.db.Genero
import kotlinx.android.synthetic.main.activity_novo_genero.*

class NovoGeneroActivity : AppCompatActivity() {

    lateinit var genero: Genero

    //canal para trabalhar com a notificação, utilizado a partir do Oreo
    private val channelId = "com.example.debce.projetofinal"

    companion object {
        const val EXTRA_REPLY = "view.REPLY"
        const val EXTRA_DELETE = "view.DELETE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_genero)

        //botão de voltar ativo no menu superior esquerdo
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent:Intent = getIntent()
        try {
            //receber o obj da intent
            genero = intent.getSerializableExtra(EXTRA_REPLY) as Genero
            //para cada item do formulario, add o valor do atributo do obj
            genero.let {
                etGenero.setText(genero.nome)
                etFaixaEtaria.setText(genero.idade)
            }
        }catch (e:Exception){

        }
    }

    fun notificacao(){
        val pendingIntent = PendingIntent.getActivity(this,
               0,intent, 0 )
        //verifica se é a versao Oreo ou Superior
        val mNotificationId = 1000

        val mNotification = if (Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.O){
            Notification.Builder(this, channelId)
        }else{
            Notification.Builder(this)
        }.apply {
            setContentIntent(pendingIntent)
            setSmallIcon(R.drawable.notification_icon_background)
            setAutoCancel(true)
            setContentText("Adicionado")
        }.build()

        val nManager = this.getSystemService(
                Context.NOTIFICATION_SERVICE
        )as NotificationManager
        nManager.notify(mNotificationId, mNotification)
    }
}
