package com.example.keepnotes.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.keepnotes.R
import com.example.keepnotes.data.Note
import com.example.keepnotes.data.NoteDatabase

class NoteAdapter(private var notes : MutableList<Note>) :RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    inner class NoteViewHolder(noteView : View) : RecyclerView.ViewHolder(noteView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes,parent,false)
        return NoteViewHolder(view)
    }
    override fun getItemCount(): Int {
       return notes.size
    }
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.itemView.apply {
            val title = findViewById<TextView>(R.id.noteTitle)
            val note = findViewById<TextView>(R.id.noteText)
            title.text = notes[position].toString()
            note.text = notes[position].toString()
        }
    }
}