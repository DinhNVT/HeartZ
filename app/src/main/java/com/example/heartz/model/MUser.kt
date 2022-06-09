package com.example.heartz.model

data class MUser(
                 val id: String?,
                 val fullName: String,
                 val birth: String,
                 val gender: Boolean,
                 val userId: String,
                 val displayName: String,
                 val avatarUrl: String,
                 val phone: String,
){
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "user_id" to this.userId,
            "display_name" to this.displayName,
            "full_name" to this.fullName,
            "birth" to this.birth,
            "avatar_url" to this.avatarUrl,
            "gender" to this.gender,
            "phone" to this.phone)
    }
}