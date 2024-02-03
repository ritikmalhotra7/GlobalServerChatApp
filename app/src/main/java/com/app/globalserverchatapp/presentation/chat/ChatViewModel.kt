package com.app.globalserverchatapp.presentation.chat

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.globalserverchatapp.data.remote.ChatSocketService
import com.app.globalserverchatapp.data.remote.MessageService
import com.app.globalserverchatapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageService: MessageService,
    private val chatSocketService: ChatSocketService,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _messageText = mutableStateOf("")
    val messageText = _messageText

    private val _state = mutableStateOf(ChatState())
    val state = _state

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    fun connectToSocket() {
        getAllMessages()
        savedStateHandle.get<String>("username")?.let { username ->
            viewModelScope.launch {
                val result = chatSocketService.initSession(username)
                when (result) {
                    is Resource.Success -> {
                        chatSocketService.observeMessage().onEach {message ->
                            val newList = state.value.messages.toMutableList().apply {
                                add(0,message)
                            }
                            _state.value = state.value.copy(
                                messages = newList
                            )
                        }.launchIn(viewModelScope)
                    }

                    is Resource.Error -> {
                        _toastEvent.emit(result.message ?: "Unknown Error")
                    }
                }
            }
        }
    }

    fun onMessageChange(message: String) {
        _messageText.value = message
    }

    fun disconnect() = viewModelScope.launch {
        chatSocketService.closeSession()
    }

    fun sendMessage() = viewModelScope.launch {
        if (messageText.value.isNotBlank()) chatSocketService.sendMessage(messageText.value)
    }

    fun getAllMessages() {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)
            val result = messageService.getAllMessages()
            _state.value = state.value.copy(
                messages = result,
                isLoading = false
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnect()
    }
}