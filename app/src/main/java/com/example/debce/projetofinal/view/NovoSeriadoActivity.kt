package com.example.debce.projetofinal.view

import android.Manifest
import android.app.Activity
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.debce.projetofinal.R
import com.example.debce.projetofinal.db.Seriado
import kotlinx.android.synthetic.main.activity_lista_seriado.*
import kotlinx.android.synthetic.main.activity_novo_seriado.*
import java.lang.Exception


class NovoSeriadoActivity : AppCompatActivity() {

    private var image_uri : Unit? = null
    private var mCurrentPhotoPath: String = ""

    lateinit var seriado: Seriado

    //canal para se trabalhar com a notificação, utilizado a partir do Oreo
    private val channelId = "com.example.debce.projetofinal"

    companion object {
        //image pick code
        private val REQUEST_IMAGE_GARELLY = 1000
        private val REQUEST_IMAGE_CAPTURE = 2000
        const val EXTRA_REPLY = "view.REPLY"
        const val EXTRA_DELETE = "view.DELETE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_seriado)

        //botão voltar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fAddSeriado?.setOnClickListener {
            fAddSeriado.setOnCreateContextMenuListener { menu, v, menuInfo ->
                menu.add(Menu.NONE, 1, Menu.NONE, "Escolher foto")
                menu.add(Menu.NONE, 2, Menu.NONE, "Tirar Foto")
            }
        }

        val intent:Intent = getIntent()
        try {
            //recebe o obj da intent
            seriado = intent.getSerializableExtra(EXTRA_REPLY) as Seriado

            //para cada item do formulario, é add o valor do atributo do obj
            seriado.let {
                etNomeSeriado.setText(seriado.nome)
                etAnoLancamento.setText(seriado.ano)
                etEpisodio.setText(seriado.episodio)
            }
        }catch (e:Exception){

        }
    }

    private fun pickImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "images/*"
        startActivityForResult(intent, REQUEST_IMAGE_GARELLY)
    }

    private fun takePicture(){
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE,"Nova Imagem")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Imagem da camera")
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")

        image_uri = contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values)
        
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (intent.resolveActivity(packageManager) != null){
            mCurrentPhotoPath = image_uri.toString()
            intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
            or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    private fun getPermissionImageFromGallery(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                //permissão negada
                val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permission, REQUEST_IMAGE_GARELLY)
            }else {
                //permissão aceita
                pickImageFromGallery()
            }
        } else{
            pickImageFromGallery()
        }
    }

    private fun getPermissionTakePicture(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
              //permissao negada
                val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permission, REQUEST_IMAGE_CAPTURE)
            }else {
                //permissao aceita
                takePicture()
            }
        } else{

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            REQUEST_IMAGE_GARELLY -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) pickImageFromGallery()
                else Toast.makeText(this, "Permissão Negada", Toast.LENGTH_SHORT).show()
                }
            REQUEST_IMAGE_CAPTURE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) takePicture()
                else Toast.makeText(this, "Permissão Negada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_GARELLY) imgNovoSeriado.setImageURI(data?.data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE) imgNovoSeriado.setImageURI(image_uri)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            1 -> getPermissionImageFromGallery()
            2 -> getPermissionTakePicture()
        }
        return super.onContextItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_novo_seriado, menu)
        try{
            seriado.let {
                //torna o botao do item do menu visivel
                val menuItem = menu?.findItem(R.id.menu_seriado_delete)
                menuItem?.isVisible = true
            }
        }catch (e: Exception){
            //torna o botao do item do menu invisivel
            val menuItem = menu?.findItem(R.id.menu_seriado_delete)
            menuItem?.isVisible = false
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home){
            finish()
            true
        }
        else if (item?.itemId == R.id.menu_seriado_save){
            if(etNomeSeriado.text.isNullOrEmpty()) Toast.makeText(this,
                    "Insira o nome do Seriado",
                    Toast.LENGTH_LONG).show()
            else{
                /**
                 * caso esteja o mínimo preenchido, verifica se
                 * existe algo no id, se já existir, apenas atualize o objeto
                 * caso contrario, instancie um novo objeto
                 *
                 * por fim, retorne a intent com o objeto populado
                 */
                if ((::seriado.isInitialized) && (seriado.id > 0)){
                    seriado.ano = etAnoLancamento.text.toString()
                    //add a notificação
                    notificacao(channelId, seriado.nome, "Seriado Alterado")
                }
                else{
                    seriado = Seriado(
                            nome = etNomeSeriado.text.toString(),
                            ano = etAnoLancamento.text.toString(),
                            episodio = etEpisodio.text.toString()
                    )
                    //add a notificação
                    notificacao(channelId, seriado.nome, "Seriado Novo")
                }
                //criando uma intent para inserir os dados de resposta
                val replyIntent = Intent()
                //inserindo na intent a chave (EXTRA_REPLY) e o valor (seriado)
                replyIntent.putExtra(EXTRA_REPLY, seriado)
                //enviando os dados
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
            true
        }
        else if(item?.itemId == R.id.menu_seriado_delete){
            val replyIntent = Intent()
            replyIntent.putExtra(EXTRA_DELETE, seriado)
            setResult(Activity.RESULT_OK, replyIntent)
            true
            }
        else{
            super.onOptionsItemSelected(item)
        }
    }

    fun notificacao(){
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        //verifica se é a versão Oreo ou superior
        val mNotificationId = 1000

        val mNotification = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Notification.Builder(this, channelId)
        } else {
            Notification.Builder(this)
        }.apply {
            setContentIntent(pendingIntent)
            setSmallIcon(R.drawable.notification_icon_background)
            setAutoCancel(true)
            setContentText("Adicionado")
        }.build()
        val nManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nManager.notify(mNotificationId, mNotification)
    }
}
