package com.orlanth23.colisnc

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import java.util.*

/**
 * Created by orlanth23 on 09/10/2017.
 */
class NetworkReceiver private constructor() : BroadcastReceiver() {

    fun listen(networkChangeListener: NetworkChangeListener): Int {
        return if (mNetworkChangeListener.contains(networkChangeListener)) {
            mNetworkChangeListener.indexOf(networkChangeListener)
        } else {
            if (mNetworkChangeListener.add(networkChangeListener)) {
                mNetworkChangeListener.indexOf(networkChangeListener)
            } else {
                -1
            }
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        notifyListener(context)
    }

    interface NetworkChangeListener {
        fun OnNetworkEnable()

        fun OnNetworkDisable()
    }

    companion object {

        val CONNECTIVITY_CHANGE_INTENT_FILTER = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")

        private var mInstance: NetworkReceiver? = null
        private val mNetworkChangeListener = ArrayList<NetworkChangeListener>()

        val instance: NetworkReceiver
            @Synchronized get() {
                if (mInstance == null) {
                    mInstance = NetworkReceiver()
                }
                return mInstance as NetworkReceiver
            }

        fun notifyListener(context: Context): Boolean {
            val conn = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = conn.activeNetworkInfo

            if (networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected) {
                for (networkChangeListener in mNetworkChangeListener) {
                    networkChangeListener.OnNetworkEnable()
                }
                return true
            } else if (networkInfo == null || !networkInfo.isAvailable || !networkInfo.isConnected) {
                for (networkChangeListener in mNetworkChangeListener) {
                    networkChangeListener.OnNetworkDisable()
                }
                return false
            }
            return false
        }

        fun checkConnection(context: Context): Boolean {
            val conn = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = conn.activeNetworkInfo

            if (networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected) {
                return true
            } else if (networkInfo == null || !networkInfo.isAvailable || !networkInfo.isConnected) {
                return false
            }
            return false
        }
    }
}
