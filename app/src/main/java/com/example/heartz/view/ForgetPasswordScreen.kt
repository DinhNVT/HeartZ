package com.example.heartz.view


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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.heartz.R
import com.example.heartz.navigation.Screen

@ExperimentalComposeUiApi
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ForgetPasswordScreen(navController: NavHostController, scrollableState: ScrollState = rememberScrollState()) {
    val textStateEmailAddress = remember { mutableStateOf("") }
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.Start)
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
                text = "Quên mật khẩu",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(0.dp, 8.dp)
                    .align(Alignment.Start)
            )
            Text(
                text = "Vui lòng nhập email  của bạn để xác minh việc đặt lại mật khẩu",
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 8.dp)
                    .align(Alignment.Start),
                color = Color.LightGray
            )

            OutlinedTextField(
                value = textStateEmailAddress.value,
                onValueChange = { textStateEmailAddress.value = it },
                label = {
                    Text(
                        "Địa chỉ email*",
                        color = if (textStateEmailAddress.value.isEmpty()) Color(rgb(255, 121, 121)).copy(alpha = 0.5f)
                        else Color(rgb(76, 175, 80))
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = if (textStateEmailAddress.value.isEmpty()) Color(
                        rgb(
                            255,
                            121,
                            121
                        )
                    )
                    else Color(rgb(76, 175, 80)),
                    unfocusedBorderColor = if (textStateEmailAddress.value.isEmpty()) Color(
                        rgb(
                            38,
                            198,
                            218
                        )
                    )
                    else Color(rgb(76, 175, 80))
                ),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 8.dp),
                singleLine = true,
                trailingIcon = {
                    if (textStateEmailAddress.value.isNotEmpty()) {
                        IconButton(onClick = { textStateEmailAddress.value = "" }) {
                            Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = null
                            )
                        }
                    }
                }
            )

            Button(
                onClick = {
                    navController.navigate(Screen.Login.route)
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