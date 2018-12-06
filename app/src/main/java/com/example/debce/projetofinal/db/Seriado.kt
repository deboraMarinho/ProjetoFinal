package com.example.debce.projetofinal.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "seriado_table")
class Seriado (
        @ColumnInfo(name = "nome")
        var nome: String,
        @ColumnInfo(name = "ano")
        var ano: String = "",
        @ColumnInfo(name = "episodio")
        var episodio: String = ""
        ): Serializable{
            @PrimaryKey(autoGenerate = true)
            @ColumnInfo(name = "id")
            val id: Long = 0
}

