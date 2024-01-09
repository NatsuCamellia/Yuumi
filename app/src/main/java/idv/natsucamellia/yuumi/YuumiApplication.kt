package idv.natsucamellia.yuumi

import android.app.Application

class YuumiApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}