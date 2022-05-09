package com.example.heartz.view

import android.graphics.Color.rgb
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.heartz.R
import com.example.heartz.navigation.Screen
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Composable
fun AnimatedSplashScreen(navController: NavHostController){
    var startAnimation = remember {
        mutableStateOf( false)
    }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation.value) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )

    LaunchedEffect(key1  = true) {
        startAnimation.value = true
        delay( 4000)
        navController.popBackStack()
        navController.navigate(Screen.Login.route)
    }
    Splash(alpha = alphaAnim.value)
}

@Composable
fun Splash(alpha: Float) {
    Box(
        modifier = Modifier
            .angledGradient(colors = listOf(
                Color(rgb(38, 205, 205)),
                Color(rgb(27, 45, 130))
            ), 135f)
            .fillMaxSize()
            .alpha(alpha = alpha),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            modifier = Modifier.size(200.dp),
            contentDescription = "Logo Icon",
        )
    }
}

fun Modifier.angledGradient(colors: List<Color>, degrees: Float) = this.then(
    Modifier.drawBehind {
        val rad = (degrees * PI / 180).toFloat()
        val diagonal = sqrt(size.width * size.width + size.height * size.height)
        val centerOffsetX = cos(rad) * diagonal / 2
        val centerOffsetY = sin(rad) * diagonal / 2

        val startOffset = Offset(
            x = (center.x - centerOffsetX).coerceIn(0f, size.width),
            y = (center.y - centerOffsetY).coerceIn(0f, size.height)
        )
        val endOffset = Offset(
            x = (center.x + centerOffsetX).coerceIn(0f, size.width),
            y = (center.y + centerOffsetY).coerceIn(0f, size.height)
        )

        drawRect(
            brush = Brush.linearGradient(
                colors = colors,
                start = startOffset,
                end = endOffset
            ),
            size = size
        )
    }
)