package com.example.keepnotes.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.keepnotes.R

class NotesAdapter(private var notes: MutableList<com.example.keepnotes.data.Note>) :RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    inner class NotesViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes , parent , false)
        return NotesViewHolder(view)
    }
    override fun getItemCount(): Int {
       return notes.size
    }
    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.itemView.apply {
            val noteTitle = findViewById<TextView>(R.id.noteTitle)
            val noteText = findViewById<TextView>(R.id.noteText)

            noteTitle.text = notes[position].toString()
            noteText.text = notes[position].toString()
        }
    }
}