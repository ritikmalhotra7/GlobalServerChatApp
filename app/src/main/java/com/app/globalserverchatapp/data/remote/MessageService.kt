package com.app.globalserverchatapp.data.remote

import com.app.globalserverchatapp.domain.model.MessageModel

interface MessageService {

    suspend fun getAllMessages():List<MessageModel>

    companion object{
        const val BASE_URL = "http://10.0.2.2:8080"
    }

    sealed class Endpoints(val url:String){
        object GetAllMessages:Endpoints("$BASE_URL/messages")
    }
}