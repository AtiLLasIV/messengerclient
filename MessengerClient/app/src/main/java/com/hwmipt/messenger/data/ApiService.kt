package com.hwmipt.messenger.data

import com.hwmipt.messenger.data.response.ChatApiResponse
import com.hwmipt.messenger.data.response.ChatListApiResponse
import com.hwmipt.messenger.data.response.MessageListApiResponse
import retrofit2.http.*
import retrofit2.Response

interface ApiService {
    @GET("mipt_network/chats")
    suspend fun getChats(): Response<ChatListApiResponse>

    @GET("mipt_network/chat")
    suspend fun getChatMessages(@Query("id") id: Int): Response<ChatApiResponse>

    @POST("mipt_network/msg")
    suspend fun sendMessage(@Query("id") chatId: Int, @Query("text") text: String): Response<MessageListApiResponse>

    @POST("mipt_network/create_chat")
    suspend fun createChat(@Query("name") name: String): Response<ChatListApiResponse>
}
