package com.hwmipt.messenger.viewmodel

import com.hwmipt.messenger.data.model.ChatModel

data class ChatListState(
    val chats: List<ChatModel> = emptyList(),
    val selectedChatId: Int? = null,
)
