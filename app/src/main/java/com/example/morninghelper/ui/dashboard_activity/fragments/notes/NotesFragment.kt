package com.example.morninghelper.ui.dashboard_activity.fragments.notes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.morninghelper.R
import com.example.morninghelper.application.App
import com.example.morninghelper.room.AppDatabase
import com.example.morninghelper.room.Notes
import com.example.morninghelper.tools.extensions.onTextChanged
import com.example.morninghelper.tools.extensions.setViewVisibility
import com.example.morninghelper.ui.BaseFragment
import com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock.AlarmInterface
import com.example.morninghelper.ui.dashboard_activity.fragments.notes.activity.CreateNoteActivity
import com.example.morninghelper.ui.dashboard_activity.fragments.notes.activity.NotesRecyclerViewAdapter
import kotlinx.android.synthetic.main.notes_fragment.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class NotesFragment : BaseFragment() {

    companion object {
        fun newInstance() =
            NotesFragment()

        const val REQUEST_CODE = 20
    }

    private val db by lazy {
        Room.databaseBuilder(
            App.instance.getContext(),
            AppDatabase::class.java, "database-name"
        ).build()

    }


    private lateinit var viewModel: NotesViewModel
    private var noteItems: ArrayList<Notes> = arrayListOf()
    private lateinit var notesRecyclerViewAdapter: NotesRecyclerViewAdapter
    private var noteItemListPosition = 0
    override fun getLayoutResource(): Int = R.layout.notes_fragment

    override fun start(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {

        initRecyclerView()
        rootView!!.addNoteBT.setOnClickListener {
            init()
//            CoroutineScope(Dispatchers.Main).launch { delete() }
        }
        initNotes()
        searchNote()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

    }


    private fun init() {
        val intent = Intent(activity, CreateNoteActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE)

    }

    private fun initRecyclerView() {
        rootView!!.notesRecyclerView.layoutManager = LinearLayoutManager(App.instance.getContext())
        notesRecyclerViewAdapter = NotesRecyclerViewAdapter(noteItems, object :AlarmInterface{
            override fun onClick(position: Int) {
                noteItemListPosition = position

            }

        })
        rootView!!.notesRecyclerView.adapter = notesRecyclerViewAdapter


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (REQUEST_CODE == 20 && resultCode == Activity.RESULT_OK) {
            val note = data!!.extras?.getParcelable<Notes>("createdNote")
            noteItems.add(0, note!!)
            notesRecyclerViewAdapter.notifyDataSetChanged()

        }
    }


    private fun initNotes() {
        CoroutineScope(Dispatchers.Main).launch {
            readNotes()
        }
    }

    private suspend fun readNotes() {
        val notes = db.notesDao().getAll()
        if (notes.isEmpty()) {
            rootView!!.addYourNote.setViewVisibility(false)
        } else {
            rootView!!.notesRecyclerView.setViewVisibility(true)
            notes.forEach {
                noteItems.add(0, it)
            }
            notesRecyclerViewAdapter.notifyDataSetChanged()

        }

    }

    private fun searchNote() {
        rootView!!.searchNoteET.onTextChanged { search(rootView!!.searchNoteET.text.toString()) }
    }


    private fun search(title: String) {
        val filterNotes: ArrayList<Notes> = ArrayList()

        for(i in noteItems){
            if (i.title!!.toLowerCase(Locale.ROOT).contains(title.toLowerCase(Locale.ROOT))) {
                filterNotes.add(i)
            }
        }
        notesRecyclerViewAdapter.filterNoteList(filterNotes)
    }

    private suspend fun delete(){
        val note = noteItems[noteItemListPosition]
        noteItems.remove(note)
        db.notesDao().delete(note)
        notesRecyclerViewAdapter.notifyDataSetChanged()
    }




}