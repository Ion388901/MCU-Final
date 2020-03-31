package com.example.segundaactividad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

class MainActivity : AppCompatActivity() {

    private lateinit var mcuDudeAdapter : MCUDudeRecyclerAdapter

    var jAvenger:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRecyclerView()// Adapter instantiated
        Log.i("MCUMARVEL API", getMarvelAPIUrl())
        MCUMarvelVolley(getMarvelAPIUrl(), this, mcuDudeAdapter).callMarvelAPI()
    }

    fun setRecyclerView(){
        recycler_view_mcu.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            mcuDudeAdapter = MCUDudeRecyclerAdapter()
            adapter = mcuDudeAdapter
        }
    }

    private fun getDataSet() : ArrayList<MCUDude> {
        val dudes = ArrayList<MCUDude>()
        jAvenger = leaJSON()
        Log.i("com.example", jAvenger)
        val jArray = JSONArray(jAvenger)
        for(i in 0..jArray.length()-1){
            val jobject = jArray.getJSONObject(i)
            val name = jobject.getString("name/alias")
            val notes = jobject.getString("notes")
            if(name!=null) dudes.add( MCUDude(name, notes, ""))
        }
        return dudes
    }

    fun leaJSON():String?{
        var jContenido:String? = null
        try{

            val inputS = assets.open("avengers.json")
            jContenido = inputS.bufferedReader().use { it.readLine() }
        }catch ( ex: Exception){
            ex.printStackTrace()
            Toast.makeText(this, "POM! no Avenger", Toast.LENGTH_LONG).show()
        }
        return jContenido;
    }

    fun String.md5():String{
        val md5Al = MessageDigest.getInstance("MD5")
        return BigInteger(1,md5Al.digest(toByteArray())).toString(16).padStart(32,'0')
    }

    fun getMarvelAPIUrl(): String{
        val tString = Timestamp(System.currentTimeMillis()).toString()
        val hString = tString + "01df0b638abf44a1bcc3dff2d6fb7da51b585075" + "d17a7bbe7bb5257a711c63a5c7b2da04"
        val hash = hString.md5()

        var marvelAPI : String = "https://gateway.marvel.com:443/v1/public/characters?ts=" +
                tString +
                "d17a7bbe7bb5257a711c63a5c7b2da04"+hash
        return marvelAPI

    }
}
