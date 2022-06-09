package com.example.heartz

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.heartz.navigation.SetupNavGraph
import com.example.heartz.ui.theme.HeartZTheme
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HeartZTheme {
//                val db = FirebaseFirestore.getInstance()
//                val user: MutableMap<String, Any> = HashMap()
//                user["firstName"] = "Jeo"
//                user["lastName"] = "James"
//
//                db.collection("users")
//                    .add(user)
//                    .addOnSuccessListener {
//                        Log.d("FB", "onCreate: ${it.id}")
//                    }
//                    .addOnFailureListener{
//                        Log.d("FB", "onCreate: ${it}")
//                    }
                val navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}