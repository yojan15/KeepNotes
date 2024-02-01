package com.example.keepnotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.keepnotes.data.Note
import com.example.keepnotes.data.NoteDatabase
import com.example.keepnotes.databinding.ActivityInsertBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class InsertActivity : AppCompatActivity() {
    private lateinit var binding : ActivityInsertBinding
    private lateinit var database: NoteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = NoteDatabase.getDatabase(this)
        binding.insert.setOnClickListener {
            val insertTitle = binding.title.text.toString()
            val insertText = binding.note.text.toString()

            if (insertText.isNotBlank()) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        database.noteDao().insertNote(Note(0, insertTitle, insertText))
                        Log.e("inserted", "Note Added")
                        binding.title.text.clear()
                        binding.note.text.clear()
                        Toast.makeText(this@InsertActivity, "Note Added", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Log.e("inserted", "Error inserting note: ${e.message}")
                        e.printStackTrace()
                        Toast.makeText(this@InsertActivity, "Error adding note", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
