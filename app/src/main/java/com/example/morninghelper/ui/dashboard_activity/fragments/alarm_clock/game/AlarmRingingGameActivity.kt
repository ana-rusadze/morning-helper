package com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock.game

import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.morninghelper.R
import com.example.morninghelper.dialog.ChooseItemRecyclerViewAdapter
import com.example.morninghelper.tools.Tools
import com.example.morninghelper.ui.HomeActivity
import com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock.AlarmInterface
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_alarm_ringing_game.*
import kotlinx.android.synthetic.main.activity_set_alarm.*
import kotlinx.android.synthetic.main.chooser_dialog_layout.*
import kotlin.properties.Delegates

class AlarmRingingGameActivity : AppCompatActivity() {


    private lateinit var r: Ringtone
    private lateinit var adapter: GameRecyclerViewAdapter
    private val gameItems = mutableListOf<GameModel>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_ringing_game)
        init()
    }

    private fun init() {
        val ringtone = intent.getStringExtra("data")
        d("ringtone", ringtone.toString())
        val myUri = Uri.parse(ringtone.toString())
        r = RingtoneManager.getRingtone(applicationContext, myUri)
        r.play()


        gameRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = GameRecyclerViewAdapter(gameItems,  object : AlarmInterface{
            override fun onClick(position: Int) {
                answerButton.setOnClickListener {
                    var count = 0
                    (0 until gameItems.size).forEach{
                        if(gameItems[it].right){
                            count +=1
                        }
                    }
                    if(count ==4){
                        r.stop()
                        Toast.makeText(applicationContext, "Well done!", Toast.LENGTH_SHORT).show()
                        Tools.startActivity(this@AlarmRingingGameActivity, HomeActivity::class.java, null, true)
                    } else
                        Toast.makeText(applicationContext, "Please complete all tasks correctly!", Toast.LENGTH_SHORT).show()
                }

            }
        })
        gameRecyclerView.adapter = adapter

        addItems()

    }

    private fun addItems(){

        var count = 0

        for (i in 0..15) {
            val firstInt = (10..99).random()
            val secondInt = (10..99).random()
            val operator = listOf("+", "-").random()
            val rightAnswer = if(operator == "+"){
               firstInt + secondInt
            }else
               firstInt - secondInt


            if (firstInt > secondInt) {
                gameItems.add(GameModel(firstInt, operator, secondInt, rightAnswer, false))
                count += 1
            }
            if (count == 4) break
        }

    }

    override fun onBackPressed() {
        Toast.makeText(this, "Complete tasks first!", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        r.stop()
    }

}