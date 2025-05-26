package com.hwmipt.messenger.ui

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hwmipt.messenger.MainActivity
import com.hwmipt.messenger.R
import com.hwmipt.messenger.viewmodel.ChatListViewModel
import kotlinx.coroutines.launch

class ChatListFragment : Fragment() {

    private lateinit var viewModel: ChatListViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatListAdapter

    private lateinit var createChatLayout: LinearLayout
    private lateinit var editChatName: EditText
    private lateinit var btnCreateChat: Button
    private lateinit var btnCancelChat: Button
    private lateinit var fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_chat_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (requireActivity() as MainActivity).chatListViewModel

        recyclerView = view.findViewById(R.id.list_chats)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ChatListAdapter { chat ->
            viewModel.selectChat(chat.id)
            adapter.setSelectedChatId(chat.id)

            val isPortrait = activity?.findViewById<View?>(R.id.message_fragment) == null

            if (isPortrait) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MessageListFragment())
                    .addToBackStack(null)
                    .commit()
            } else {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.message_fragment, MessageListFragment())
                    .commit()
            }
        }
        recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    adapter.submitList(state.chats)
                    adapter.setSelectedChatId(state.selectedChatId)
                }
            }
        }
        viewModel.loadChats()

        createButtons(view)
    }

    private fun createButtons(view: View) {
        fab = view.findViewById(R.id.fab_add_chat)
        createChatLayout = view.findViewById(R.id.create_chat_layout)
        editChatName = view.findViewById(R.id.edit_chat_name)
        btnCreateChat = view.findViewById(R.id.btn_create_chat)
        btnCancelChat = view.findViewById(R.id.btn_cancel_chat)

        fab.setOnClickListener {
            createChatLayout.visibility = View.VISIBLE
            fab.hide()
        }

        btnCancelChat.setOnClickListener {
            createChatLayout.visibility = View.GONE
            fab.show()
            editChatName.text.clear()
        }

        btnCreateChat.setOnClickListener {
            val name = editChatName.text.toString().trim()
            if (name.isNotEmpty()) {
                viewModel.createChat(name)
                createChatLayout.visibility = View.GONE
                fab.show()
                editChatName.text.clear()
            }
        }
    }
}