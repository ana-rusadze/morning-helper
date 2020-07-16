package com.example.morninghelper.ui.dashboard_activity.fragments.notes.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.morninghelper.R
import com.example.morninghelper.application.App
import com.example.morninghelper.room.AppDatabase
import com.example.morninghelper.room.Notes
import com.example.morninghelper.tools.extensions.setColor
import com.example.morninghelper.ui.dashboard_activity.fragments.notes.NotesViewModel
import com.google.android.material.shape.MaterialShapeUtils
import kotlinx.android.synthetic.main.activity_edit_note.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditNoteActivity : AppCompatActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            App.instance.getContext(),
            AppDatabase::class.java, "database-name"
        ).build()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)
        init()
        settingsBT.setOnClickListener { CoroutineScope(Dispatchers.Main).launch { editNote()} }
    }

    private fun init(){
        attachToolbar()
        getNote()
    }

    private fun attachToolbar() {
        setSupportActionBar(toolBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        MaterialShapeUtils.setElevation(toolBar, 0f)
        settingsBT.setBackgroundResource(R.mipmap.ic_save)
        titleTV.setColor(
            getString(R.string.edit_note),
            ContextCompat.getColor(this, android.R.color.white)
        )
    }

    private fun getNote():Notes{
        val note = intent.extras?.getParcelable<Notes>("editableNote")
        titleETEdit.setText(note!!.title)
        descriptionETEdit.setText(note.description)
        return note
    }


    private suspend fun editNote(){
        val title = titleETEdit.text.toString()
        val description = descriptionETEdit.text.toString()
        if (title.isNotEmpty() && description.isNotEmpty()){
           val editedNote =  getNote()
            editedNote.title = title
            editedNote.description = description
            d("editedNoteInActivity", editedNote.toString())
            db.notesDao().updateNotes(editedNote)
            val intent = intent.putExtra("EditedNote", editedNote)
            setResult(Activity.RESULT_OK, intent)
            finish() }
        else
            d("logTEst", "bla blah blah")
        }



    private fun backToNotesFragment() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out)

    }

    override fun onBackPressed() {
        backToNotesFragment()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            super.onBackPressed()
        return true
    }

}



