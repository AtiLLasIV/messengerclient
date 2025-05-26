package com.hwmipt.messenger.data

import com.hwmipt.messenger.data.model.ChatModel
import com.hwmipt.messenger.data.model.MessageModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChatRepository(private val apiService: ApiService) {

    suspend fun getChats(): List<ChatModel>? = withContext(Dispatchers.IO) {
        val response = apiService.getChats()
        if (response.isSuccessful) response.body()?.chats else null
    }

    suspend fun getMessages(chatId: Int): List<MessageModel>? = withContext(Dispatchers.IO) {
        val response = apiService.getChatMessages(chatId)
        if (response.isSuccessful) response.body()?.messages else null
    }

    suspend fun sendMessage(chatId: Int, text: String): List<MessageModel>? = withContext(Dispatchers.IO) {
        val response = apiService.sendMessage(chatId, text)
        if (response.isSuccessful) response.body()?.messages else null
    }

    suspend fun createChat(name: String): List<ChatModel>? = withContext(Dispatchers.IO) {
        val response = apiService.createChat(name)
        if (response.isSuccessful) response.body()?.chats else null
    }
}
