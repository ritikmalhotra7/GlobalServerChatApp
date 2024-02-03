package com.app.globalserverchatapp.data.remote

import com.app.globalserverchatapp.data.remote.dto.MessageDto
import com.app.globalserverchatapp.domain.model.MessageModel
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class MessageServiceImpl(private val client: HttpClient):MessageService {
    override suspend fun getAllMessages(): List<MessageModel> {
        return try{
            client.get<List<MessageDto>>(MessageService.Endpoints.GetAllMessages.url).map { it.toMessage() }
        }catch(e:Exception){
            emptyList()
        }
    }
}