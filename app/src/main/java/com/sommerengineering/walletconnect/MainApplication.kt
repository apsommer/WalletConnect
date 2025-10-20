package com.sommerengineering.walletconnect

import android.app.Application
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

fun initWalletConnect(
    application: MainApplication) {

    val projectId = BuildConfig.walletConnectProjectId
    val connectionType = ConnectionType.AUTOMATIC // ConnectionType.MANUAL
    val telemetryEnabled = true
    val appMetaData = Core.Model.AppMetaData(
        name = "WalletConnect",
        description = "Example app ...",

        // todo
        url = "todo",
        icons = listOf("todo"),
        redirect = "kotlin-wallet-wc:/request" // Custom Redirect URI
    )

    CoreClient.initialize(
        projectId = projectId,
        connectionType = connectionType,
        application = application,
        metaData = appMetaData,
        telemetryEnabled = telemetryEnabled,
        relay = null,
        keyServerUrl = null,
        networkClientTimeout = null,
        onError = { onError(it) },
    )

    val initParams = Wallet.Params.Init(
        core = CoreClient
    )

    WalletKit.initialize(initParams) { error ->
        // todo Error will be thrown if there's an issue during initialization
    }

}

fun onError(
    error: Core.Model.Error) {

    Timber.tag(TAG).d("onError: ${error}")
}