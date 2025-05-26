package com.hwmipt.messenger

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.hwmipt.messenger.data.ChatRepository
import com.hwmipt.messenger.data.RetrofitInstance
import com.hwmipt.messenger.ui.ChatListFragment
import com.hwmipt.messenger.ui.MessageListFragment
import com.hwmipt.messenger.viewmodel.ChatListViewModel
import com.hwmipt.messenger.viewmodel.ChatListViewModelFactory
import com.hwmipt.messenger.viewmodel.MessageListViewModel
import com.hwmipt.messenger.viewmodel.MessageListViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var chatListViewModel: ChatListViewModel
    lateinit var messageListViewModel: MessageListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = ChatRepository(RetrofitInstance.api)

        chatListViewModel = ViewModelProvider(
            this,
            ChatListViewModelFactory(repository)
        )[ChatListViewModel::class.java]

        messageListViewModel = MessageListViewModelFactory(repository)
            .create(MessageListViewModel::class.java)

        if (findViewById<View?>(R.id.message_fragment) != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.chat_fragment, ChatListFragment())
                .replace(R.id.message_fragment, MessageListFragment())
                .commit()
        } else {
            lifecycleScope.launch {
                chatListViewModel.state.collectLatest { state ->
                    val fragment = if (state.selectedChatId != null) {
                        MessageListFragment()
                    } else {
                        ChatListFragment()
                    }

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .apply {
                            if (fragment is MessageListFragment) {
                                addToBackStack(null)
                            }
                        }
                        .commit()
                }
            }
        }
    }
}