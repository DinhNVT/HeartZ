package com.example.heartz.repository

import com.example.heartz.model.MUser
import com.google.android.gms.common.api.Response
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserFromFirestore(): Flow<MUser>

//    suspend fun addBookToFirestore(title: String, author: String): Flow<Response<Void?>>
//
//    suspend fun deleteBookFromFirestore(bookId: String): Flow<Response<Void?>>
}