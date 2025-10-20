package com.sommerengineering.walletconnect

import android.app.Application
import android.util.Log
import com.reown.android.Core
import com.reown.android.CoreClient
import com.reown.android.relay.ConnectionType
import com.reown.walletkit.client.Wallet
import com.reown.walletkit.client.WalletKit
import timber.log.Timber

const val TAG = "~~"

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initWalletConnect(this)
    }
}

