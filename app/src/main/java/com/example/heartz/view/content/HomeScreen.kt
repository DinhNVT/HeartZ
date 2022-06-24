package com.example.heartz.view.content

import android.graphics.Color.rgb
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.heartz.R

@Composable
fun HomeScreen(navController: NavController){
    //val listImage = listOf<Int>(1, 2, 3, 4, 5, 4, 3, 2, 5)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 16.dp)
            .padding(top = 30.dp),
    ) {
//        LazyColumn(
//            modifier = Modifier.padding(bottom = 60.dp)
//        ){
//            items(listImage) { image ->
//                NewspaperItem(image)
//            }
//        }
        Intro()
    }
}

@Composable
fun Intro(){
    Surface() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Đo nhịp tim",
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            color = Color(rgb(38, 198, 218)),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Hãy đo nhịp tim của bạn thường xuyên để kiểm tra tình hình sức khỏe",
            fontSize = 16.sp,
            color = Color(rgb(38, 198, 218)))

            Image(
                painterResource(R.drawable.heart_beat),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(400.dp)
                    .width(300.dp)
            )
        }
    }
}

@Preview
@Composable
fun NewspaperItem(image: Int = 1){
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(start = 10.dp, top = 10.dp, bottom = 0.dp, end = 10.dp)
            .fillMaxWidth()
    ) {
        Column(

        ) {
            Card(
                modifier = Modifier
                    .padding(0.dp)
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RectangleShape,
            ) {
                Image(
                    painterResource(
                        if(image==1) R.drawable.image_1
                        else if(image==2) R.drawable.image_2
                        else if(image==3) R.drawable.image_3
                        else if(image==4) R.drawable.image_4
                        else R.drawable.image_5),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }   
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 20.dp)
            ) {
                Column() {
                    Text(text = "Lorem ipsum dolor sit amet, Lorem ipsum dolor sit amet, ",
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )

                    Text(
                        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                                "Ipsum maecenas bibendum vitae ac nam sit. " +
                                "Varius id tincidunt aliquet aliquam nam eget. " +
                                "Varius id tincidunt aliquet aliquam nam eget",
                        fontWeight = FontWeight.Light,
                        fontSize = 14.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Read more",
                            fontWeight = FontWeight.Light,
                            fontSize = 14.sp,
                            color = Color(rgb(38, 180, 180))
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Icon(painter = painterResource(id = R.drawable.ic_baseline_arrow_right_alt_24),
                            contentDescription = "read more",
                        tint = Color(rgb(38, 180, 180)),
                        modifier = Modifier.padding(top = 2.dp))
                    }

                }

            }
        }
    }
}
