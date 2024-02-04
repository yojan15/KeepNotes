package com.example.keepnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.keepnotes.data.Note
import com.example.keepnotes.data.NoteDatabase
import com.example.keepnotes.databinding.ActivityMainBinding
import com.example.keepnotes.view.NoteAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: NoteDatabase
    private lateinit var adapter: NoteAdapter
    private val notes = mutableListOf<Note>()
    companion object {
        const val EDIT_NOTE_REQUEST_CODE = 1 // or any other appropriate value
    }

    private var selectedNotePosition: Int = -1 // Initialize with an invalid value


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = NoteDatabase.getDatabase(this)
        adapter = NoteAdapter(notes, database)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        CoroutineScope(Dispatchers.IO).launch {
            Log.e("getAll", "data is visible")
            notes.clear() // Clear existing notes before adding new ones
            notes.addAll(database.noteDao().getAllNote())

            runOnUiThread { /*
            The runOnUiThread method in Android is a convenient way to execute
             a block of code on the UI (main) thread. Android UI
            operations must be performed on the main thread to ensure the
             responsiveness and integrity of the user interface. */
                binding.recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT
                    or
                    ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                adapter.deleteNoteAtPosition(position)
                Log.e("delete", "$position")
            }
        }

        adapter.setOnNoteClickListener(object : NoteAdapter.OnNoteClickListener {
            override fun onNoteClick(position: Int) {
                selectedNotePosition = position
                val intent = Intent(this@MainActivity , EditNoteActivity::class.java)
                intent.putExtra("note",notes[position])
                startActivityForResult(intent , EDIT_NOTE_REQUEST_CODE)
            }

        })

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        binding.addNote.setOnClickListener {
            val intent = Intent(this, InsertActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == EDIT_NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            val updatedNote = data?.getSerializableExtra("note") as? Note
            updatedNote?.let {
                adapter.updateNoteAtPosition(selectedNotePosition, it)
            }
        }
    }

}

