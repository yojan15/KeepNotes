package com.example.keepnotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.keepnotes.databinding.ActivityMainBinding
import com.example.keepnotes.view.InsertFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val getAllNoteFrame = GetAllNotes()
        val insertFragment = InsertFragment()

        setCurrentFragment(getAllNoteFrame)
        binding.addNote.setOnClickListener {
            setCurrentFragment(insertFragment)
        }
    }
    private fun setCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.mainFrame , fragment)
        commit()
    }
}