package org.martellina.task5_fragments

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class Task5_Fragments: Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
}