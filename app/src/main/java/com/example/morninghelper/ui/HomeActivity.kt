package com.example.morninghelper.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.util.Log.d
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import androidx.core.app.ActivityCompat
import androidx.core.view.GestureDetectorCompat

import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.morninghelper.R
import com.example.morninghelper.application.App
import com.example.morninghelper.room.AppDatabase
import com.example.morninghelper.shared_preferences.AppSharedPreferences
import com.example.morninghelper.tools.Tools
import com.example.morninghelper.ui.dashboard_activity.DashboardActivity
import com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock.AlarmInterface
import com.example.morninghelper.ui.dashboard_activity.fragments.notes.activity.NotesRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.notes_fragment.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.jar.Manifest


class HomeActivity : AppCompatActivity(), View.OnClickListener {

    var y2: Float = 0.0f
    var y1: Float = 0.0f

    companion object {
        const val MIN_DISTANCE = 150
    }
    private val db by lazy {
        Room.databaseBuilder(
            App.instance.getContext(),
            AppDatabase::class.java, "database-name"
        ).build()

    }

    private var latestNotesList = ArrayList<String>()
    private lateinit var latestNotesRecyclerView: LatestNotesRecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
        CoroutineScope(Dispatchers.Main).launch { getLatestNotes() }
        initRecyclerView()


    }


    private fun init() {
        alarmsTV.setOnClickListener(this)
        newsTV.setOnClickListener(this)
        horoscopeTV.setOnClickListener(this)
        helloTextView.text =
            "Hello, ${AppSharedPreferences.getString(AppSharedPreferences.USER_NAME)}!"
        animations()
    }

    private fun animations() {
        Tools.animation(this, R.anim.fade_in_slow, helloTextView)
        Tools.animation(this, R.anim.fade_in_slow, goodDayTextView)
        Tools.animation(this, R.anim.slide_up, whiteLayout)
        Tools.animation(this, R.anim.blink, swipeToSeeMore)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.actionMasked) {
            0 -> {
                y1 = event.y
            }
            1 -> {
                y2 = event.y
                val valueY: Float = y2 - y1

                if (kotlin.math.abs(valueY) > MIN_DISTANCE && y2 < y1) {
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down)
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun onClickItem(position:Int){
        val intent = Intent(this, DashboardActivity::class.java)
        intent.putExtra("currentPosition", position)
        startActivity(intent)
    }




    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.alarmsTV -> onClickItem(0)
            R.id.newsTV -> onClickItem(1)
            R.id.horoscopeTV -> onClickItem(2)

        }

    }
    private fun initRecyclerView(){
        latestNoteRecyclerView.layoutManager = LinearLayoutManager(this)
        latestNotesRecyclerView = LatestNotesRecyclerView(latestNotesList)
        latestNoteRecyclerView.adapter = latestNotesRecyclerView
        latestNotesRecyclerView.notifyDataSetChanged()
    }



    private suspend fun getLatestNotes(){
     val notes = db.notesDao().getAll()
        if(notes.isNotEmpty()){
        val latestNotes = notes.take(5)
        for (i in latestNotes)
            latestNotesList.add(i.title!!)
            latestNotesRecyclerView.notifyDataSetChanged()
        }

}
}






