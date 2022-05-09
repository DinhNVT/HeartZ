package com.example.heartz.view.content

import android.graphics.Color.rgb
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.heartz.R
import com.example.heartz.navigation.Screen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProfileScreen(scrollableState: ScrollState = rememberScrollState(),
          navController: NavHostController) {
    var textStateUserName = remember { mutableStateOf("") }
    var textStatePassword = remember { mutableStateOf("") }
    var passwordVisible = remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

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
                Image(painter = painterResource(id = R.drawable.mu1),
                    contentDescription = "profile image",
                    modifier = Modifier.size(100.dp),
                    contentScale = ContentScale.Crop)
            }

            CardInfo(title = "Tên", content = "Nguyễn Hữu Dinh" )
            CardInfo(title = "Mật khẩu", content = "***********" )
            CardInfo(title = "Số điện thoại", content = "04234983243" )
            CardInfo(title = "Email", content = "email@gmail.com" )
            CardInfo(title = "Sinh Nhật", content = "Chưa có thông tin" )
            CardInfo(title = "Giới tính", content = "Chưa có thông tin" )

            Button(
                onClick = {
//                    navController.navigate(Screen.Main.route) {
//                        popUpTo(Screen.Login.route) {
//                            inclusive = true
//                        }
//                    }
                },
                shape = RoundedCornerShape(20),
                modifier = Modifier
                    .width(200.dp)
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

@Composable
fun CardInfo(title: String, content: String){
    Card(
        backgroundColor = Color.White,
        elevation = 5.dp,
        modifier = Modifier.padding(0.dp, 8.dp)
            .height(50.dp)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp, 0.dp)
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Left,
                modifier = Modifier.width(130.dp),
                color = Color.Gray
            )
            Text(text = content, color = Color.Gray)
        }
    }
}