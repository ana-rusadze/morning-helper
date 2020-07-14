package com.example.morninghelper.ui.dashboard_activity.fragments.notes.activity

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.morninghelper.R
import com.example.morninghelper.room.AppDatabase
import com.example.morninghelper.room.Notes
import com.example.morninghelper.tools.Tools
import com.example.morninghelper.tools.extensions.setColor
import com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock.AlarmInterface
import com.google.android.material.shape.MaterialShapeUtils
import kotlinx.android.synthetic.main.activity_create_note.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CreateNoteActivity : AppCompatActivity(), View.OnClickListener {
    private val db by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java, "database-name"
        ).build()

    }
//
//    private lateinit var chooseColorRecyclerViewAdapter: ChooseColorRecyclerViewAdapter
//    private var colorsItemList:ArrayList<Int> = arrayListOf()
//    private var colorChooserItemPosition = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)
        attachToolbar()
        init()
//        initColorChooserRecyclerView()
    }

    private fun init() {
        settingsBT.setOnClickListener(this)
        colorChooserAnimation.setOnClickListener(this)

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
//            note.color = colorsItemList[colorChooserItemPosition]
            Log.d("model", "$note.title")
            db.notesDao().insertAll(note)
            for (i in db.notesDao().getAll())
                Log.d("logNote", "${i.title}")
            val intent = intent.putExtra("createdNote", note)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }


//    private fun initColorChooserRecyclerView() {
//        colorChooserRecyclerView.layoutManager =
//            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
//        chooseColorRecyclerViewAdapter = ChooseColorRecyclerViewAdapter(colorsItemList, object : AlarmInterface {
//            override fun onClick(position: Int) {
//                colorChooserItemPosition = position
//                onChooseColor()
//            }
//
//        })
//        colorChooserRecyclerView.adapter = chooseColorRecyclerViewAdapter
//        chooseColorRecyclerViewAdapter.notifyDataSetChanged()
//
//    }
//
//    private fun addColorsInRecyclerView() {
//        Tools.animation(this, R.anim.slide_right_in, colorChooserRecyclerView)
////        val addableColorsItemList = arrayListOf<Int>(
////            R.color.yellowColor,
////            R.color.brown,
//            R.color.creamColor,
//            R.color.greenColor,
//            R.color.redColor
//        )
//        for (i in addableColorsItemList) {
//            colorsItemList.addAll(addableColorsItemList)
//        }

//        val colorsTxt =
//            applicationContext.resources.getStringArray(R.array.colors)
//        for (i in colorsTxt.indices) {
//            colorsItemList.add(Color.parseColor(colorsTxt[i]))
//
//        }
//        chooseColorRecyclerViewAdapter.notifyDataSetChanged()
//    }

//    private fun onChooseColor(){
////        colorsItemList.clear()
//        colorChooserRecyclerView.adapter!!.notifyDataSetChanged()
//        Tools.animation(this, R.anim.slide_right_out, colorChooserRecyclerView)
//    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.settingsBT -> CoroutineScope(Dispatchers.Main).launch { saveNote() }
//            R.id.colorChooserAnimation -> addColorsInRecyclerView()
        }
    }
}