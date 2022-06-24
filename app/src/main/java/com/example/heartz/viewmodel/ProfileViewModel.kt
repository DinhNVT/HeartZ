package com.example.heartz.viewmodel

import android.util.Log

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.heartz.model.History
import com.example.heartz.model.MUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp

class ProfileViewModel: ViewModel() {
    val auth: FirebaseAuth = Firebase.auth
    private val userId = auth.currentUser?.uid
    var mUser: MutableLiveData<MUser> = MutableLiveData<MUser>()
    private var firestore : FirebaseFirestore = FirebaseFirestore.getInstance()
    private var listHistory: MutableLiveData<List<History>> = MutableLiveData<List<History>>()

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        getUser()
        getHistory()
    }

    private fun getUser(){
        if (userId != null) {
            firestore.collection("users").document(userId.toString()).addSnapshotListener {
                    snapshot, e ->
                if (e != null) {
                    Log.w("Listen failed", e)
                    return@addSnapshotListener
                }
                snapshot?.let {
                    mUser.value = MUser(
                        id = "",
                         fullName = snapshot.data?.get("full_name") as String,
                         birth = snapshot.data?.get("birth") as String,
                         gender = snapshot.data?.get("gender") as Boolean,
                         userId = snapshot.data?.get("user_id") as String,
                         displayName = snapshot.data?.get("display_name") as String,
                         avatarUrl = snapshot.data?.get("avatar_url") as String,
                         phone = snapshot.data?.get("phone") as String,
                         histories = snapshot.data?.get("histories") as List<History>
                    )
                }
            }
        }
    }

    private fun getHistory(){
        if (userId != null) {
            firestore
                .collection("users")
                .document(userId.toString())
                .collection("histories")
                .addSnapshotListener {
                    snapshot, e ->
                if (e != null) {
                    Log.w("Listen failed", e)
                    return@addSnapshotListener
                }
                snapshot?.let {
                    val allSpecimens = ArrayList<History>()
                    val documents = snapshot.documents
                    documents.forEach { it ->
                        val specimen = it.toObject(History::class.java)
                        specimen?.let {
                            allSpecimens.add(it)
                        }
                    }
                    listHistory.value = allSpecimens
                }
            }
        }
    }
}