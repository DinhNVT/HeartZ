package com.example.heartz.view.content

import android.graphics.Color.rgb
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.heartz.R

@Composable
fun HistoryScreen(){
    MyContent()
}

@Preview
@Composable
fun MyContent(){
    var mExpanded by remember { mutableStateOf(false) }
    val mCities = listOf("Day", "Month", "Year")
    var mSelectedText by remember { mutableStateOf("") }
    var mTextFieldSize by remember { mutableStateOf(Size.Zero)}
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    val listStatus = listOf<String>("bt", "c", "t", "c", "bt", "c", "t")

    Surface( modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {
        Column(modifier = Modifier
            .background(Color(rgb(38, 198, 218)))) {
            Surface(elevation = 4.dp) {
                Column(
                    Modifier
                        .background(Color.White)
                        .padding(21.dp)
                ) {
                    OutlinedTextField(
                        value = mSelectedText,
                        onValueChange = { mSelectedText = it },
                        modifier = Modifier
                            .width(150.dp)
                            .onGloballyPositioned { coordinates ->
                                mTextFieldSize = coordinates.size.toSize()
                            },
                        placeholder = {Text(text=mCities[0])},
                        trailingIcon = {
                            Icon(icon,"contentDescription",
                                Modifier.clickable { mExpanded = !mExpanded })
                        },
                        readOnly = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(
                                rgb(
                                    38,
                                    198,
                                    218
                                )
                            ),
                            unfocusedBorderColor = Color(
                                rgb(
                                    38,
                                    198,
                                    218
                                )
                            )),
                    )

                    DropdownMenu(
                        expanded = mExpanded,
                        onDismissRequest = { mExpanded = false },
                        modifier = Modifier
                            .width(with(LocalDensity.current){mTextFieldSize.width.toDp()})
                    ) {
                        mCities.forEach { label ->
                            DropdownMenuItem(onClick = {
                                mSelectedText = label
                                mExpanded = false
                            }) {
                                Text(text = label)
                            }
                        }
                    }

                    Row(modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        , horizontalArrangement = Arrangement.SpaceBetween) {
                        Column() {
                            Text(text = "Trung bình",
                                fontSize = 16.sp)
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(text = "78",
                                    fontSize = 32.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "BPM",
                                    modifier = Modifier.padding(bottom = 5.dp))
                            }
                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        Column() {
                            Text(text = "Max",
                                fontSize = 16.sp)
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(text = "79",
                                    fontSize = 32.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "BPM",
                                    modifier = Modifier.padding(bottom = 5.dp))
                            }
                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        Column() {
                            Text(text = "Min",
                                fontSize = 16.sp)
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(text = "77",
                                    fontSize = 32.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "BPM",
                                    modifier = Modifier.padding(bottom = 5.dp))
                            }
                        }
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.padding(bottom = 60.dp)
            ){
                items(listStatus) { status ->
                    StatusItem(status)
                }
            }
        }
    }
}

@Composable
private fun StatusItem(status: String) {
    Column(modifier = Modifier
        .padding(top = 20.dp, bottom = 0.dp, start = 6.dp, end = 6.dp)) {
        Text(text = "Chủ nhật, 15-05-2022",
            modifier = Modifier
                .padding(start = 10.dp, bottom = 4.dp),
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium)
        Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(4.dp))
            .background(Color.White)
            .padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth()
            ) {
            Column() {
                Text(text = "10:24 AM", style = MaterialTheme.typography.caption)

                Text(buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.DarkGray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("100 ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color.DarkGray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light
                        )
                    ) {
                        append("BPM")
                    }
                })
            }
            Row(horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()) {
                Text(text = if(status=="bt"){ "Bình thường"} else if(status=="c"){ "Cao"}
                else {"Thấp"})
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_circle_24),
                    contentDescription = "",
                    tint = Color(
                        if(status=="bt"){ rgb(16,225,87,)}
                        else if(status=="c"){ rgb(252,227,1,)}
                        else {rgb(239,83,80)})
                )
            }
        }
    }
}