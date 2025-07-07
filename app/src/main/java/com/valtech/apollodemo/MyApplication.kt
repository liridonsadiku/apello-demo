package com.valtech.apollodemo

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.util.Log
import com.valtech.apollodemo.feature.CallViewModel
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

    lateinit var callViewModel: CallViewModel


    @SuppressLint("ServiceCast")
    override fun onCreate() {
        super.onCreate()
        // Initialize your app here if needed
        callViewModel = CallViewModel()

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

//        uncomment this only when you've set your device as device owner with the following adb command:
//        adb shell dpm set-device-owner com.valtech.apollodemo/.MyDeviceAdminReceiver

//        val adminComponentName = ComponentName(this, MyDeviceAdminReceiver::class.java)
//        val devicePolicyManager = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
//        devicePolicyManager.setLockTaskPackages(adminComponentName, arrayOf(packageName))

        // Add CoreListener
        coreListener = object : CoreListenerStub() {
            override fun onCallStateChanged(core: Core, call: Call, state: Call.State?, message: String) {
                callViewModel.onCallStateChanged(call, state)
            }

            override fun onRegistrationStateChanged(core: Core, proxyConfig: ProxyConfig, state: RegistrationState?, message: String) {
                Log.d("aa22aa", "onRegistrationStateChanged State: $state - $message")
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
