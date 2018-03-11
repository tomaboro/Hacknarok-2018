package pl.hacknarok.positivedevs.runit_mobileapp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.elem_activity.view.*

import kotlinx.android.synthetic.main.activity_run_picker.*
import java.util.*
import android.content.ContentValues.TAG
import android.content.Context
import kotlinx.android.synthetic.main.activity_run_picker.view.*
import android.R.id.edit
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject


class EventViewAdapter(var events : MutableList<Event>) : RecyclerView.Adapter<EventViewAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return events.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        val event = events.get(position)
        holder!!.eventName.text = event.name
        holder.eventDatePlace.text = event.date + "  " + event.place
        holder.eventDescription = event.description

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.elem_activity,parent,false)
        return MyViewHolder(view)
    }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val eventName = v.event_name
        val eventDatePlace = v.event_data_place
        var eventDescription = ""
        val eventImage = v.event_photo
        val w = v.setOnClickListener{n ->
            val sharedPref = n.context.getSharedPreferences("prefs",Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("event", eventName.text.toString())
            editor.putString("event_desc", eventDescription)
            editor.commit()
            Toast.makeText(n.context,"Event selected",Toast.LENGTH_LONG).show()
            }
        }
    }

class RunPicker() : Fragment() {
    var events = mutableListOf<Event>()
    @SuppressLint("ValidFragment")
    constructor(list: MutableList<Event>) : this() {
        events = list
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rv = RecyclerView(context)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = EventViewAdapter(events)
        return rv
    }

}
