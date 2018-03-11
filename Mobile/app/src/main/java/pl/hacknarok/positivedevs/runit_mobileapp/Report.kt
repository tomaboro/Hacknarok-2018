package pl.hacknarok.positivedevs.runit_mobileapp

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.content.pm.PackageManager
import android.location.LocationManager
import android.support.v4.app.ActivityCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_report.*
import kotlinx.android.synthetic.main.fragment_report.view.*
import android.view.View.OnLongClickListener
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Report.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Report.newInstance] factory method to
 * create an instance of this fragment.
 */
class Report : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report, container, false)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        button_accident.setOnLongClickListener { n ->
            val locMan = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (ActivityCompat.checkSelfPermission(this!!.context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this!!.context!!, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {}
            val loc = locMan?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val prefs = n.context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
            val token = prefs.getString(getString(R.string.pref_token), "111")
            val url = getString(R.string.url_ng) + "/app/addaccident"
            val jsonBody = JSONObject("{\"id\":\"" + token + "\",\"latitude\":\"" + loc!!.latitude + "\",\"longitude\":\"" + loc!!.longitude + " \"}")
            val stringRequest = JsonObjectRequest(Request.Method.PUT, url, jsonBody,
                    object : Response.Listener<JSONObject> {
                        override fun onResponse(response: JSONObject?) {
                        }
                    },
                    object : Response.ErrorListener {
                        override fun onErrorResponse(erreor: VolleyError) {
                        }
                    })

            ConnectionSingleton.getInstance(context).requestQueue.add(stringRequest)
            true
        }

        button_lost.setOnLongClickListener{ n ->
            val locMan = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (ActivityCompat.checkSelfPermission(this!!.context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this!!.context!!, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {}
            val loc = locMan?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val prefs = n.context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
            val token = prefs.getString(getString(R.string.pref_token), "111")
            val url = getString(R.string.url_ng) + "/app/addlost"
            val jsonBody = JSONObject("{\"id\":\"" + token + "\",\"latitude\":\"" + loc!!.latitude + "\",\"longitude\":\"" + loc!!.longitude + " \"}")
            val stringRequest = JsonObjectRequest(Request.Method.PUT, url, jsonBody,
                    object : Response.Listener<JSONObject> {
                        override fun onResponse(response: JSONObject?) {
                        }
                    },
                    object : Response.ErrorListener {
                        override fun onErrorResponse(erreor: VolleyError) {
                        }
                    })

            ConnectionSingleton.getInstance(context).requestQueue.add(stringRequest)
            true
        }
    }
}

