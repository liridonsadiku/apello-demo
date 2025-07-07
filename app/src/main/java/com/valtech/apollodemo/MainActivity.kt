package com.valtech.apollodemo

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.valtech.apollodemo.feature.CallUiState
import com.valtech.apollodemo.ui.HomeScreen
import com.valtech.apollodemo.ui.InCallScreen
import com.valtech.apollodemo.ui.IncomingCallScreen
import com.valtech.apollodemo.ui.OutgoingCallScreen
import com.valtech.apollodemo.ui.dailycheckin.DailyCheckInDialog
import com.valtech.apollodemo.ui.theme.ApolloDemoTheme
import org.linphone.core.Core
import org.linphone.core.Factory

class MainActivity : ComponentActivity() {

    val username = "robertss"
    val password = "AppelloTest"
    val domain = "sip.linphone.org"

    val username2 = "liridon"
    val password2 = "Prishtina123"
    val domain2 = "sip.linphone.org"

    val username3 = "borasadiku"
    val password3 = "Prishtina456"
    val domain3 = "sip.linphone.org"

    val username4 = "malsadiku"
    val password4 = "Prishtina123"
    val domain4 = "sip.linphone.org"

    val receiverUsername = "malsadiku" // user that will receive the call

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val viewModel = (application as MyApplication).callViewModel
        val callUiState by viewModel.callUiState

        startLockTask()

        enableEdgeToEdge()
        val core = (applicationContext as MyApplication).linphoneCore
        setContent {
            var showDialog by remember { mutableStateOf(false) }

            ApolloDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    if (showDialog) {
                        DailyCheckInDialog(onDismiss = { showDialog = false })
                    }

                    when (callUiState) {
                        is CallUiState.Incoming -> {
                            IncomingCallScreen(
                                caller = (callUiState as CallUiState.Incoming).caller,
                                onAccept = { viewModel.acceptIncomingCall(core) },
                                onDecline = { viewModel.declineCall() }
                            )
                        }

                        is CallUiState.Outgoing -> {
                            OutgoingCallScreen(
                                callee = (callUiState as CallUiState.Outgoing).callee,
                                onCancel = { viewModel.endCall() }
                            )
                        }

                        is CallUiState.InCall -> InCallScreen(core = core, onEnd = { viewModel.endCall() })
                        is CallUiState.Ended, CallUiState.Idle -> {

                            HomeScreen { name ->
                                when (name) {
                                    "Alarm" -> {
                                        // user that will accept the call
                                        val address = core.interpretUrl("sip:$receiverUsername@sip.linphone.org")

                                        val params = core.createCallParams(null)
                                        params?.isVideoEnabled = true
                                        core.inviteAddressWithParams(address!!, params!!)

                                        println("You clicked on Alarm")
                                    }

                                    "Messages" -> {
                                        println("You clicked on Messages")
                                    }

                                    "I'm OK" -> showDialog = true

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

        val permissions = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET
        )

        requestPermissions(permissions, 0)
        configureSipAccountCaller((applicationContext as MyApplication).linphoneCore, username2, password2, domain2)
    }

    override fun onResume() {
        super.onResume()
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_FULLSCREEN

    }

    //current user that will make the call
    private fun configureSipAccountCaller(core: Core, username: String, password: String, domain: String) {
        val factory = Factory.instance()
        val authInfo = factory.createAuthInfo(username, null, password, null, null, domain)
        core.addAuthInfo(authInfo)

        val proxyCfg = core.createProxyConfig()
        val identity = factory.createAddress("sip:$username@$domain")
        proxyCfg.identityAddress = identity
        proxyCfg.serverAddr = "sip:$domain"
        proxyCfg.isRegisterEnabled = true

        core.addProxyConfig(proxyCfg)
        core.defaultProxyConfig = proxyCfg
        core.isVideoCaptureEnabled = true
        core.isVideoDisplayEnabled = true
        //        core.usePreviewWindow(true)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ApolloDemoTheme {
    }
}