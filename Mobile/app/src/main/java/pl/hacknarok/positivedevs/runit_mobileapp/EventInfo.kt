package pl.hacknarok.positivedevs.runit_mobileapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.design.widget.Snackbar
import android.support.design.widget.FloatingActionButton
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_event_info.*
import kotlinx.android.synthetic.main.fragment_event_info.view.*
import java.time.Duration
import android.provider.MediaStore
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import android.graphics.Bitmap.CompressFormat
import android.location.LocationManager
import android.support.v4.app.ActivityCompat
import android.util.Base64
import java.io.ByteArrayOutputStream


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EventInfo.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EventInfo.newInstance] factory method to
 * create an instance of this fragment.
 */
class EventInfo() : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_event_info, container, false)
    }

    override fun onStart() {
        super.onStart()

        val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val info = prefs.getString("event_desc", "111")
        tv_info.text = "O biegu AGH 2017\n" +
                "Akademicki Związek Sportowy AGH oraz Fundacja dla AGH pod patronatem Jego Magnificencji prof. dr hab. inż. Tadeusza Słomki serdecznie zapraszają na VI BIEG AGH, który odbędzie się  10 czerwca 2017 roku o godzinie 16:00 \n" +
                "Tegoroczna edycja imprezy biegowej organizowanej na terenie naszej uczelni będzie rozegranana dwóch dystansach :\n" +
                "5 i 10 km\n" +
                "\n" +
                "OPŁATY\n" +
                "Opłaty startowe do 31.05.2016 r.: 45 zł (studenci AGH – 35 zł) + \n + Opłaty od dnia 8.06.2015 r.: 50 zł (studenci AGH – 50 zł)\n" +
                "\n" +
                "Opłaty w biurze zawodów: 70 zł (studenci AGH – 70 zł)\n" +
                "\n" +
                "Opłatę startową należy uregulować poprzez system zapisów."

        fab.setOnClickListener{_ ->
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, 4)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == 4 && resultCode == Activity.RESULT_OK) {
            val extras = data.extras
            val imageBitmap = extras!!.get("data") as Bitmap

            val byteArrayOutputStream = ByteArrayOutputStream()
            imageBitmap.compress(CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val encoded = Base64.encodeToString(byteArray,Base64.DEFAULT)

            val locMan = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (ActivityCompat.checkSelfPermission(this!!.context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this!!.context!!, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {}
            val loc = locMan?.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            val url = getString(R.string.url_ng) + "/image/add"
            val jsonBody = JSONObject("{\"name\":\"" + "nanana.png" + "\",\"data\":\"" + encoded + "\",\"latitude\":\"" + loc!!.latitude + "\",\"longitude\":\"" + loc!!.longitude + " \"}")
            val stringRequest = JsonObjectRequest(Request.Method.PUT, url, jsonBody,
                    object : Response.Listener<JSONObject> {
                        override fun onResponse(response: JSONObject?) {
                        }
                    },
                    object : Response.ErrorListener {
                        override fun onErrorResponse(error: VolleyError) {
                        }
                    })

            ConnectionSingleton.getInstance(context).requestQueue.add(stringRequest)
        }
    }
}
