package com.example.heartz.view

import android.graphics.Color.rgb
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.heartz.navigation.NavMain
import com.example.heartz.R
import com.example.heartz.navigation.Screen
import com.example.heartz.viewmodel.ProfileViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.heartz.view.content.*

@Composable
fun MainScreen(navControllerMain: NavHostController){
    var navController = rememberNavController()
    var title = remember{ mutableStateOf(NavMain.Home.titleHeader) }
    Scaffold(
        topBar = { TopBar(title = title.value) },
        bottomBar = {
            BottomNavigation(navController = navController){
                    newTitle->title.value = newTitle
            }}
    ) {
        Navigation(navController = navController, navControllerMain=navControllerMain)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Navigation( navController: NavHostController, navControllerMain: NavHostController){
    NavHost(
        navController = navController,
        startDestination = NavMain.Home.route){

        composable(route = NavMain.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(route = NavMain.HeartRate.route) {
            HeartRateScreen()
        }

        composable(route = NavMain.History.route) {
            HistoryScreen()
        }

        composable(route = NavMain.Profile.route) {
            ProfileScreen(navControllerMain = navControllerMain,
            navController = navController)
        }

        composable(route = NavMain.ChangeProfile.route){
            ChangeProfileScreen(navController = navController)
        }

        composable(route = NavMain.ChangePassword.route){
            ChangePassword(navController = navController)
        }
    }
}

@Composable
fun TopBar(title: String){
    TopAppBar(
        contentColor = Color.White,
        backgroundColor =Color(rgb(38, 198, 218))
    ){
        Text(text = title, fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp)) }
}

@Composable
fun BottomNavigation(navController: NavHostController, TopTitle: (String) -> Unit){
    var items = listOf(
        NavMain.Home,
        NavMain.HeartRate,
        NavMain.History,
        NavMain.Profile
    )

    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.White
    ){
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        var currentRoute = navBackStackEntry?.destination?.route

        items.forEach(){
                item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    TopTitle(item.titleHeader)
                    navController.navigate(item.route){
                        navController.graph.startDestinationRoute?.let {
                                route -> popUpTo(route){
                            saveState = true
                        }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    item.icon?.let { painterResource(id = it) }?.let {
                        Icon(painter = it,
                            contentDescription = item.title,
                            modifier = Modifier.size(25.dp))
                    }
                },
                label = {Text(text = item.title)},
                selectedContentColor = Color(rgb(38, 198, 218)),
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true
            )
        }
    }
}