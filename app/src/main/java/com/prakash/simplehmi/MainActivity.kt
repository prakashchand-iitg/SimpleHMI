package com.prakash.simplehmi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prakash.simplehmi.presentation.dashboard.DashboardUiState
import com.prakash.simplehmi.presentation.dashboard.DashboardViewModel

private val Background = Color(0xFF080A0D)
private val Panel = Color(0xFF171B21)
private val Accent = Color(0xFFFF6B24)
private val Green = Color(0xFF21D69A)
private val Red = Color(0xFFFF5252)
private val Muted = Color(0xFF8B929C)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = android.graphics.Color.BLACK
        window.navigationBarColor = android.graphics.Color.BLACK
        setContent {
            MaterialTheme {
                val dashboardViewModel: DashboardViewModel by viewModels()
                val state by dashboardViewModel.uiState.collectAsState()
                DashboardScreen(state)
            }
        }
    }
}

@Composable
private fun DashboardScreen(state: DashboardUiState) {
    Surface(modifier = Modifier.fillMaxSize(), color = Background) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 30.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1.15f).fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("SIMPLE", color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(12.dp))
                        Speedometer(state.speedKmh)
                        Text(
                            state.scenario,
                            color = Accent,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        )
                    }
                    Column(
                        modifier = Modifier.width(150.dp).padding(start = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatusCard("MOTOR", if (state.motorOn) "ON" else "OFF",
                            if (state.motorOn) Green else Red)
                        StatusCard("KILL SWITCH", if (state.killSwitchActive) "ACTIVE" else "INACTIVE",
                            if (state.killSwitchActive) Red else Green)
                        StatusCard("DATA SIGNAL", if (state.signalMessage == null) "NORMAL" else "CHECK",
                            if (state.signalMessage == null) Green else Accent)
                    }
                }
            }
        }
    }
}

@Composable
private fun Speedometer(speed: Int?) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(width = 300.dp, height = 190.dp)) {
        Canvas(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            drawArc(
                color = Color(0xFF303741),
                startAngle = 150f,
                sweepAngle = 240f,
                useCenter = false,
                style = Stroke(width = 13.dp.toPx(), cap = StrokeCap.Round),
                size = Size(size.width, size.height * 1.55f)
            )
            val fraction = ((speed ?: 0).coerceIn(0, 60) / 60f)
            drawArc(
                color = Accent,
                startAngle = 150f,
                sweepAngle = 240f * fraction,
                useCenter = false,
                style = Stroke(width = 13.dp.toPx(), cap = StrokeCap.Round),
                size = Size(size.width, size.height * 1.55f)
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = speed?.toString() ?: "--",
                color = Color.White,
                fontSize = 76.sp,
                fontWeight = FontWeight.Light
            )
            Text("km/h", color = Muted, fontSize = 15.sp, letterSpacing = 3.sp)
        }
    }
}

@Composable
private fun StatusCard(title: String, value: String, color: Color) {
    Column(
        modifier = Modifier.background(Panel, RoundedCornerShape(14.dp)).padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(title, color = Muted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        Text(value, color = color, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}
