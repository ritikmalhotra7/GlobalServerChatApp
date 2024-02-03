package com.app.globalserverchatapp.data.remote.dto

import com.app.globalserverchatapp.domain.model.MessageModel
import kotlinx.serialization.Serializable
import java.text.DateFormat
import java.util.Date

@Serializable
data class MessageDto(
    val uid: String,
    val text: String,
    val timeStamp: Long,
    val userName: String,
) {
    fun toMessage(): MessageModel {
        val date = Date(timeStamp)
        val formattedTime = DateFormat.getDateInstance(DateFormat.DEFAULT).format(date)
        return MessageModel(text = text, formattedTime = formattedTime, userName = userName)
    }
}
