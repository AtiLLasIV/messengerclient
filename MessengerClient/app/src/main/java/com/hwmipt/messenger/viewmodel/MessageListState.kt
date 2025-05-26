package com.hwmipt.messenger.viewmodel

import com.hwmipt.messenger.data.model.MessageModel

data class MessageListState(
    val messages: List<MessageModel> = emptyList(),
)
