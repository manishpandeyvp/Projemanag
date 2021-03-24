package com.example.projemanag.firebase

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.example.projemanag.activity.*
import com.example.projemanag.models.Board
import com.example.projemanag.models.User
import com.example.projemanag.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.util.concurrent.CompletionStage

class FirestoreClass {
    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: SignUpActivity, userInfo: User){
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .set(userInfo, SetOptions.merge())
            .addOnCompleteListener {
                activity.userRegisteredSuccess()
            }.addOnFailureListener {
                Log.e(activity.javaClass.simpleName, "Error in registering User")
            }
    }

    fun createBoard(activity: CreateBoardActivity, board: Board){
        mFireStore.collection(Constants.BOARDS).document().set(board, SetOptions.merge()).addOnSuccessListener {
            Log.e(activity.javaClass.simpleName, "Board Created Successfully")
            Toast.makeText(activity, "Board Created Successfully!!", Toast.LENGTH_SHORT).show()
            activity.boardCreatedSuccessfully()
        }.addOnFailureListener {
            exception ->
            activity.hideProgressDialog()
            Log.e(activity.javaClass.simpleName, "Error while creating a board", exception)
        }
    }

    fun getBoardsList(activity: MainActivity){
        mFireStore.collection(Constants.BOARDS).whereArrayContains(Constants.ASSIGNED_TO, getCurrentUserId()).get().addOnSuccessListener {
            document ->
            Log.i(activity.javaClass.simpleName, document.documents.toString())
            val boardList: ArrayList<Board> = ArrayList()
            for(i in document.documents){
                val board = i.toObject(Board::class.java)!!
                board.documentId = i.id
                boardList.add(board)
            }

            activity.populateBoardsListUI(boardList)
        }.addOnFailureListener {
            e ->
            activity.hideProgressDialog()
            Log.e(activity.javaClass.simpleName, "Error while creating board", e)
        }
    }

    fun loadUserData(activity: Activity){
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener {document ->
                val loggedInUser = document.toObject(User::class.java)!!

                when(activity){
                    is SignInActivity -> {
                        activity.signInSuccess(loggedInUser)
                    }
                    is MainActivity -> {
                        activity.updateNavigationUserDetails(loggedInUser)
                    }
                    is MyProfileActivity -> {
                        activity.setUserDataUI(loggedInUser)
                    }
                }
            }.addOnFailureListener {
                when(activity){
                    is SignInActivity -> {
                        activity.hideProgressDialog()
                    }
                    is MainActivity -> {
                        activity.hideProgressDialog()
                    }
                }
                Log.e(activity.javaClass.simpleName, "Error in registering User")
            }
    }

    fun getCurrentUserId(): String{
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if(currentUser != null){
            currentUserID = currentUser.uid
        }
        return currentUserID
    }

    fun updateUserProfileData(activity: MyProfileActivity, userHashMap: HashMap<String, Any>){
        mFireStore.collection(Constants.USERS).document(getCurrentUserId()).update(userHashMap).addOnSuccessListener {
            Log.e(activity.javaClass.simpleName, "Profile Data Updated Successfully!")
            Toast.makeText(activity, "Profile updated successfully!!", Toast.LENGTH_SHORT).show()
            activity.profileUpdateSuccess()
        }.addOnFailureListener {
            exception ->
            activity.hideProgressDialog()
            Log.e(activity.javaClass.simpleName, "Error while creating a board.", exception)
            Toast.makeText(activity, "Some error occurred while updating profile!", Toast.LENGTH_SHORT).show()
        }
    }
}