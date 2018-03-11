package pl.hacknarok.positivedevs.runit_mobileapp

import android.Manifest
import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.location.LocationManager
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import android.content.SharedPreferences
import pl.hacknarok.positivedevs.runit_mobileapp.R.id.et_name


class UpdateService() : IntentService("Update Service") {
    var context: Context? = null
    var locMan: LocationManager? = null

    override fun onCreate() {
        super.onCreate()
        context = this
        locMan = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    /**
     * Runs all models
     * @param intent intent sent to service
     */
    override fun onHandleIntent(intent: Intent?) {
        if (ActivityCompat.checkSelfPermission(this!!.context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this!!.context!!, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        val loc = locMan?.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        val prefs = this.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val token = prefs.getString(getString(R.string.pref_token), "111")
        val url = getString(R.string.url_ng) + "/user/updateposition"
        val jsonBody = JSONObject("{\"token\":\"" + token + "\",\"latitude\":\"" + (loc?.latitude ?: 0) + "\",\"longitude\":\"" + (loc?.latitude ?: 0) + " \"}")
        val stringRequest = JsonObjectRequest(Request.Method.POST, url, jsonBody,
                object : Response.Listener<JSONObject> {
                    override fun onResponse(response: JSONObject?) {
                    }
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(erreor: VolleyError) {
                    }
                })

        ConnectionSingleton.getInstance(this).requestQueue.add(stringRequest)
    }
}