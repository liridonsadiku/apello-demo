package com.valtech.apollodemo

import android.app.Application
import android.content.Intent
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import java.util.Timer
import java.util.TimerTask
import org.linphone.core.Call
import org.linphone.core.Core
import org.linphone.core.CoreListenerStub
import org.linphone.core.Factory
import org.linphone.core.ProxyConfig
import org.linphone.core.RegistrationState
import org.linphone.core.tools.service.CoreService

@HiltAndroidApp
class MyApplication : Application() {
    lateinit var linphoneCore: Core
    private lateinit var coreListener: CoreListenerStub
    private var timer: Timer? = null

    override fun onCreate() {
        super.onCreate()
        // Initialize your app here if needed

        Factory.instance().setDebugMode(true, "Linphone")

        // You must provide the Android app context as createCore last param !
        linphoneCore = Factory.instance().createCore(null, null, this.applicationContext)
        linphoneCore.start()

        val serviceIntent = Intent(this, CoreService::class.java)
        startService(serviceIntent)

        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                linphoneCore.iterate()
            }
        }, 0, 20)

        // Add CoreListener
        coreListener = object : CoreListenerStub() {
            override fun onCallStateChanged(core: Core, call: Call, state: Call.State?, message: String) {
                Log.d("CALL", "Call state changed: $state - $message")
                when (state) {
                    Call.State.IncomingReceived -> {
                        //                        val params = core.createCallParams(call)
                        //                        //  params.enableVideo(false)
                        //                        call.acceptWithParams(params)
                        println("liridon state IncomingReceived")

                    }

                    Call.State.End -> {
                        println("liridon state End")
                    }

                    Call.State.StreamsRunning -> {
                        println("liridon state StreamsRunning")
                    }

                    Call.State.Error -> {
                        println("liridon state Error: $message")
                    }

                    Call.State.OutgoingInit -> {
                        println("liridon state OutgoingInit")
                    }

                    Call.State.OutgoingProgress -> {
                        println("liridon state OutgoingProgress")
                    }

                    //                    Call.State.Idle -> TODO()
                    //                    Call.State.PushIncomingReceived -> TODO()
                    //                    Call.State.OutgoingRinging -> TODO()
                    //                    Call.State.OutgoingEarlyMedia -> TODO()
                    //                    Call.State.Connected -> TODO()
                    //                    Call.State.Pausing -> TODO()
                    //                    Call.State.Paused -> TODO()
                    //                    Call.State.Resuming -> TODO()
                    //                    Call.State.Referred -> TODO()
                    //                    Call.State.PausedByRemote -> TODO()
                    //                    Call.State.UpdatedByRemote -> TODO()
                    //                    Call.State.IncomingEarlyMedia -> TODO()
                    //                    Call.State.Updating -> TODO()
                    //                    Call.State.Released -> TODO()
                    //                    Call.State.EarlyUpdatedByRemote -> TODO()
                    //                    Call.State.EarlyUpdating -> TODO()
                    null -> TODO()
                    else -> {
                        println("liridon state ELSE block: $state")
                    }
                }
            }

            @Deprecated("Deprecated in Java")
            override fun onRegistrationStateChanged(core: Core, proxyConfig: ProxyConfig, state: RegistrationState?, message: String) {
                Log.d("REGISTRATION", " liridon State: $state - $message")
            }
        }
        linphoneCore.addListener(coreListener)
    }

    override fun onTerminate() {
        super.onTerminate()
        timer?.cancel()
        linphoneCore.stop()
    }
}
