package com.example.keepnotes.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.keepnotes.data.NoteDatabase
import com.example.keepnotes.databinding.FragmentGetAllNotesBinding

class GetAllNotes : Fragment() {
    private lateinit var binding : FragmentGetAllNotesBinding
    private lateinit var database: NoteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentGetAllNotesBinding.inflate(layoutInflater)

    }
}