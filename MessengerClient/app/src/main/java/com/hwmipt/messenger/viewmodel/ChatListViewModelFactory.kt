package com.hwmipt.messenger.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.createSavedStateHandle
import com.hwmipt.messenger.data.ChatRepository

class ChatListViewModelFactory(
    private val repository: ChatRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras): T
    {
        val savedStateHandle = extras.createSavedStateHandle()
        return ChatListViewModel(repository, savedStateHandle) as T
    }
}