package com.example.heartz.view.login

import android.graphics.Color.rgb
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.platform.LocalContext
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.heartz.viewmodel.LoginScreenViewModel
import java.util.regex.Pattern

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Login(scrollableState: ScrollState = rememberScrollState(),
          navController: NavHostController,
          viewModel: LoginScreenViewModel = viewModel()
){
    var textStateUserName = remember { mutableStateOf("") }
    var textStatePassword = remember { mutableStateOf("") }

    var passwordVisible  = remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    val context = LocalContext.current

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White) {
        Column(
            modifier = Modifier
                .padding(16.dp, 0.dp)
                .verticalScroll(scrollableState),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier
                    .size(250.dp)
                    .padding(0.dp, 50.dp, 0.dp, 20.dp),
                painter = painterResource(id = R.drawable.picture_login),
                contentDescription = "picture login")
            Text(
                text = "Đăng nhập",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(0.dp, 8.dp)
            )
            OutlinedTextField(
                value = textStateUserName.value,
                onValueChange = {textStateUserName.value = it},
                label = { Text("Tên đăng nhập", color = if(textStateUserName.value.isEmpty()) Color(rgb(255, 121, 121)).copy(alpha = 0.5f)
                else Color(rgb(76, 175, 80))) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor =  if(textStateUserName.value.isEmpty()) Color(rgb(255, 121, 121))
                    else Color(rgb(76, 175, 80)),
                    unfocusedBorderColor = if(textStateUserName.value.isEmpty()) Color(rgb(38, 198, 218))
                    else Color(rgb(76, 175, 80))),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 8.dp),
                singleLine = true,
                trailingIcon = {
                    if (textStateUserName.value.isNotEmpty()) {
                        IconButton(onClick = { textStateUserName.value = "" }) {
                            Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = null
                            )
                        }
                    }
                }
            )

            OutlinedTextField(
                value = textStatePassword.value,
                onValueChange = {textStatePassword.value = it},
                label = { Text("Mật khẩu", color = if(textStatePassword.value.isEmpty()) Color(rgb(255, 121, 121)).copy(alpha = 0.5f)
                else Color(rgb(76, 175, 80))) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor =  if(textStatePassword.value.isEmpty()) Color(rgb(255, 121, 121))
                    else Color(rgb(76, 175, 80)),
                    unfocusedBorderColor = if(textStatePassword.value.isEmpty()) Color(rgb(38, 198, 218))
                    else Color(rgb(76, 175, 80))),
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 8.dp),
                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                trailingIcon = {
                    val image = if (passwordVisible.value)
                        R.drawable.ic_baseline_visibility_24
                    else R.drawable.ic_baseline_visibility_off_24
                    val description = if (passwordVisible.value) "Hide password" else "Show password"

                    IconButton(onClick = {passwordVisible.value = !passwordVisible.value}){
                        Icon(painter = painterResource(id = image), contentDescription = description)
                    }
                }
            )

            TextButton(onClick = {
                                 navController.navigate(Screen.ForgetPassword.route)
            },
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color(rgb(38, 198, 218))
                ),
                shape = RoundedCornerShape(50.dp)
            ) {
                Text(text = "Quên mật khẩu?", color = Color.Black,
                )
            }

            Button(onClick = {
                if (isValidString(textStateUserName.value)&&textStatePassword.value.length>=6)
                {
                    viewModel.signInWithEmailAndPassword(textStateUserName.value, textStatePassword.value){
                        home->
                        if(!home)
                        {
                            Toast.makeText(
                                context,
                                "Sai email hoặc mật khẩu",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else{
                            Toast.makeText(
                                context,
                                "Đăng nhập thành công",
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.navigate(Screen.Main.route){
                                popUpTo(Screen.Login.route){
                                    inclusive = true
                                }
                            }
                        }
                    }
                }
                else{
                    Toast.makeText(
                        context,
                        "Sai email hoặc mật khẩu",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 14.dp, 0.dp, 4.dp)
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(rgb(38, 198, 218)),
                    contentColor = Color.White),
            ){
                Text(text = "Đăng nhập", style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ))
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Nếu bạn chưa đăng ký?")
                TextButton(onClick = {
                                     navController.navigate(Screen.SignUp.route)
                },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = Color(rgb(38, 198, 218))
                    ),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text(text = "Đăng ký", color = Color(rgb(38, 198, 218)))
                }
            }
        }
    }
}

val EMAIL_ADDRESS_PATTERN = Pattern.compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)

fun isValidString(str: String): Boolean{
    return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
}