package com.momomart.dse5star
import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.*
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
object AdManager {
    const val BANNER_ID = "ca-app-pub-3595462838327387/4473556802"
    const val INTER_ID = "ca-app-pub-3595462838327387/7999749175"
    const val APPOPEN_ID = "ca-app-pub-3595462838327387/2932410270"
    const val REWARD_ID = "ca-app-pub-3595462838327387/1817484203"
    var appOpenAd: AppOpenAd? = null
    var interstitialAd: InterstitialAd? = null
    var rewardedAd: RewardedAd? = null
    fun init(c: Context){ MobileAds.initialize(c); loadAppOpen(c); loadInter(c); loadReward(c) }
    fun loadBanner(v: AdView){ v.loadAd(AdRequest.Builder().build()) }
    fun loadAppOpen(c: Context){
        AppOpenAd.load(c, APPOPEN_ID, AdRequest.Builder().build(), AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, object: AppOpenAd.AppOpenAdLoadCallback(){
            override fun onAdLoaded(ad: AppOpenAd){ appOpenAd = ad }
        })
    }
    fun showAppOpen(a: Activity){ appOpenAd?.show(a) }
    fun loadInter(c: Context){
        InterstitialAd.load(c, INTER_ID, AdRequest.Builder().build(), object: InterstitialAdLoadCallback(){
            override fun onAdLoaded(ad: InterstitialAd){ interstitialAd = ad }
        })
    }
    fun showInter(a: Activity, cb:()->Unit){
        interstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback(){
            override fun onAdDismissedFullScreenContent(){ loadInter(a); cb() }
        }
        interstitialAd?.show(a) ?: cb()
    }
    fun loadReward(c: Context){
        RewardedAd.load(c, REWARD_ID, AdRequest.Builder().build(), object: RewardedAdLoadCallback(){
            override fun onAdLoaded(ad: RewardedAd){ rewardedAd = ad }
        })
    }
    fun showReward(a: Activity, cb:()->Unit){ rewardedAd?.show(a){ cb() } ?: cb() }
}