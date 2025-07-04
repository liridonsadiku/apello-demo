package com.valtech.apollodemo.feature

sealed class CallUiState {
    object Idle : CallUiState()
    data class Outgoing(val callee: String) : CallUiState()
    data class Incoming(val caller: String) : CallUiState()
    object InCall : CallUiState()
    object Ended : CallUiState()
}