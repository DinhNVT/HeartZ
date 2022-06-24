package com.example.heartz.view.content

import android.graphics.Color.rgb
import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.heartz.LoadingAnimation
import com.example.heartz.R
import com.example.heartz.model.MUser
import com.example.heartz.viewmodel.ProfileViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.heartz.model.HistoryDetails
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@Composable
fun HistoryScreen(
    viewModel: ProfileViewModel = viewModel()
){
    val mUser: MUser? by viewModel.mUser.observeAsState()
    if (mUser!=null){
        MyContent(mUser = mUser)
    }
    else{
        LoadingAnimation()
    }
}

@Composable
fun MyContent(mUser: MUser?){
    val str = remember {
        mutableStateOf("${mUser?.histories}")
    }
    val listHistories: List<String> = historyItem(str.value)

    var listAll: List<HistoryDetails> = listHistoriesDetail(listHistories)
    listAll = listAll.sortedByDescending { t->t.time }
    if(str.value=="[]"){
        Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()) {
            Text(text = "Bạn chưa đo lần nào",
            color = Color(rgb(38, 198, 218)),
            fontSize = 24.sp)
        }
    }

    else{
        Log.d("TAG", str.value)
        Surface( modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            Column(modifier = Modifier
                .background(Color(rgb(0, 94, 106)))) {
                LazyColumn(
                    modifier = Modifier.padding(bottom = 60.dp)
                ){
                    items(listAll) { lists ->
                        StatusItem(
                            date = lists.date,
                            hour = lists.hour,
                            bpm = lists.bpm,
                            outcome = lists.outcome)
                    }
                }
            }
        }
    }
}

@Composable
private fun StatusItem(
    date: String = "22-12-2022",
    hour: String = "22:22 AM",
    bpm: String= "12",
    outcome: String = "true",
    ) {
    Column(modifier = Modifier
        .padding(top = 20.dp, bottom = 0.dp, start = 10.dp, end = 10.dp)) {
        Text(text = date,
            modifier = Modifier
                .padding(start = 10.dp, bottom = 4.dp),
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = Color.White)
        Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(4.dp))
            .background(Color.White)
            .padding(top = 16.dp, bottom = 10.dp, start = 16.dp, end = 10.dp)
            .fillMaxWidth()
            ) {
            Column(
                Modifier.width(150.dp)
            ) {
                Text(text = hour, style = MaterialTheme.typography.caption)

//                Text(buildAnnotatedString {
//                    withStyle(
//                        style = SpanStyle(
//                            color = Color.DarkGray,
//                            fontSize = 30.sp,
//                            fontWeight = FontWeight.Bold
//                        )
//                    ) {
//                        append("$bpm ")
//                    }
//                    withStyle(
//                        style = SpanStyle(
//                            color = Color.DarkGray,
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.Light
//                        )
//                    ) {
//                        append("BPM")
//                    }
//
//                },
//                    modifier = Modifier.width(150.dp),)
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text= bpm,
                        color = Color.DarkGray,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                    modifier = Modifier.width(60.dp))
                    Text(text = "BPM",
                        color = Color.DarkGray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.padding(top = 10.dp)
                        )
                }
            }

            Row(horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()) {
                Text(text = if(outcome=="true"){ "Bình thường"} else {"Không bình thường"})
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_circle_24),
                    contentDescription = "",
                    tint = Color(
                        if(outcome=="true"){ rgb(16,225,87,)}
                        else {rgb(239,83,80)})
                )
            }
        }
    }
}

fun historyItem(str: String): List<String> {
    val strNew: String = str.replace("]", "").replace("[", "")
        .replace(" ", "").replace("time=Timestamp(seconds=", "")
        .replace("nanoseconds=", "").replace(")", "")
        .replace("bpm=", "").replace("outcome=", "")
        .replace("},", "#").replace("{", "")
        .replace("}","")
    return strNew.split("#")
}

fun listHistoriesDetail(listHistories: List<String>): List<HistoryDetails>{
    val arrListHistories: ArrayList<HistoryDetails> = arrayListOf()
    for (element in listHistories){
        Log.d("TEST", element)
        val arrList = element.split(",")
        var check = false
        for(i in arrList.indices){
            if(arrList[i]==""||arrList[i]=="null"||arrList[i]==" "||arrList[i]=="  "
                || arrList[i].length<=1) check = true
        }
        if(arrList.size==4 && !check){
            var bpm: String = ""
            var outcome: String = ""
            if(arrList[2]=="false"||arrList[2]=="true"){
                bpm = arrList[3].replace("\n", "").replace(" ", "")
                outcome = arrList[2]
            }
            else{
                bpm = arrList[2].replace("\n", "").replace(" ", "")
                outcome = arrList[3]
            }
            var date: String = ""
            var hour: String = ""
            convertTimestampToDate(Integer.parseInt(arrList[0])){
                    str->
                date = str.split(", ")[0]
                hour = str.split(", ")[1]
            }
            val historyDetails: HistoryDetails
                    = HistoryDetails(
                bpm = bpm,
                outcome = outcome,
                date = date,
                hour = hour,
                time = Integer.parseInt(arrList[0])
            )
            if(bpm.length<=4)
            arrListHistories.add(historyDetails)
        }

    }
    return arrListHistories
}

fun convertTimestampToDate(timeStamp: Int, date: (String)->Unit){
    val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.ENGLISH)
    val str: String = simpleDateFormat.format(timeStamp * 1000L)

    date(str)
}