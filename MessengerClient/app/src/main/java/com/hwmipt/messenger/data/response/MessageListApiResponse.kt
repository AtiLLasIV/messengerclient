package com.hwmipt.messenger.data.response

import com.hwmipt.messenger.data.model.MessageModel

data class MessageListApiResponse(
    val messages: List<MessageModel>
)
