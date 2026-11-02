package com.momomart.taxi.hk
import android.app.Application
class TaxiApplication: Application(){
    override fun onCreate(){ super.onCreate(); AdManager.init(this) }
}
