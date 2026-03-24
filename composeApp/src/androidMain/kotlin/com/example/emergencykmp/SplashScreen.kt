package com.example.emergencykmp

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.SettingsVoice
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val PrimaryBlue = Color(0xFF135BEC)
val BgDark = Color(0xFF101622)
val BgMid = Color(0xFF0D1C3A)

@Composable
fun SplashScreen(
    primaryBlue: Color = Color(0xFF135BEC),
    onFinished: () -> Unit,

) {

    val progress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1800)
        )
        onFinished()
    }

//    LaunchedEffect(Unit) {
//        delay(1800)
//        onFinished()
//    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(BgDark, BgMid, BgDark)
                )
            )
    ) {

        // 🔵 Radial glow
        Box(
            modifier = Modifier
                .size(500.dp)
                .align(Alignment.Center)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            PrimaryBlue.copy(alpha = 0.25f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Spacer(Modifier.height(50.dp))

            // 🛡️ Logo
            LogoBlock()

            // ⬇️ Bottom
            BottomLoading(
                progress.value,
                primaryBlue
            )
        }
    }
}


@Composable
private fun LogoBlock() {
    Column() {
    Box(
        modifier = Modifier
            .size(140.dp)
            .background(
                Color.White.copy(alpha = 0.05f),
                RoundedCornerShape(28.dp)
            )
            .border(
                1.dp,
                Color.White.copy(alpha = 0.1f),
                RoundedCornerShape(28.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.Security,
            contentDescription = null,
            tint = PrimaryBlue,
            modifier = Modifier.size(64.dp)
        )
    }

        Spacer(Modifier.height(30.dp))

        Surface(
        color = PrimaryBlue.copy(alpha = 0.1f),
        shape = RoundedCornerShape(50)
    ) {
        Text(
            "SECURE ENVIRONMENT",
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            fontSize = 10.sp,
            color = PrimaryBlue,
            fontWeight = FontWeight.Bold
        )
    }



    }
}

@Composable
private fun BottomLoading(progress: Float, primaryBlue: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = 32.dp)
    ) {

        Row {
            Text(
                text = "Voice",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "Guard",
                color = PrimaryBlue,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
        Spacer(Modifier.height(12.dp))

        // Progress bar
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(6.dp)
                .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(50))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress.coerceIn(0f, 1f))
                    .background(primaryBlue, RoundedCornerShape(50))
            )
        }

        Spacer(Modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Outlined.SettingsVoice,
                contentDescription = null,
                tint = PrimaryBlue,
                modifier = Modifier.size(14.dp)
            )
            Spacer(Modifier.width(6.dp))
            Text(
                "Initializing listener",
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.5f),
                letterSpacing = 1.sp
            )
        }
    }
}
