package com.example.heartz.view.content

import android.graphics.Color.rgb
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.heartz.R
import com.example.heartz.model.MUser
import com.example.heartz.navigation.Screen
import com.example.heartz.viewmodel.ProfileViewModel
import com.firebase.ui.auth.AuthUI
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.heartz.LoadingAnimation
import com.example.heartz.navigation.NavMain

@Composable
fun ProfileScreen(
    navControllerMain: NavHostController,
    navController: NavController,
    viewModel: ProfileViewModel = viewModel()
){
    val mUser: MUser? by viewModel.mUser.observeAsState()
    if (mUser!=null){
        Content(
            navControllerMain = navControllerMain,
            mUser = mUser,
            navController = navController
        )
    }
    else{
        LoadingAnimation()
    }

}

@Composable
fun Content(
    scrollableState: ScrollState = rememberScrollState(),
    navControllerMain: NavHostController,
    mUser: MUser?,
    viewModel: ProfileViewModel = viewModel(),
    navController: NavController
){
    val context = LocalContext.current
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp, 0.dp)
                .verticalScroll(scrollableState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier
                    .size(150.dp)
                    .padding(16.dp),
                shape = CircleShape,
                border = BorderStroke(4.dp, color = colorResource(id = R.color.main_color)),
                elevation = 4.dp,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
            ){
                Image(painter = painterResource(id = R.drawable.icon_logo),
                    contentDescription = "profile image",
                    modifier = Modifier.size(100.dp),
                    contentScale = ContentScale.Crop)
            }



            if (mUser != null) {
                CardInfo(title = "Họ và tên", content =mUser.fullName){
                    navController.navigate(route = NavMain.ChangeProfile.route)
                }

                CardInfo(title = "Email",
                    content = viewModel.auth.currentUser?.email.toString(),
                    enabled = false){
                }

                CardInfo(title = "Mật khẩu", content = "******" ){
                    navController.navigate(route = NavMain.ChangePassword.route)
                }

                if(mUser.phone=="")
                    CardInfo(title = "Số điện thoại", content = "Thêm" ){
                        navController.navigate(route = NavMain.ChangeProfile.route)
                    }
                else
                    CardInfo(title = "Số điện thoại", content = mUser.phone ){
                        navController.navigate(route = NavMain.ChangeProfile.route)
                    }

                if(mUser.birth=="")
                    CardInfo(title = "Sinh Nhật", content = "Thêm" ){
                        navController.navigate(route = NavMain.ChangeProfile.route)
                    }
                else
                    CardInfo(title = "Sinh Nhật", content = mUser.birth ){
                        navController.navigate(route = NavMain.ChangeProfile.route)
                    }

                if(mUser.gender)
                    CardInfo(title = "Giới tính", content = "Nam"){
                        navController.navigate(route = NavMain.ChangeProfile.route)
                    }
                else
                    CardInfo(title = "Giới tính", content = "Nữ"){
                        navController.navigate(route = NavMain.ChangeProfile.route)
                    }
            }

            Button(
                onClick = {
                    AuthUI.getInstance()
                        .signOut(context)
                        .addOnCompleteListener {
                            Toast.makeText(context, "Bạn đã đăng xuất", Toast.LENGTH_SHORT).show()
                            navControllerMain.navigate(Screen.Login.route) {
                                popUpTo(Screen.Main.route) {
                                    inclusive = true
                                }
                            }

                    }
                },
                shape = RoundedCornerShape(20),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 20.dp, 0.dp, 4.dp)
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(rgb(38, 198, 218)),
                    contentColor = Color.White
                ),
            ) {
                Text(
                    text = "Đăng xuất", style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardInfo(title: String,
             content: String,
             enabled: Boolean = true,
             onClick: ()->Unit,
){
    Card(
        backgroundColor = Color.White,
        elevation = 5.dp,
        modifier = Modifier
            .padding(0.dp, 8.dp)
            .height(50.dp)
            .fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp, 0.dp)
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Left,
                modifier = Modifier.width(110.dp),
                color = Color.Gray
            )
            Text(text = content, color = Color.Gray
            ,modifier = Modifier.width(200.dp),)

            if(enabled)
            Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Icon edit",
            tint = Color.Black.copy(alpha = 0.5f))
        }
    }
}