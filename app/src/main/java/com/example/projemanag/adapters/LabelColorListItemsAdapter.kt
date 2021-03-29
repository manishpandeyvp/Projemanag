package com.example.projemanag.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projemanag.R
import kotlinx.android.synthetic.main.item_label_color.view.*

open class LabelColorListItemsAdapter(
    private val context: Context,
    private val list: ArrayList<String>,
    private val mSelectedColor: String
): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var onItemCLickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_label_color, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        if(holder is MyViewHolder){
            holder.itemView.view_main.setBackgroundColor(Color.parseColor(item))

            if(item == mSelectedColor){
                holder.itemView.iv_selected_color.visibility = View.VISIBLE
            }else{
                holder.itemView.iv_selected_color.visibility = View.GONE
            }

            holder.itemView.setOnClickListener {
                if(onItemCLickListener != null){
                    onItemCLickListener!!.onCLick(position, item)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onCLick(position: Int, color: String)
    }

    private class MyViewHolder(view: View): RecyclerView.ViewHolder(view)
}