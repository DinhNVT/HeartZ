package com.example.heartz.view.content

import android.graphics.PointF
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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

@Composable
fun HeartRateScreen(){
    val auth: FirebaseAuth = Firebase.auth
    val database: DatabaseReference = Firebase.database.reference
    val userId = auth.currentUser?.uid

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment= Alignment.Center,
                modifier = Modifier
                    .padding(0.dp, 16.dp)
                    .size(200.dp)
                    .border(
                        BorderStroke(
                            25.dp,
                            color = colorResource(id = R.color.main_color)
                        ),
                        shape = CircleShape
                    )
                    .clip(shape = CircleShape)
                    .background(Color.White, shape = CircleShape)
            )
            {
                Column() {
                    Text(
                        text = "78",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 50.sp,
                            fontWeight = FontWeight.Bold,

                        ),
                        color = Color.Black,
                        modifier = Modifier
                            .defaultMinSize(24.dp)
                    )
                    Text(
                        text ="BPM",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 30.sp,
                            ),
                        color = Color.Black,
                        modifier = Modifier
                            .defaultMinSize(24.dp)
                    )
                }
            }

            Button(
                onClick = {
                          //TODO REQUEST
                    database.child("state").child("do").setValue(1)
                    database.child("state").child("user_id").setValue(userId)
                },
                shape = RoundedCornerShape(20),
                modifier = Modifier
                    .width(100.dp)
                    .padding(0.dp, 20.dp, 0.dp, 4.dp)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(android.graphics.Color.rgb(38, 198, 218)),
                    contentColor = Color.White
                ),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_play_circle_outline_24),
                    contentDescription = "Play",
                    modifier = Modifier.size(140.dp)
                )
            }

            LinearChartApplication()
        }
    }
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

            drawPath(path, color = Color(android.graphics.Color.rgb(38, 198, 218)), style = Stroke(width = 8f))
        }
    }
}

@Composable
fun LinearChartApplication() {
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
                data = provideMockData(),
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