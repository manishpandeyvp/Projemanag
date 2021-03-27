package com.example.projemanag.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projemanag.R
import com.example.projemanag.adapters.MemberListItemsAdapter
import com.example.projemanag.firebase.FirestoreClass
import com.example.projemanag.models.Board
import com.example.projemanag.models.User
import com.example.projemanag.utils.Constants
import kotlinx.android.synthetic.main.activity_members.*

class MembersActivity : BaseActivity() {
    private lateinit var mBoardDetails: Board

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_members)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setupActionBar()

        if(intent.hasExtra(Constants.BOARD_DETAIL)){
            mBoardDetails = intent.getParcelableExtra<Board>(Constants.BOARD_DETAIL)!!
        }

        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getAssignedMemberListDetails(this, mBoardDetails.assignedTo)
    }

    private fun setupActionBar(){
        setSupportActionBar(toolbar_members_activity)
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white)
        }

        toolbar_members_activity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    fun setupMemberList(list: ArrayList<User>){
        hideProgressDialog()

        rv_members_list.layoutManager = LinearLayoutManager(this)
        rv_members_list.setHasFixedSize(true)

        val adapter = MemberListItemsAdapter(this, list)
        rv_members_list.adapter = adapter
    }
}
