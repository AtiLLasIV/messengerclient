package com.hwmipt.messenger.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwmipt.messenger.data.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatListViewModel(
    private val repository: ChatRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val KEY_SELECT_ID = "selectId"

    private val _state = MutableStateFlow(
        ChatListState(selectedChatId = savedStateHandle.get<Int>(KEY_SELECT_ID))
    )
    val state: StateFlow<ChatListState> get() = _state

    fun loadChats() {
        viewModelScope.launch {
            val chats = repository.getChats() ?: emptyList()
            _state.value = _state.value.copy(chats = chats)
        }
    }

    fun createChat(name: String) {
        viewModelScope.launch {
            val result = repository.createChat(name)
            _state.value = _state.value.copy(chats = result ?: emptyList())
        }
    }

    fun selectChat(id: Int) {
        savedStateHandle[KEY_SELECT_ID] = id
        _state.value = _state.value.copy(selectedChatId = id)
    }
}
