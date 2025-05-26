package com.hwmipt.messenger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwmipt.messenger.data.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MessageListViewModel(
    private val repository: ChatRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MessageListState())
    val state: StateFlow<MessageListState> get() = _state

    fun loadMessages(chatId: Int) {
        viewModelScope.launch {
            val messages = repository.getMessages(chatId) ?: emptyList()
            _state.value = _state.value.copy(messages = messages)
        }
    }

    fun sendMessage(chatId: Int, text: String) {
        viewModelScope.launch {
            val result = repository.sendMessage(chatId, text) ?: emptyList()
            _state.value = _state.value.copy(messages = result)
        }
    }
}