package com.example.morninghelper.ui.dashboard_activity.fragments.notes

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.morninghelper.R
import com.example.morninghelper.application.App
import com.example.morninghelper.dialog.CustomDialogInterface
import com.example.morninghelper.room.AppDatabase
import com.example.morninghelper.room.Notes
import com.example.morninghelper.tools.Tools
import com.example.morninghelper.tools.extensions.onTextChanged
import com.example.morninghelper.tools.extensions.setViewVisibility
import com.example.morninghelper.ui.BaseFragment
import com.example.morninghelper.ui.dashboard_activity.DashboardActivity
import com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock.AlarmInterface
import com.example.morninghelper.ui.dashboard_activity.fragments.notes.activity.CreateNoteActivity
import com.example.morninghelper.ui.dashboard_activity.fragments.notes.activity.EditNoteActivity
import com.example.morninghelper.ui.dashboard_activity.fragments.notes.activity.NotesRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_set_alarm.*
import kotlinx.android.synthetic.main.alarm_clock_fragment.view.*
import kotlinx.android.synthetic.main.notes_fragment.*
import kotlinx.android.synthetic.main.notes_fragment.view.*
import kotlinx.android.synthetic.main.notes_fragment.view.notesRecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class NotesFragment : BaseFragment() {

    companion object {

        const val REQUEST_CODE_CREATE = 20
        const val REQUEST_CODE_EDIT = 21
    }

    private val db by lazy {
        Room.databaseBuilder(
            App.instance.getContext(),
            AppDatabase::class.java, "database-name"
        ).build()

    }

    private var noteItems: ArrayList<Notes> = arrayListOf()
    private lateinit var notesRecyclerViewAdapter: NotesRecyclerViewAdapter
    private var editedNotePos = 0
    override fun getLayoutResource(): Int = R.layout.notes_fragment

    override fun start(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {

        initRecyclerView()
        rootView!!.addNoteBT.setOnClickListener {
            init()
        }
        initNotes()
        searchNote()
        noNotes()

    }



    private fun init() {

        val intent = Intent(activity, CreateNoteActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_CREATE)
    }
    private fun initRecyclerView() {
        rootView!!.notesRecyclerView.layoutManager = LinearLayoutManager(App.instance.getContext())
        notesRecyclerViewAdapter = NotesRecyclerViewAdapter(noteItems, object : AlarmInterface {
            override fun onClick(position: Int) {
                Tools.initDialog(
                    activity as DashboardActivity,
                    "Notes` options",
                    object : CustomDialogInterface {
                        override fun delete() {
                            CoroutineScope(Dispatchers.Main).launch { delete(position) }
                            d("selectedPositionmod", position.toString())
                            noNotes()
                        }

                        override fun edit() {
                            editNote(noteItems[position], position)
                            editedNotePos = position
                        }
                    })
            }
        })
        rootView!!.notesRecyclerView.adapter = notesRecyclerViewAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CREATE) {
                val note = data!!.extras?.getParcelable<Notes>("createdNote")
                rootView!!.addYourNote.setViewVisibility(false)
                rootView!!.notesRecyclerView.setViewVisibility(true)
                rootView!!.searchNoteET.setViewVisibility(true)
                noteItems.add(0, note!!)
                d("createdNote", note.title.toString())
                notesRecyclerViewAdapter.notifyItemInserted(0)
                notesRecyclerView.scrollToPosition(0)
            } else if (requestCode == REQUEST_CODE_EDIT) {
                val note = data!!.extras?.getParcelable<Notes>("EditedNote")
                if (note != null) {
                    d("editedNote", note.title.toString())
                    noteItems.add(editedNotePos, note)
                    notesRecyclerViewAdapter.notifyItemChanged(editedNotePos)
                    notesRecyclerView.scrollToPosition(editedNotePos)
                    noNotes()
                } else
                    d("nulCheck", "note object is null")

            }


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
            rootView!!.addYourNote.setViewVisibility(true)
            rootView!!.searchNoteET.setViewVisibility(false)
            rootView!!.notesRecyclerView.setViewVisibility(false)
        } else {
            rootView!!.addYourNote.setViewVisibility(false)
            rootView!!.notesRecyclerView.setViewVisibility(true)
            rootView!!.searchNoteET.setViewVisibility(true)

            notes.forEach {
                noteItems.add(0, it)
            }
            notesRecyclerViewAdapter.notifyDataSetChanged()

        }

    }

    private fun searchNote() {
        rootView!!.searchNoteET.onTextChanged { search(rootView!!.searchNoteET.text.toString()) }
        noNotes()
    }


    private fun search(title: String) {
        val filterNotes: ArrayList<Notes> = ArrayList()
        for (i in noteItems) {
            if (i.title!!.toLowerCase(Locale.ROOT)
                    .contains(title.toLowerCase(Locale.ROOT))
            ) {
                filterNotes.add(i)

            }
        }
        notesRecyclerViewAdapter.filterNoteList(filterNotes)
    }

    private suspend fun delete(position: Int) {
        val note = noteItems[position]
        d("logPosition", position.toString())
        db.notesDao().delete(note)
        noNotes()
        noteItems.remove(note)
        notesRecyclerViewAdapter.notifyItemRemoved(position)
    }


    private fun editNote(notes: Notes, position: Int) {
        val intent = Intent(App.instance.getContext(), EditNoteActivity::class.java)
        editedNotePos = position
        noteItems.remove(notes)
        intent.putExtra("editableNote", notes)
        startActivityForResult(intent, REQUEST_CODE_EDIT)

    }

    private fun noNotes(){
        if(noteItems.size ==0)
            rootView!!.addYourNote.setViewVisibility(true)
        else
            rootView!!.addYourNote.setViewVisibility(false)

    }




}