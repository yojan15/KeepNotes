package com.example.keepnotes.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
@Dao
interface NoteDao {
    @Insert
    fun insertNote(note: Note)
    @Update
    fun updateNote(note: Note)
    @Delete
    fun deleteNote(note: Note)
    @Query("select * from note order by id desc")
    fun getAllNote():MutableList<Note>
}