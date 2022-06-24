package com.example.heartz.view.content

import android.graphics.PointF
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.heartz.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import android.graphics.Color.rgb
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.layout.ContentScale
import com.example.heartz.viewmodel.StatusViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.heartz.LoadingAnimation

@Composable
fun HeartRateScreen(viewModel: StatusViewModel = viewModel()){
    val listECGNew: Int? by viewModel.status.observeAsState()
    if (listECGNew!=null){
        HeartMainContent(listECGNew)
    }
    else{
        LoadingAnimation()
    }
}
 @Composable
 fun HeartMainContent(listECGNew: Int?){
     val auth: FirebaseAuth = Firebase.auth
     val database: DatabaseReference = Firebase.database.reference
     val userId = auth.currentUser?.uid

     Surface(
         modifier = Modifier
             .fillMaxSize()
             .padding(vertical = 16.dp)
     ) {
         Column(
             horizontalAlignment = Alignment.CenterHorizontally,
         ) {
             Log.d("TAG", listECGNew.toString())
//            Box(
//                contentAlignment= Alignment.Center,
//                modifier = Modifier
//                    .padding(0.dp, 16.dp)
//                    .size(200.dp)
//                    .border(
//                        BorderStroke(
//                            25.dp,
//                            color = colorResource(id = R.color.main_color)
//                        ),
//                        shape = CircleShape
//                    )
//                    .clip(shape = CircleShape)
//                    .background(Color.White, shape = CircleShape)
//            )
//            {
//                Column() {
//                    Text(
//                        text = "78",
//                        textAlign = TextAlign.Center,
//                        style = TextStyle(
//                            fontSize = 50.sp,
//                            fontWeight = FontWeight.Bold,
//                        ),
//                        color = Color(rgb(38, 198, 218)),
//                        modifier = Modifier
//                            .defaultMinSize(24.dp)
//                    )
//                    Text(
//                        text ="BPM",
//                        textAlign = TextAlign.Center,
//                        style = TextStyle(
//                            fontSize = 30.sp,
//                            ),
//                        color = Color(rgb(173,53,1)),
//                        modifier = Modifier
//                            .defaultMinSize(24.dp)
//                    )
//                }
//            }

             if (listECGNew == 1)
             {
                 Text(text =  "Đang đo...",
                     fontSize = 24.sp,
                     fontWeight = FontWeight.Bold,
                     modifier = Modifier
                         .fillMaxWidth(),
                     textAlign = TextAlign.Center,
                     color = Color(rgb(38, 198, 218))
                 )
             }
             else if(listECGNew==0) {
                 Text(text =  "Đang không đo",
                     fontSize = 24.sp,
                     fontWeight = FontWeight.Bold,
                     modifier = Modifier
                         .fillMaxWidth(),
                     textAlign = TextAlign.Center,
                     color = Color(rgb(38, 198, 218))
                 )
             }

             Image(
                 painterResource(R.drawable.heart_beat),
                 contentDescription = "",
                 contentScale = ContentScale.Fit,
                 modifier = Modifier
                     .height(300.dp)
                     .width(200.dp)
             )

             Spacer(modifier = Modifier.height(150.dp))

             Button(
                 onClick = {
                     database.child("state").child("do").setValue(1)
                     database.child("state").child("user_id").setValue(userId.toString())
                 },
                 shape = RoundedCornerShape(20),
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(10.dp, 20.dp, 10.dp, 4.dp)
                     .height(50.dp),
                 colors = ButtonDefaults.buttonColors(
                     backgroundColor = Color(rgb(38, 198, 218)),
                     contentColor = Color.White
                 ),
             ) {
                 Icon(
                     painter = painterResource(id = R.drawable.ic_baseline_play_circle_outline_24),
                     contentDescription = "Play",
                 )
                 Spacer(modifier = Modifier.width(8.dp))
                 Text(text = "Bắt đầu đo",
                     fontSize = 24.sp)
             }
//            listECGNew?.let { LinearChartApplication(it,listEcg) }
         }
     }
 }
fun listt(ecg: Int, list: MutableList<Int>): MutableList<Int> {
    val listtt: MutableList<Int> = list
    for(j in listtt.indices){
            if(j!=listtt.size-1)
                listtt[j] = listtt[j+1]
            else
                listtt[listtt.size-1] = ecg
        }
    return listtt
}
sealed class LinearChartStyle {
    object Default : LinearChartStyle()
    object Smooth : LinearChartStyle()
}

@Composable
fun LinearChart(
    modifier: Modifier = Modifier,
    style: LinearChartStyle = LinearChartStyle.Default,
    data: List<Int>
) {
    Canvas(modifier = modifier) {
        val distance = size.width / (data.size + 1)
        var currentX = 0F
        val maxValue = data.maxOrNull() ?: 0
        val points = mutableListOf<PointF>()

        data.forEachIndexed { index, currentData ->
            if (data.size >= index + 2) {
                val y0 = (maxValue - currentData) * (size.height / maxValue)
                val x0 = currentX + distance
                points.add(PointF(x0, y0))
                currentX += distance
            }
        }

        if (style == LinearChartStyle.Default) {
            for (i in 0 until points.size - 1) {
                drawLine(
                    start = Offset(points[i].x, points[i].y),
                    end = Offset(points[i + 1].x, points[i + 1].y),
                    color = Color(android.graphics.Color.rgb(38, 198, 218)),
                    strokeWidth = 8f
                )
            }
        } else {
            val cubicPoints1 = mutableListOf<PointF>()
            val cubicPoints2 = mutableListOf<PointF>()
            for (i in 1 until points.size) {
                cubicPoints1.add(PointF((points[i].x + points[i - 1].x) / 2, points[i - 1].y))
                cubicPoints2.add(PointF((points[i].x + points[i - 1].x) / 2, points[i].y))
            }

            val path = Path()
            path.moveTo(points.first().x, points.first().y)

            for (i in 1 until points.size) {
                path.cubicTo(
                    cubicPoints1[i - 1].x,
                    cubicPoints1[i - 1].y,
                    cubicPoints2[i - 1].x,
                    cubicPoints2[i - 1].y,
                    points[i].x,
                    points[i].y
                )
            }

            drawPath(path, color = Color(rgb(38, 198, 218)), style = Stroke(width = 8f))
        }
    }
}

@Composable
fun LinearChartApplication(ecg:Int, list: MutableList<Int>) {
    var lists:MutableList<Int> = list
    lists = listt(ecg, list)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentSize(align = Alignment.BottomStart)
        ) {
            LinearChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                data = lists,
                style = LinearChartStyle.Smooth
            )
        }
    }
}

private fun provideMockData() = listOf(
    5929, 6898, 8961, 5674, 7122, 6592, 3427, 5520, 4680, 7418,
    4743, 4080, 3611, 7295, 9900, 12438, 11186, 5439, 4227, 5138,
    11115, 8386, 12450, 10411, 10852, 7782, 7371, 4983, 9234, 6847
)

private fun dataOne() = listOf(
    1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
    1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
    1, 1, 1, 1, 1, 1, 1, 1, 1, 1
)