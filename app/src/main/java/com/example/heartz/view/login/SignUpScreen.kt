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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.heartz.navigation.Screen

@ExperimentalComposeUiApi
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreen(navController: NavHostController,
                 scrollableState: ScrollState = rememberScrollState(),
                 viewModel: LoginScreenViewModel = viewModel()) {
    val textStateFullName = remember { mutableStateOf("") }
    val textStateEmailAddress = remember { mutableStateOf("") }
    val textStatePassword = remember { mutableStateOf("") }
    val textStatePasswordCallback = remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

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
            Image(
                modifier = Modifier
                    .size(150.dp)
                    .padding(0.dp, 10.dp, 0.dp, 10.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo"
            )
            Text(
                text = "Đăng ký",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(0.dp, 8.dp)
                    .align(Alignment.Start)
            )
            Text(
                text = "Điền thông tin của bạn",
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp)
                    .align(Alignment.Start),
                color = Color.LightGray
            )
            OutlinedTextField(
                value = textStateFullName.value,
                onValueChange = { textStateFullName.value = it },
                label = {
                    Text(
                        "Họ tên*",
                        color = if (textStateFullName.value.isEmpty()) Color(rgb(255, 121, 121)).copy(alpha = 0.5f)
                        else Color(rgb(76, 175, 80))
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = if (textStateFullName.value.isEmpty()) Color(
                        rgb(
                            255,
                            121,
                            121
                        )
                    )
                    else Color(rgb(76, 175, 80)),
                    unfocusedBorderColor = if (textStateFullName.value.isEmpty()) Color(
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
                    if (textStateFullName.value.isNotEmpty()) {
                        IconButton(onClick = { textStateFullName.value = "" }) {
                            Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = null
                            )
                        }
                    }
                }
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

            OutlinedTextField(
                value = textStatePassword.value,
                onValueChange = { textStatePassword.value = it },
                label = {
                    Text(
                        "Mật khẩu",
                        color = if (textStatePassword.value.isEmpty()) Color(rgb(255, 121, 121)).copy(alpha = 0.5f)
                        else Color(rgb(76, 175, 80))
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = if (textStatePassword.value.isEmpty()) Color(
                        rgb(
                            255,
                            121,
                            121
                        )
                    )
                    else Color(rgb(76, 175, 80)),
                    unfocusedBorderColor = if (textStatePassword.value.isEmpty()) Color(
                        rgb(
                            38,
                            198,
                            218
                        )
                    )
                    else Color(rgb(76, 175, 80))
                ),
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
                    val description =
                        if (passwordVisible.value) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                        Icon(
                            painter = painterResource(id = image),
                            contentDescription = description
                        )
                    }
                }
            )

            OutlinedTextField(
                value = textStatePasswordCallback.value,
                onValueChange = { textStatePasswordCallback.value = it },
                label = {
                    Text(
                        "Nhập lại mật khẩu*",
                        color = if (textStatePasswordCallback.value.isEmpty()) Color(rgb(255, 121, 121)).copy(alpha = 0.5f)
                        else Color(rgb(76, 175, 80))
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = if (textStatePasswordCallback.value.isEmpty()) Color(
                        rgb(
                            255,
                            121,
                            121
                        )
                    )
                    else Color(rgb(76, 175, 80)),
                    unfocusedBorderColor = if (textStatePasswordCallback.value.isEmpty()) Color(
                        rgb(
                            38,
                            198,
                            218
                        )
                    )
                    else Color(rgb(76, 175, 80))
                ),
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
                    val description =
                        if (passwordVisible.value) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                        Icon(
                            painter = painterResource(id = image),
                            contentDescription = description
                        )
                    }
                }
            )

            Button(
                onClick = {
                    if (isValidString(textStateEmailAddress.value)
                        &&textStatePassword.value.length>=6
                        &&textStatePasswordCallback.value==textStatePassword.value)
                    {
                        viewModel.createUserWithEmailAndPassword(
                            email = textStateEmailAddress.value,
                            password = textStatePassword.value,
                            fullName = textStateFullName.value){
                                home->
                            if(!home)
                            {
                                Toast.makeText(
                                    context,
                                    "Bạn nhập sai thông tin",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else{
                                Toast.makeText(
                                    context,
                                    "Đăng ký thành công",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.navigate(Screen.Login.route)
                                }
                            }
                    }
                    else{
                        if(textStatePasswordCallback.value!=textStatePassword.value)
                        {
                            Toast.makeText(
                                context,
                                "Mật khẩu không khớp",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else{
                            Toast.makeText(
                                context,
                                "Bạn nhập sai thông tin",
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
                    text = "Đăng ký", style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 8.dp)) {
                Text(text = "Bạn đã có tài khoản?")
                TextButton(
                    onClick = {
                        navController.navigate(Screen.Login.route)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = Color(rgb(38, 198, 218))
                    ),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text(text = "Đăng nhập", color = Color(rgb(38, 198, 218)))
                }
            }
        }
    }
}