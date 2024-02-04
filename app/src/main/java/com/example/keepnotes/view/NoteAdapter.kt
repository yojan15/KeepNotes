package com.example.keepnotes.view
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.keepnotes.R
import com.example.keepnotes.data.Note
import com.example.keepnotes.data.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteAdapter(private var notes : MutableList<Note> , private val database: NoteDatabase )
    :RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(noteView: View) : RecyclerView.ViewHolder(noteView)

    fun updateNoteAtPosition(position: Int, updatedNote: Note) {
        if (position in 0 until notes.size) {
            notes[position] = updatedNote
            notifyDataSetChanged() // Notify the adapter that the data set has changed
            // Update the note in the database
            CoroutineScope(Dispatchers.IO).launch {
                database.noteDao().updateNote(updatedNote)
            }
        }
    }

    // Handle click events for updating notes
    interface OnNoteClickListener {
        fun onNoteClick(position: Int)
    }

    private var onNoteClickListener: OnNoteClickListener? = null

    fun setOnNoteClickListener(listener: OnNoteClickListener) {
        onNoteClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.itemView.apply {
            val title = findViewById<TextView>(R.id.noteTitle)
            val note = findViewById<TextView>(R.id.noteText)

            val currentNote = notes[position]
            Log.e("position", "${notes.size}")
            title.text = currentNote.title
            note.text = currentNote.note

            setOnClickListener {
                onNoteClickListener?.onNoteClick(position)
            }
        }

    }
    // fun deleteTodoPosition(position: Int) {
//        val deleteNote = notes.removeAt(position)
//        notifyItemRemoved(position)
//        CoroutineScope(Dispatchers.IO).launch {
//            database.noteDao().deleteNote(deleteNote)
    //}
//        CoroutineScope(Dispatchers.IO).launch {
//            database.noteDao().deleteNote(notes.removeAt(position))
//        }
//
//        Log.e("delete","${notes.size}")
//        val deleteNote = notes.removeAt(position)
//        val databaseAccess = database.noteDao()
//        databaseAccess.deleteNote(deleteNote)
//
//        notes.removeAt(position)
//        notifyItemRemoved(position)

    fun deleteNoteAtPosition(position: Int) {
        if (position in 0 until notes.size) {
            val deletedNote = notes.removeAt(position)
            notifyDataSetChanged() // Notify the adapter that the data set has changed
            // Delete the note from the database
            CoroutineScope(Dispatchers.IO).launch {
                database.noteDao().deleteNote(deletedNote)
            }
        }
    }
}