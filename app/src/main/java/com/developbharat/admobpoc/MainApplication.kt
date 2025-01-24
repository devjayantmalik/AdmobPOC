package com.developbharat.admobpoc

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        MobileAds.initialize(this) {}
//        Thread({
//            MobileAds.initialize(this) {}
//            MobileAds.setRequestConfiguration(
//                RequestConfiguration.Builder().setTestDeviceIds(listOf("ABCDEF012345")).build()
//            )
//        }).start();
    }
}