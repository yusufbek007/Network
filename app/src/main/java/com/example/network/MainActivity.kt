package com.example.network

import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.network.databinding.ActivityMainBinding
import com.example.network.utils.NetworkHelper
import com.google.gson.JsonObject
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var networkHelper: NetworkHelper
    lateinit var requestQueue: RequestQueue

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        networkHelper = NetworkHelper(this)

        if (networkHelper.isNetworkConnexted()){
            binding.tv.text = "Connected"

            requestQueue = Volley.newRequestQueue(this)

            featchImageLoad()

            featchObjectLoad()


        }




    }

    private fun featchImageLoad() {

        val imageRequest = ImageRequest("https://i.imgur.com/Nwk25LA.jpg",
            object : Response.Listener<Bitmap>{
                override fun onResponse(response: Bitmap?) {
                    binding.imageView.setImageBitmap(response)
                }
            }, 0, 0, ImageView.ScaleType.CENTER_CROP,
            Bitmap.Config.ARGB_8888,
            object : Response.ErrorListener{
                override fun onErrorResponse(error: VolleyError?) {
                    binding.tv.text = error?.message
                }
            })


        requestQueue.add(imageRequest)
    }

    private fun featchObjectLoad() {
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, "http://ip.jsontest.com", null,
            object: Response.Listener<JSONObject>{//xatolik
            override fun onResponse(response: JSONObject?) {
                val strstring = response?.getString("ip")
                binding.tv.text = strstring
            }
            }, object : Response.ErrorListener{
                override fun onErrorResponse(error: VolleyError?) {
                    binding.tv.text = error?.message
                }
            })
        requestQueue.add(jsonObjectRequest)

    }

}