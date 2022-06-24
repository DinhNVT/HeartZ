package com.example.heartz.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.heartz.model.History
import com.example.heartz.model.MUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase

class StatusViewModel: ViewModel() {
    var status: MutableLiveData<Int> = MutableLiveData<Int>()
    private val db = Firebase.database
    init {
        getStatus()
    }

    private fun getStatus(){
        val myRef = db.getReference("state").child("do")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue<Int>()
                if(value==0) status.value = 0
                else if(value==1)status.value  = 1
                else status.value =0
                Log.d("tagg", status.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }
}