package pl.poznan.put.cs.lab2_155858_barman

import android.app.Application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

class BarmanApplication : Application(), ViewModelStoreOwner {
    private val appViewModelStore = ViewModelStore()

    override val viewModelStore: ViewModelStore
        get() = appViewModelStore

    override fun onTerminate() {
        super.onTerminate()
        appViewModelStore.clear()
    }
}
