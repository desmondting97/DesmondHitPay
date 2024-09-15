package com.example.desmondhitpay.util

import android.app.Application

class ContextObjects: Application() {

    companion object {
        const val DATABASE_NAME: String = "hitPayMiniProject.db"
        const val DATABASE_VERSION: Int = 1
    }
}