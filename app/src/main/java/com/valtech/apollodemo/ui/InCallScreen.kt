package com.valtech.apollodemo.ui

import android.view.TextureView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import org.linphone.core.Core
import org.linphone.mediastream.video.capture.CaptureTextureView

@Composable
fun InCallScreen(
    core: Core,
    onEnd: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101827))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Video Call with House Manager",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = onEnd,
                    colors = ButtonDefaults.buttonColors(Color.Red),
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(Icons.Default.CallEnd, contentDescription = null, tint = Color.White)
                    Text("End Call", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.Black)
                    .align(Alignment.CenterHorizontally)
            ) {
                AndroidView(factory = { context ->
                    TextureView(context).apply {
                        core.setNativeVideoWindowId(this)
                    }
                }, modifier = Modifier.fillMaxSize())

                Column(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(Color.Green, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Connected", color = Color.Green)
                    }
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Text("House Manager", color = Color.White, style = MaterialTheme.typography.bodyLarge)
                    Text("Available to assist you", color = Color.LightGray, style = MaterialTheme.typography.bodyMedium)
                }

                AndroidView(factory = { context ->
                    CaptureTextureView(context).apply {
                        core.setNativePreviewWindowId(this)
                    }
                }, modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)
                    .border(2.dp, Color.White, RoundedCornerShape(8.dp))
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.width(24.dp))
                IconButton(onClick = onEnd) {
                    Icon(Icons.Default.CallEnd, contentDescription = "End Call", tint = Color.Red)
                }
            }
        }
    }
}
