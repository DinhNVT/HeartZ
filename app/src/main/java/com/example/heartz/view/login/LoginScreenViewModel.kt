package com.example.heartz.view.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heartz.model.History
import com.example.heartz.model.MUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.sql.Timestamp

class LoginScreenViewModel: ViewModel() {
//     val loadingState = MutableStateFlow(LoadingState.IDLE)

    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun signInWithEmailAndPassword(email: String,
                                   password: String,
                                   home: (Boolean) -> Unit)
            = viewModelScope.launch{
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{ task->
                    if(task.isSuccessful) {
                        Log.d(
                            "FB",
                            "signInWithEmailAndPassword: Ok! ${task.result.toString()}"
                        )
                        home(true)
                    }else{
                        Log.d("FB", "signInWithEmailAndPassword: ")
                        home(false)
                    }
                }
        }catch (ex: Exception){
            Log.d("FB", "signInWithEmailAndPassword: ${ex.message}")
        }
    }

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        fullName: String,
        home: (Boolean) -> Unit){
        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val displayName = task.result?.user?.email?.split('@')?.get(0)
                        createUser(displayName, fullName)
                        home(true)
                    } else {
                        Log.d("FB", "createUserWithEmailAndPassword:")
                        home(false)
                    }
                    _loading.value = false
                }
        }
    }

    private fun createUser(displayName: String?, fullName: String) {
        val userId = auth.currentUser?.uid

        val user = MUser(
            userId = userId.toString(),
            displayName = displayName.toString(),
            avatarUrl = "",
            phone = "",
            fullName = fullName,
            gender = false,
            birth = "",
            id = null,
            histories = listOf(History(bpm = "123", outcome = "false", time = Timestamp(System.currentTimeMillis())))).toMap()

        FirebaseFirestore.getInstance().collection("users").document(userId.toString())
            .set(user)
            .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }
    }
}