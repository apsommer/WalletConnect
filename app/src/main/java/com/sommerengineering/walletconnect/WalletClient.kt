package com.sommerengineering.walletconnect

import android.annotation.SuppressLint
import android.util.Log
import com.reown.android.Core
import com.reown.android.CoreClient
import com.reown.android.relay.ConnectionType
import com.reown.walletkit.client.Wallet
import com.reown.walletkit.client.WalletKit
import timber.log.Timber

fun initWalletConnect(
    application: MainApplication) {

    val projectId = BuildConfig.walletConnectProjectId
    val connectionType = ConnectionType.MANUAL
    val telemetryEnabled = true
    val appMetaData = Core.Model.AppMetaData(
        name = "World Wallet",
        description = "Description of my world wallet ...",

        // todo
        url = "https://etherscan.io/address/0x7d38077562ecb1b7c38135bb261f3aa30a8cfc54",
        icons = listOf("todo"),
        redirect = null
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
        onError = { onCoreError(it) },
    )

    val initParams = Wallet.Params.Init(
        core = CoreClient
    )

    WalletKit.initialize(
        params = initParams,
        onSuccess = { onWalletInitSuccess() },
        onError = { onWalletInitError(it) })

    ///////////// delegate

    val walletDelegate = object : WalletKit.WalletDelegate {

        override fun onSessionProposal(sessionProposal: Wallet.Model.SessionProposal, verifyContext: Wallet.Model.VerifyContext) {
            // Triggered when wallet receives the session proposal sent by a Dapp
        }

//        fun onSessionAuthenticate(sessionAuthenticate: Wallet.Model.SessionAuthenticate, verifyContext: Wallet.Model.VerifyContext) {
//            // Triggered when wallet receives the session authenticate sent by a Dapp
//        }

        override fun onSessionRequest(sessionRequest: Wallet.Model.SessionRequest, verifyContext: Wallet.Model.VerifyContext) {
            // Triggered when a Dapp sends SessionRequest to sign a transaction or a message
        }

//        override fun onAuthRequest(authRequest: Wallet.Model.AuthRequest, verifyContext: Wallet.Model.VerifyContext) {
//            // Triggered when Dapp / Requester makes an authorization request
//        }

        override fun onSessionDelete(sessionDelete: Wallet.Model.SessionDelete) {
            // Triggered when the session is deleted by the peer
        }

        override fun onSessionExtend(session: Wallet.Model.Session) {
            TODO("Not yet implemented")
        }

        override fun onSessionSettleResponse(settleSessionResponse: Wallet.Model.SettledSessionResponse) {
            // Triggered when wallet receives the session settlement response from Dapp
        }

        override fun onSessionUpdateResponse(sessionUpdateResponse: Wallet.Model.SessionUpdateResponse) {
            // Triggered when wallet receives the session update response from Dapp
        }

        override fun onConnectionStateChange(state: Wallet.Model.ConnectionState) {
            //Triggered whenever the connection state is changed
        }

        override fun onError(error: Wallet.Model.Error) {
            // Triggered whenever there is an issue inside the SDK
        }
    }

    WalletKit.setWalletDelegate(walletDelegate)
    //////////////



    // namespaces
    val namespaces = mapOf("eip155" to Wallet.Model.Namespace.Session(
        methods = listOf(
            "eth_accounts",
            "eth_requestAccounts",
            "eth_sendRawTransaction",
            "eth_sign",
            "eth_signTransaction",
            "eth_signTypedData",
            "eth_signTypedData_v3",
            "eth_signTypedData_v4",
            "eth_sendTransaction",
            "personal_sign",
            "wallet_switchEthereumChain",
            "wallet_addEthereumChain",
            "wallet_getPermissions",
            "wallet_requestPermissions",
            "wallet_registerOnboarding",
            "wallet_watchAsset",
            "wallet_scanQRCode",
            "wallet_sendCalls",
            "wallet_getCallsStatus",
            "wallet_showCallsStatus",
            "wallet_getCapabilities"),
        events = listOf(
            "chainChanged",
            "accountsChanged",
            "message",
            "disconnect",
            "connect",),
        accounts = listOf(
            "eip155:1:0x163f8C2467924be0ae7B5347228CABF260318753"))
    )

    val sessionProposal: Wallet.Model.SessionProposal =  /* an object received by `fun onSessionProposal(sessionProposal: Wallet.Model.SessionProposal)` in `WalletKit.WalletDelegate` */
    val sessionNamespaces = WalletKit.generateApprovedNamespaces(
        sessionProposal,
        namespaces)

    val proposerPublicKey: String = /*Proposer publicKey from SessionProposal object*/

    val approveParams: Wallet.Params.SessionApprove = Wallet.Params.SessionApprove(proposerPublicKey, namespaces)
    WalletKit.approveSession(approveParams) { error -> /*callback for error while approving a session*/ }

}

fun onWalletInitSuccess() {
    Log.d(TAG, "onWalletInitSuccess: ")
}

fun onCoreError(
    error: Core.Model.Error) {
    Log.d(TAG, "onCoreError: $error")
}

fun onWalletInitError(
    error: Wallet.Model.Error) {
    Log.d(TAG, "onWalletInitError: $error")
}