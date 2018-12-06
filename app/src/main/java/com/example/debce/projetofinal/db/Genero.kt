package com.example.debce.projetofinal.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "genero_table")
class Genero(
        @ColumnInfo(name = "nome")
        var nome: String,
        @ColumnInfo(name = "idade")
        var idade: String = ""
        ):Serializable{
            @PrimaryKey(autoGenerate = true)
            @ColumnInfo(name = "id")
            val id: Long = 0
}
