package com.example.morninghelper.ui.dashboard_activity.fragments.notes.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.morninghelper.R
import com.example.morninghelper.dialog.ChooseItemRecyclerViewAdapter
import com.example.morninghelper.room.AppDatabase
import com.example.morninghelper.room.Notes
import com.example.morninghelper.room.alarms.Alarms
import com.example.morninghelper.tools.extensions.setColor
import com.example.morninghelper.tools.extensions.setViewVisibility
import com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock.AlarmInterface
import com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock.AlarmTools
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.shape.MaterialShapeUtils
import kotlinx.android.synthetic.main.activity_create_note.*
import kotlinx.android.synthetic.main.activity_set_alarm.*
import kotlinx.android.synthetic.main.chooser_dialog_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class CreateNoteActivity : AppCompatActivity(), View.OnClickListener {
    private val db by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java, "database-name"
        ).build()

    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>
    private lateinit var chooserAdapter: ChooseItemRecyclerViewAdapter
    private val chooseItems = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)
        attachToolbar()
        init()


    }

    private fun init() {
        settingsBT.setOnClickListener(this)

    }


    private fun attachToolbar() {
        setSupportActionBar(toolBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        MaterialShapeUtils.setElevation(toolBar, 0f)
        settingsBT.setBackgroundResource(R.mipmap.ic_save)
        titleTV.setColor(
            getString(R.string.createNote),
            ContextCompat.getColor(this, android.R.color.white)
        )


    }

    private suspend fun saveNote() {
        val titleEditText = titleET.text
        val descriptionEditText = descriptionET.text
        if (titleEditText.isNotEmpty() && descriptionEditText.isNotEmpty()) {
            val note = Notes()
            note.title = titleEditText.toString()
            note.description = descriptionEditText.toString()
            db.notesDao().insertAll(note)
            val intent = intent.putExtra("createdNote", note)
            setResult(Activity.RESULT_OK, intent)
            finish()
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.settingsBT -> CoroutineScope(Dispatchers.Main).launch { saveNote() }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            super.onBackPressed()
        return true
    }

}