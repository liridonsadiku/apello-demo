package com.valtech.apollodemo.feature

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.linphone.core.Call
import org.linphone.core.Core

@HiltViewModel
class CallViewModel @Inject constructor() : ViewModel() {

    private val _callUiState = mutableStateOf<CallUiState>(CallUiState.Idle)
    val callUiState: State<CallUiState> = _callUiState

    var currentCall: Call? = null
        private set

    fun onCallStateChanged(call: Call, state: Call.State?) {
        when (state) {
            Call.State.IncomingReceived -> {
                currentCall = call
                _callUiState.value = CallUiState.Incoming(call.remoteAddress.username!!)
            }

            Call.State.OutgoingInit -> {
                currentCall = call
                _callUiState.value = CallUiState.Outgoing(call.remoteAddress.username!!)
            }

            Call.State.StreamsRunning, Call.State.Connected -> {
                _callUiState.value = CallUiState.InCall
            }

            Call.State.End, Call.State.Error, Call.State.Released -> {
                _callUiState.value = CallUiState.Ended
                currentCall = null
            }

            else -> {}
        }
    }

    fun acceptIncomingCall(core: Core) {
        currentCall?.let {
            val params = core.createCallParams(it)
            params?.isVideoEnabled = true
            it.acceptWithParams(params)
        }
    }

    fun declineCall() {
        currentCall?.terminate()
    }

    fun endCall() {
        currentCall?.terminate()
    }
}
