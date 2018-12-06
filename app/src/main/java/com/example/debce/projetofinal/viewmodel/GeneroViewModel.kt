package com.example.debce.projetofinal.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.debce.projetofinal.db.Genero
import com.example.debce.projetofinal.db.GeneroDatabase
import com.example.debce.projetofinal.repository.GeneroRepository
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import kotlin.coroutines.experimental.CoroutineContext

class GeneroViewModel(application: Application): AndroidViewModel(application) {
    private val repository: GeneroRepository
    val allGeneros: LiveData<List<Genero>>

    private var parentJob = Job()

    private val coroutineContext: CoroutineContext
    get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    init {
        val generoDao = GeneroDatabase.getDatabase(application, scope).generoDao()

        repository = GeneroRepository(generoDao)
        allGeneros = repository.allGeneros
    }

    fun insert(genero: Genero) = scope.launch(Dispatchers.IO){
        repository.insert(genero)
    }

    fun update(genero: Genero) = scope.launch(Dispatchers.IO){
        repository.update()
    }
}