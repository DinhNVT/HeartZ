package com.example.heartz.navigation

import com.example.heartz.R

sealed class NavMain(var route: String, var icon: Int, var title: String, var titleHeader: String) {
    object Home: NavMain("home", R.drawable.home, "Home", "Hiểu biết về sức khỏe")
    object HeartRate: NavMain("music", R.drawable.heart_rate, "HeartRate", "Đo nhịp tim")
    object History: NavMain("tab", R.drawable.ic_baseline_history_24, "History", "Lịch sử đo")
    object Profile: NavMain("profile", R.drawable.ic_outline_person_outline_24, "Profile", "Tài khoản")
}
