package com.example.keepnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.keepnotes.data.NoteDatabase
import com.example.keepnotes.databinding.ActivityMainBinding
import com.example.keepnotes.view.NoteAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: NoteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

            database = NoteDatabase.getDatabase(this)
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            CoroutineScope(Dispatchers.IO).launch {
                Log.e("getAll","data is visible")
                binding.recyclerView.adapter = NoteAdapter(database.noteDao().getAllNote())
            }
        binding.addNote.setOnClickListener {
            val intent = Intent(this,InsertActivity::class.java)
            startActivity(intent)
        }
    }
}