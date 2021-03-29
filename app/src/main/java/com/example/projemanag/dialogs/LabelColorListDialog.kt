package com.example.projemanag.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projemanag.R
import com.example.projemanag.adapters.LabelColorListItemsAdapter
import kotlinx.android.synthetic.main.dialog_list.view.*

abstract class LabelColorListDialog(
    context: Context,
    private var list: ArrayList<String>,
    private var title: String = "",
    private var mSelectedColor: String
) : Dialog(context){
    private var adapter: LabelColorListItemsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_list, null)

        setContentView(view)
        setCancelable(true)
        setCanceledOnTouchOutside(true)
        setupRecyclerView(view)
    }

    private fun setupRecyclerView(view: View){
        view.tvTitle.text = title
        view.rvList.layoutManager = LinearLayoutManager(context)
        adapter = LabelColorListItemsAdapter(context, list, mSelectedColor)
        view.rvList.adapter = adapter

        adapter!!.onItemCLickListener = object : LabelColorListItemsAdapter.OnItemClickListener{
            override fun onCLick(position: Int, color: String) {
                dismiss()
                onItemSelected(color)
            }
        }
    }

    protected abstract fun onItemSelected(color: String)

}