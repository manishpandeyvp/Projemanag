package com.example.projemanag.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projemanag.R
import com.example.projemanag.models.SelectedMembers
import kotlinx.android.synthetic.main.item_card_selected_member.view.*

open class CardMemberListItemsAdapter(
    private val context: Context,
    private val list: ArrayList<SelectedMembers>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_card_selected_member,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if(holder is MyViewHolder){
            if(position == list.size - 1){
                holder.itemView.iv_add_member.visibility = View.VISIBLE
                holder.itemView.iv_selected_member_image.visibility = View.GONE
            } else {
                holder.itemView.iv_add_member.visibility = View.GONE
                holder.itemView.iv_selected_member_image.visibility = View.VISIBLE

                Glide.with(context)
                    .load(model.image)
                    .centerCrop()
                    .placeholder(R.drawable.ic_person_black_24dp)
                    .into(holder.itemView.iv_selected_member_image)
            }

            holder.itemView.setOnClickListener {
                if(onClickListener != null){
                    onClickListener!!.onCLick()
                }
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }

    private class MyViewHolder(view: View): RecyclerView.ViewHolder(view)

    interface OnClickListener{
        fun onCLick()
    }
}