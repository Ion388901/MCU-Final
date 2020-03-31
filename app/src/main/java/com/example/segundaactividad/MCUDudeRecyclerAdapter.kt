package com.example.segundaactividad

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.mcu_dude_layout.view.*

class MCUDudeRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var dudes: List<MCUDude> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MCUDudeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.mcu_dude_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return dudes.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MCUDudeViewHolder -> {
                holder.bind(dudes.get(position))
                holder.itemView.setOnClickListener {
                    val contexto = it.context
                    val intent = Intent(contexto, MarvelDudeActivity::class.java)
                    intent.putExtra("dude", dudes[position])
                    contexto.startActivity(intent)
                    Toast.makeText(contexto, "INSIDE DUDE!!!!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun setData(listDudes: List<MCUDude>){
        dudes = listDudes
        notifyDataSetChanged()
    }

    class MCUDudeViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageDude = itemView.imageView
        val name = itemView.name_alias
        val notes = itemView.notes

        fun bind(mcuDude: MCUDude){
            name.text = mcuDude.name
            notes.text = mcuDude.hnotes
            imageDude.setPicDude(mcuDude.imageDude)
        }

        fun ImageView.setPicDude(url:String){
            val options = RequestOptions()
                .error(R.mipmap.ic_launcher_round)
            Glide.with(this)
                .setDefaultRequestOptions(options)
                .load(url)
                .into(this)
        }

    }


}