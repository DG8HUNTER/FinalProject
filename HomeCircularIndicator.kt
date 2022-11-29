package com.example.expensetrackerproject.Home

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetrackerproject.ui.theme.mediumGray
import com.example.expensetrackerproject.ui.theme.mint
import com.example.expensetrackerproject.ui.theme.onSurface
import kotlin.math.roundToInt
import kotlin.math.roundToLong


@Composable
fun HomeCircularIndicator(income:Float, expenses:Float) {

        val spending = animateFloatAsState(
            targetValue = expenses,
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        )


        val percentage = animateFloatAsState(
            targetValue = if (income == 0f && expenses == 0f) 0f else if (income>=expenses)(expenses / income) else 1f,
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        )
        val indicatorBackgroundColor: Color = Color(0xFFDBE4E6)
        val indicatorForegroundColor = animateColorAsState(targetValue =if(expenses>income)Color.Red else onSurface ,
            animationSpec = tween(durationMillis = 200 , easing = FastOutSlowInEasing)
        )
        val indicatorBackgroundStrokeWidth: Float = 100F
        val indicatorForegroundStrokeWidth: Float = 100F
        val sweepAngle by animateFloatAsState(
            targetValue = percentage.value * 240f, animationSpec = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )

        Column(
            modifier = Modifier

                .fillMaxWidth(0.8f)
                .height(300.dp)

                .drawBehind {
                    val componentSize = size / 1.25f
                    drawBackgroundCircularIndicator(
                        componentSize = componentSize,
                        indicatorBackgroundColor = indicatorBackgroundColor,
                        indicatorBackgroundStrokeWidth = indicatorBackgroundStrokeWidth
                    )
                    drawForegroundCircularIndicator(
                        componentSize = componentSize,
                        indicatorForegroundColor = indicatorForegroundColor.value,
                        indicatorForegroundStrokeWidth = indicatorForegroundStrokeWidth,
                        sweepAngle = sweepAngle
                    )
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = String.format("%.1f",(expenses)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = mint.copy(alpha = 0.5f)
                )
                Text(
                    text = " / $income",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = mint
                )
            }
            if(expenses>income){
                Spacer(modifier=Modifier.height(10.dp))
                Text(text = "+ ${expenses-income}" ,
                    fontSize = 20.sp ,
                    fontWeight = FontWeight.Bold ,
                    color=Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()

                )
            }


        }


    }





fun DrawScope.drawBackgroundCircularIndicator(
    componentSize: Size,
    indicatorBackgroundColor: Color,
    indicatorBackgroundStrokeWidth: Float
) {
    drawArc(
        color = indicatorBackgroundColor,
        size = componentSize,
        startAngle = 150f,
        sweepAngle = 240f,
        useCenter = false,
        style = Stroke(
            width = indicatorBackgroundStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )

}

fun DrawScope.drawForegroundCircularIndicator(
    componentSize: Size,
    indicatorForegroundColor:Color,
    indicatorForegroundStrokeWidth: Float,
    sweepAngle: Float
) {
    drawArc(
        color = indicatorForegroundColor,
        size = componentSize,
        style = Stroke(
            width = indicatorForegroundStrokeWidth,
            cap = StrokeCap.Round
        ),
        useCenter = false,
        startAngle = 150f,
        sweepAngle = sweepAngle,
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}


