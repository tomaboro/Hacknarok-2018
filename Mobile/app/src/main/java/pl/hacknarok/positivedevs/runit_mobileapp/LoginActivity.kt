package pl.hacknarok.positivedevs.runit_mobileapp

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button.setOnClickListener({ _ ->
            val url = getString(R.string.url_ng) + "/user/login"
            val jsonBody = JSONObject("{\"name\":\"" + et_name.text + "\",\"password\":\"" + et_pass.text + " \"}")
            val stringRequest = JsonObjectRequest(Request.Method.POST, url, jsonBody,
                    object : Response.Listener<JSONObject> {
                        override fun onResponse(response: JSONObject?) {
                            loginProgressBar.visibility = View.INVISIBLE


                            val sharedPref = getSharedPreferences("prefs",Context.MODE_PRIVATE)
                            val editor = sharedPref.edit()
                            editor.putString(getString(R.string.pref_token), response?.getString("content"))
                            editor.commit()

                            val intent = Intent(applicationContext, MainActivity::class.java)
                            Log.d("asdass",intent.toString())
                            startActivity(intent)
                        }
                    },
                    object : Response.ErrorListener {
                        override fun onErrorResponse(erreor: VolleyError) {
                            Toast.makeText(applicationContext,"Login errror",Toast.LENGTH_LONG).show()
                            loginProgressBar.visibility = View.INVISIBLE
                        }
                    })

            loginProgressBar.visibility = View.VISIBLE
            ConnectionSingleton.getInstance(this).requestQueue.add(stringRequest)

        })

    }

    override fun onBackPressed() {}
}
