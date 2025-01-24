package com.developbharat.admobpoc

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.developbharat.admobpoc.ui.theme.AdmobPOCTheme
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions

class MainActivity : ComponentActivity() {
    private val BANNER_AD_UNIT_ID = "ca-app-pub-2808790498981347/2774831010"
    private val INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-2808790498981347/8417500934"
//    private val NATIVE_ADVANCED_AD_UNIT_ID = "ca-app-pub-2808790498981347/9530174574"
    private val NATIVE_ADVANCED_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110" // test id

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AdmobPOCTheme {
                Scaffold(bottomBar = {
                    AdmobBanner(modifier = Modifier.fillMaxWidth(), adsUnitId = BANNER_AD_UNIT_ID)
                }) { paddingValues ->
                    Surface(modifier = Modifier.padding(paddingValues)) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                                showInterstialAd(this@MainActivity, INTERSTITIAL_AD_UNIT_ID)
                            }) {
                                Text("Show Interstitial Ad")
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                                showNativeAdvancedAd(this@MainActivity, NATIVE_ADVANCED_AD_UNIT_ID)
                            }) {
                                Text("Show Native Advanced")
                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun AdmobBanner(modifier: Modifier = Modifier, adsUnitId: String) {
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = adsUnitId
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}

private fun showInterstialAd(activity: Activity, adUnitId: String) {
    InterstitialAd.load(
        activity,
        adUnitId,
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                interstitialAd.show(activity)
            }
        }
    )
}


private fun showNativeAdvancedAd(activity: Activity, adUnitId: String) {
    val adLoader = AdLoader.Builder(activity, adUnitId)
        .forNativeAd { ad: NativeAd ->
            // Show the ad.
        }
        .withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                // Handle the failure.
            }
        })
        .withNativeAdOptions(NativeAdOptions.Builder().build())
        .build()

    adLoader.loadAd(AdRequest.Builder().build())
}