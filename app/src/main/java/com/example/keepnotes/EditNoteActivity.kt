package com.example.keepnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.keepnotes.data.Note
import com.example.keepnotes.data.NoteDatabase
import com.example.keepnotes.databinding.ActivityEditNoteBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditNoteBinding
    private lateinit var originalNote : Note
    private lateinit var database : NoteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = NoteDatabase.getDatabase(this)
        originalNote = (intent.getSerializableExtra("note") as? Note)!!
        if (originalNote == null) {

            Log.e("EditNoteActivity", "Error: Unable to cast 'note' to Note")
            finish()
        } else {

            binding.editTitle.setText(originalNote.title)
            binding.editNote.setText(originalNote.note)

            binding.btnSave.setOnClickListener {
                updateNote()
            }
        }
    }
    private fun updateNote() {
        val updatedTitle = binding.editTitle.text.toString().trim()
        val updatedNoteText = binding.editNote.text.toString().trim()

        // Check if there are changes
        if (updatedTitle == originalNote.title && updatedNoteText == originalNote.note) {
            // No changes, finish the activity
            finish()
        } else {
            // Create an updated note object
            val updatedNote = Note(
                id = originalNote.id,
                title = updatedTitle,
                note = updatedNoteText
            )

            // Update the note in the database
            CoroutineScope(Dispatchers.IO).launch {
                database.noteDao().updateNote(updatedNote)
            }

            // Set the result as OK and finish the activity
            val resultIntent = Intent()
            resultIntent.putExtra("note", updatedNote)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}