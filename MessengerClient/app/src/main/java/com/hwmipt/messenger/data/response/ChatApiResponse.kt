package com.hwmipt.messenger.data.response

import com.hwmipt.messenger.data.model.MessageModel

data class ChatApiResponse(
    val id: Int,
    val name: String,
    val messages: List<MessageModel>
)
