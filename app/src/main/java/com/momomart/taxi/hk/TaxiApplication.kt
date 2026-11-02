package com.momomart.taxi.hk
import android.app.Application
class DSEApplication: Application(){
    override fun onCreate(){ super.onCreate(); AdManager.init(this) }
}
