package com.sommerengineering.walletconnect

import androidx.lifecycle.ViewModel
import com.reown.android.Core
import com.reown.android.CoreClient
import com.reown.android.relay.ConnectionType
import com.reown.walletkit.client.Wallet
import com.reown.walletkit.client.WalletKit

class WalletConnectViewModel() : ViewModel() {


}

fun initWalletKit() {

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
        application = this, // todo,
        metaData = appMetaData,
        telemetryEnabled = telemetryEnabled
    )

    val initParams = Wallet.Params.Init(
        core = CoreClient
    )

    WalletKit.initialize(initParams) { error ->
        // todo Error will be thrown if there's an issue during initialization
    }
}