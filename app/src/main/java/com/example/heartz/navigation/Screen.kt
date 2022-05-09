package com.example.heartz.navigation

sealed class Screen(val route: String){
    object Splash:Screen("splash_screen")
    object Main:Screen("main_screen")
    object Login:Screen("login_screen")
    object SignUp:Screen("sign_up_screen")
    object ForgetPassword:Screen("forget_password_screen")
}
