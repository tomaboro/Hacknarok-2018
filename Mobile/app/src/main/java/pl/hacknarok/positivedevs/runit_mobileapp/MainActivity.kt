package pl.hacknarok.positivedevs.runit_mobileapp

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import android.content.Intent
import android.widget.Toast
import android.app.ActivityManager
import android.content.Context
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,EventInfo())
        fragmentTransaction.commit()

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val manager = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        var isAlreadyRunning: Boolean? = false
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (MainForegroundService::class.java.name == service.service.className) {
                isAlreadyRunning = true
            }
        }
        if ((!isAlreadyRunning!!)) {
            val intent = Intent(baseContext, MainForegroundService::class.java)
            Log.d("asdass",intent.toString())
            startService(intent)
        }


    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_info -> {

                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container,EventInfo())
                fragmentTransaction.commit()

            }
            R.id.nav_select_run -> {
                var events = mutableListOf<Event>()
                val url = getString(R.string.url_ng) + "/event/getAll"
            val jsonBody = JSONObject("{\"name\":\"" + "asdsad" + "\",\"password\":\"" + "dasd"+ " \"}")
                val stringRequest = JsonObjectRequest(Request.Method.POST, url, jsonBody,
                        object : Response.Listener<JSONObject> {
                            override fun onResponse(response: JSONObject?) {

                                val arr = response?.getJSONArray("events")

                                for (i in 0 until arr!!.length()) {
                                    events.add(Event(arr.getJSONObject(i).getString("name"), arr.getJSONObject(i).getString("place"),"sdasdasd",arr.getJSONObject(i).getString("start_date")))

                                val fragmentTransaction = fragmentManager.beginTransaction()
                                fragmentTransaction.replace(R.id.fragment_container,RunPicker(events))
                                fragmentTransaction.commit()

                                Log.d("Connection","OK")
                            }
                        }},
                        object : Response.ErrorListener {
                            override fun onErrorResponse(error: VolleyError) {
                                Log.d("Connection",error.message)
                            }
                        })
                ConnectionSingleton.getInstance(this).requestQueue.add(stringRequest)
            }
            R.id.nav_report -> {
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container,Report())
                fragmentTransaction.commit()
            }
            R.id.nav_map -> {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://inform.release.commandcentral.com/#/"))
                startActivity(browserIntent)
            }
            R.id.nav_logout -> {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
