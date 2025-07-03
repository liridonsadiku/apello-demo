package com.valtech.apollodemo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.valtech.apollodemo.ui.HomeScreen
import com.valtech.apollodemo.ui.theme.ApolloDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApolloDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen { name ->
                        when (name) {
                            "Alarm" -> println("You clicked on Alarm")
                            "Messages" -> println("You clicked on Messages")
                            "I'm OK" -> println("You clicked on I'm OK")
                            "Call Manager" -> println("You clicked on Call Manager")
                            "Repairs" -> println("You clicked on Repairs")
                            "Call a Neighbour" -> println("You clicked on Call a Neighbour")
                            else -> Toast.makeText(this, "Unknown button clicked", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ApolloDemoTheme {
        //  Greeting("Android")
    }
}