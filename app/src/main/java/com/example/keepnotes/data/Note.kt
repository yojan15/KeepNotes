package com.example.keepnotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val title : String,
    val note : String
):Serializable
