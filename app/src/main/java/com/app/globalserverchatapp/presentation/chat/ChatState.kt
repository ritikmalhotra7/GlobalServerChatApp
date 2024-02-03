package com.app.globalserverchatapp.presentation.chat

import com.app.globalserverchatapp.domain.model.MessageModel

data class ChatState(
    val messages:List<MessageModel> = emptyList(),
    val isLoading:Boolean = false
)
