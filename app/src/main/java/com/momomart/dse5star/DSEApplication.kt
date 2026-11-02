package com.momomart.dse5star
import android.app.Application
class DSEApplication: Application(){
    override fun onCreate(){ super.onCreate(); AdManager.init(this) }
}