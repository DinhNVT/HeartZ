package com.example.heartz.view.content

import android.app.DatePickerDialog
import android.graphics.Color.rgb
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.heartz.LoadingAnimation
import com.example.heartz.R
import com.example.heartz.model.MUser
import com.example.heartz.viewmodel.ProfileViewModel
import java.util.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.EmailAuthProvider.getCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

@Composable
fun ChangeProfileScreen(navController: NavController,
    viewModel: ProfileViewModel = viewModel()
){
    val mUser: MUser? by viewModel.mUser.observeAsState()
    if (mUser!=null){
        ChangeProfileScreenContent(navController = navController,
        mUser = mUser)
    }
    else{
        LoadingAnimation()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChangeProfileScreenContent(navController: NavController,
                               mUser: MUser?
){
    var textFullName = remember { mutableStateOf("${mUser?.fullName}") }
    var textBirth = remember { mutableStateOf("${mUser?.birth}") }
    var textPhone = remember { mutableStateOf("${mUser?.phone}") }
    var textStatePasswordNew = remember { mutableStateOf("") }
    var textStatePasswordCallback = remember { mutableStateOf("") }
    val selectedGender = remember { mutableStateOf(
        if(mUser?.gender == true) "Nam" else "Nữ"
    ) }

    var passwordVisible  = remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    val timeOld = textBirth.value.split("/").toTypedArray()
    //Date time picker
    val mContext = LocalContext.current
    var mYear: Int
    var mMonth: Int
    var mDay: Int
    val mCalendar = Calendar.getInstance()
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    if(timeOld.size==3)
    {
        mYear = Integer.parseInt(timeOld[2])
        mMonth = Integer.parseInt(timeOld[1])-1
        mDay = Integer.parseInt(timeOld[0])
    }

    mCalendar.time = Date()

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            textBirth.value = "$mDayOfMonth/${mMonth+1}/$mYear"
        }, mYear, mMonth, mDay,
    )

    val auth: FirebaseAuth = Firebase.auth
    val userId = auth.currentUser?.uid
    val db = FirebaseFirestore.getInstance()
    val washingtonRef = db.collection("users").document(userId.toString())
    val user2 = Firebase.auth.currentUser!!
    Column(
        modifier = Modifier.padding(10.dp)
    ) {
//        Log.d("DATE", textBirth.value)
        Text(text = "Chỉnh sửa thông tin",
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = Color(rgb(38, 198, 218)))

        OutlinedTextField(
            value = textFullName.value,
            onValueChange = {textFullName.value = it},
            label = { Text("Họ và tên", color = if(textFullName.value.isEmpty()) Color(
                rgb(255, 121, 121)
            ).copy(alpha = 0.5f)
            else Color(rgb(76, 175, 80))
            ) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor =  if(textFullName.value.isEmpty()) Color(rgb(255, 121, 121))
                else Color(rgb(76, 175, 80)),
                unfocusedBorderColor = if(textFullName.value.isEmpty()) Color(rgb(38, 198, 218))
                else Color(rgb(76, 175, 80))
            ),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp),
            singleLine = true,
            trailingIcon = {
                if (textFullName.value.isNotEmpty()) {
                    IconButton(onClick = { textFullName.value = "" }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = null,
                            tint = Color(rgb(38, 198, 218))
                        )
                    }
                }
            }
        )

        OutlinedTextField(
            value = textPhone.value,
            onValueChange = {textPhone.value = it},
            label = { Text("Phone", color = if(textPhone.value.isEmpty()) Color(
                rgb(255, 121, 121)
            ).copy(alpha = 0.5f)
            else Color(rgb(76, 175, 80))
            ) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor =  if(textPhone.value.isEmpty()) Color(rgb(255, 121, 121))
                else Color(rgb(76, 175, 80)),
                unfocusedBorderColor = if(textPhone.value.isEmpty()) Color(rgb(38, 198, 218))
                else Color(rgb(76, 175, 80))
            ),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp),
            singleLine = true,
            trailingIcon = {
                if (textPhone.value.isNotEmpty()) {
                    IconButton(onClick = { textPhone.value = "" }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = null,
                            tint = Color(rgb(38, 198, 218))
                        )
                    }
                }
            }
        )
        
        OutlinedTextField(
            value = textBirth.value,
            onValueChange = {textBirth.value = it},
            label = { Text("Sinh nhật", color = if(textBirth.value.isEmpty()) Color(
                rgb(255, 121, 121)
            ).copy(alpha = 0.5f)
            else Color(rgb(76, 175, 80))
            ) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor =  if(textBirth.value.isEmpty()) Color(rgb(255, 121, 121))
                else Color(rgb(76, 175, 80)),
                unfocusedBorderColor = if(textBirth.value.isEmpty()) Color(rgb(38, 198, 218))
                else Color(rgb(76, 175, 80))
            ),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            maxLines = 1,
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp),
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = {
                    mDatePickerDialog.show()
                }) {
                    Icon(
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = null,
                        tint = Color(rgb(38, 198, 218))
                    )
                }
            }
        )
        
        Row(
            modifier = Modifier.padding(top = 10.dp, start = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "Giới tính: ",
            color = Color(rgb(38, 198, 218)))
            RadioButton(selected = selectedGender.value == "Nam", onClick = {
                selectedGender.value = "Nam"
            },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color(rgb(38, 198, 218)),
                    unselectedColor = Color(rgb(38, 198, 218)).copy(alpha = 0.5f),
                    disabledColor = Color.LightGray))
            Text("Nam",
                color = Color(rgb(38, 198, 218)))
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(selected = selectedGender.value == "Nữ", onClick = {
                selectedGender.value = "Nữ"
            },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color(rgb(38, 198, 218)),
                    unselectedColor = Color(rgb(38, 198, 218)).copy(alpha = 0.5f),
                    disabledColor = Color.LightGray))
            Text("Nữ",
            color = Color(rgb(38, 198, 218)))
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Button(onClick = {
                navController.popBackStack()
            },
                shape = RoundedCornerShape(10),
                modifier = Modifier
                    .padding(0.dp, 14.dp, 0.dp, 4.dp)
                    .height(50.dp)
                    .width(170.dp),
                border = BorderStroke(1.dp, color = Color(rgb(38, 198, 218))),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color(rgb(38, 198, 218))),
            ){
                Text(text = "Hủy", style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                )
            }

            Spacer(modifier = Modifier.width(20.dp))
            Button(onClick = {
                if(textStatePasswordNew.value==textStatePasswordCallback.value&&textStatePasswordNew.value.length>=6)
                {
                    val credential = getCredential(auth.currentUser?.email.toString(), textStatePasswordNew.value)
                    user2.reauthenticate(credential)
                        .addOnCompleteListener { Log.d("TAG", "User re-authenticated. ${textStatePasswordNew.value}") }
                }
                else if(textStatePasswordNew.value!=textStatePasswordCallback.value){
                    Toast.makeText(context, "Bạn nhập sai thông tin mật khẩu", Toast.LENGTH_SHORT).show()
                }

                if(textStatePasswordNew.value==textStatePasswordCallback.value){
                    washingtonRef
                        .update("phone", textPhone.value,
                            "full_name", textFullName.value,
                            "birth", textBirth.value,
                            "gender", selectedGender.value=="Nam"
                        )
                        .addOnSuccessListener {
                            navController.popBackStack()
                            Toast.makeText(context, "Thay đổi thành công", Toast.LENGTH_SHORT).show()
                            Log.d("TAG", "DocumentSnapshot successfully updated!")
                        }
                        .addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }
                }
            },

                shape = RoundedCornerShape(10),
                modifier = Modifier
                    .padding(0.dp, 14.dp, 0.dp, 4.dp)
                    .height(50.dp)
                    .width(170.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(rgb(38, 198, 218)),
                    contentColor = Color.White),
            ){
                Text(text = "Lưu", style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                )
            }
        }
    }
}