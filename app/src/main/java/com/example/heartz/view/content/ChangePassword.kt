package com.example.heartz.view.content

import android.graphics.Color.rgb
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.heartz.R
import com.example.heartz.navigation.Screen
import com.google.firebase.auth.FirebaseAuth

@ExperimentalComposeUiApi
@Composable
fun ChangePassword(navController: NavController, scrollableState: ScrollState = rememberScrollState()) {
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(0.dp, 16.dp)
            ){
                OutlinedButton(onClick = { navController.popBackStack() },
                    modifier= Modifier
                        .padding(0.dp, 0.dp, 0.dp, 50.dp)
                        .size(50.dp),
                    shape = CircleShape,
                    border= BorderStroke(1.dp, Color(rgb(38, 198, 218))),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor =  Color(rgb(38, 198, 218))
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                        contentDescription = "Back"
                    )
                }
                Spacer(modifier = Modifier.width(60.dp))
                Image(
                    modifier = Modifier
                        .size(150.dp)
                        .padding(0.dp, 10.dp, 0.dp, 10.dp),
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo"
                )
            }

            Text(
                text = "Đổi mật khẩu",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(0.dp, 8.dp)
                    .align(Alignment.Start)
            )
            Text(
                text = "Vui lòng xem mail hoặc thư rác trong email của bạn",
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 8.dp)
                    .align(Alignment.Start),
                color = Color.LightGray
            )

            Button(
                onClick = {
                    FirebaseAuth.getInstance().sendPasswordResetEmail( FirebaseAuth.getInstance().currentUser?.email.toString())
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                navController.popBackStack()
                                Toast.makeText(
                                    context,
                                    "Chúng tôi đã gửi cho bạn email về cập nhật lại mật khẩu",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Email không tồn tại",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                },
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 14.dp, 0.dp, 4.dp)
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(rgb(38, 198, 218)),
                    contentColor = Color.White
                ),
            ) {
                Text(
                    text = "Lấy mật khẩu", style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
            }
        }
    }
}