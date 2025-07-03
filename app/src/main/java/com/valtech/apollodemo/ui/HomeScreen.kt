package com.valtech.apollodemo.ui

import androidx.compose.runtime.Composable
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(onButtonClick: (String) -> Unit) {

    var currentTime by remember { mutableStateOf(LocalDateTime.now()) }

    // Update time every minute
    LaunchedEffect(Unit) {
        while (true) {
            currentTime = LocalDateTime.now()
            delay(60_000L) // update every 60 seconds
        }
    }

    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val dateFormatter = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy")

    val formattedTime = currentTime.format(timeFormatter)    // e.g., "17:00"
    val formattedDate = currentTime.format(dateFormatter)    // e.g., "Tuesday 1 July 2025"

    val buttons = listOf(
        Triple("Alarm", Icons.Default.Notifications, Color(0xFFE74C3C)),
        Triple("I'm OK", Icons.Default.ThumbUp, Color(0xFF27AE60)),
        Triple("Messages", Icons.Default.Message, Color(0xFFF39C12)),
        Triple("Call Manager", Icons.Default.Person, Color(0xFFE91E63)),
        Triple("Repairs", Icons.Default.Build, Color(0xFF3498DB)),
        Triple("Call a Neighbour", Icons.Default.Call, Color(0xFF9B59B6)),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(24.dp)
    ) {
        // Header Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Text("appello", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Gray)

            Column(horizontalAlignment = Alignment.End) {
                Text(formattedTime, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(formattedDate, fontSize = 14.sp, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Grid of Buttons
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 60.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            itemsIndexed(buttons) { index, (label, icon, color) ->
                Box(
                    modifier = Modifier
                        .aspectRatio(2f)
                        .clip(RoundedCornerShape(20.dp))
                        .background(color)
                        .clickable { onButtonClick(label) }
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (label == "Messages") {
                            Box {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(36.dp)
                                )
                                // Badge
                                Box(
                                    modifier = Modifier
                                        .offset(x = 20.dp, y = (-10).dp)
                                        .size(20.dp)
                                        .clip(CircleShape)
                                        .background(Color.Red),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("3", color = Color.White, fontSize = 12.sp)
                                }
                            }
                        } else {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(label, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(onButtonClick = {})
}
