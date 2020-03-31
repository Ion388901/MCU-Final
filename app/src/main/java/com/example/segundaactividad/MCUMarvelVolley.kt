package com.example.segundaactividad

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MCUMarvelVolley(
    val url:String, val contexto: Context, val mcuAdapter:MCUDudeRecyclerAdapter){
        val queue = Volley.newRequestQueue(contexto)

        fun callMarvelAPI(){
            val dataHeroes = ArrayList<MCUDude>()
            val requestMarvel = JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                Response.Listener<JSONObject>{
                    response ->
                    val heroes = response.getJSONObject("data")
                        .getJSONArray("results")
                    for(i in 0..heroes.length()-1){
                        val character = heroes.getJSONObject(i)
                        val image = character.getJSONObject("thumbnail")
                        val thumbnail = image.getString("path")+ "."+image.getString("extension")
                        val dude = MCUDude(character.getString("name"), character.getString("description"), thumbnail)
                        dataHeroes.add(dude)
                    }
                    mcuAdapter.setData(dataHeroes)
                },
                Response.ErrorListener{
                    Toast.makeText(contexto, "Hubo un Error", Toast.LENGTH_LONG).show()

                })
            //queue.add(requestMarvel)
            }
        }