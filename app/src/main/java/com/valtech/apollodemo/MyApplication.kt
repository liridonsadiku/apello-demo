package com.valtech.apollodemo

import android.annotation.SuppressLint
import android.app.Application
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
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

        // after first app install you need to run this command, then kill the app and run it again and Kiosk mode will be active
        // adb shell dpm set-device-owner com.valtech.apollodemo/.MyDeviceAdminReceiver
        // to kill the Kiosk mode you need to run this command
        // adb shell dpm remove-active-admin com.valtech.apollodemo/.MyDeviceAdminReceiver

        val adminComponentName = ComponentName(this, MyDeviceAdminReceiver::class.java)
        val devicePolicyManager = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        if (devicePolicyManager.isDeviceOwnerApp(packageName)) {
            devicePolicyManager.setLockTaskPackages(adminComponentName, arrayOf(packageName))
            Toast.makeText(this, "Kiosk mode initialized", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "App is not device owner. Kiosk mode not enabled.", Toast.LENGTH_LONG).show()
        }

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
