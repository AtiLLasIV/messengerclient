package com.hwmipt.messenger.ui

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hwmipt.messenger.MainActivity
import com.hwmipt.messenger.R
import com.hwmipt.messenger.viewmodel.ChatListViewModel
import com.hwmipt.messenger.viewmodel.MessageListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MessageListFragment : Fragment() {

    private lateinit var chatListViewModel: ChatListViewModel
    private lateinit var messageListViewModel: MessageListViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var editMessage: EditText
    private lateinit var btnSend: Button
    private lateinit var btnReturn: TextView
    private lateinit var adapter: MessageListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_message_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatListViewModel = (requireActivity() as MainActivity).chatListViewModel
        messageListViewModel = (requireActivity() as MainActivity).messageListViewModel

        recyclerView = view.findViewById(R.id.list_messages)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = MessageListAdapter()
        recyclerView.adapter = adapter

        createButtons(view)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    messageListViewModel.state.collectLatest { state ->
                        adapter.submitList(state.messages)
                        recyclerView.scrollToPosition(state.messages.size - 1)
                    }
                }

                launch {
                    chatListViewModel.state.collectLatest { state ->
                        val chatId = state.selectedChatId
                        if (chatId != null) {
                            messageListViewModel.loadMessages(chatId)
                        }
                    }
                }
            }
        }
    }

    private fun createButtons(view: View) {
        btnReturn = view.findViewById(R.id.btn_return)
        editMessage = view.findViewById(R.id.edit_message)
        btnSend = view.findViewById(R.id.button_send)

        btnReturn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        btnSend.setOnClickListener {
            val text = editMessage.text.toString().trim()
            val chatId = chatListViewModel.state.value.selectedChatId
            if (text.isNotEmpty() && chatId != null) {
                messageListViewModel.sendMessage(chatId, text)
                editMessage.text.clear()
            }
        }
    }
}
