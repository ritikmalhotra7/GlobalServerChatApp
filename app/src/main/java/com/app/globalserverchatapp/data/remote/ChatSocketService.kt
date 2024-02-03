package com.app.globalserverchatapp.data.remote

import com.app.globalserverchatapp.domain.model.MessageModel
import com.app.globalserverchatapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChatSocketService {
    suspend fun initSession(userName:String):Resource<Unit>
    suspend fun sendMessage(message:String)
    fun observeMessage(): Flow<MessageModel>
    suspend fun closeSession()

    companion object{
        const val BASE_URL = "ws://10.0.2.2:8080"
    }

    sealed class Endpoints(val url:String){
        object ChatSocket:Endpoints("$BASE_URL/chat-socket")
    }
}