package com.hwmipt.messenger.ui

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hwmipt.messenger.R
import com.hwmipt.messenger.data.model.ChatModel

class ChatListAdapter(
    private val onClick: (ChatModel) -> Unit
) : ListAdapter<ChatModel, ChatListAdapter.ChatViewHolder>(DIFF) {

    private var selectedChatId: Int? = null

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val chatName: TextView = itemView.findViewById(R.id.item_chat)

        fun bind(chat: ChatModel) {
            chatName.text = chat.name

            val isSelected = chat.id == selectedChatId
            chatName.setBackgroundResource(
                if (isSelected) R.drawable.chat_select_background
                else R.drawable.chat_background
            )
            itemView.setOnClickListener { onClick(chat) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<ChatModel>() {
            override fun areItemsTheSame(oldItem: ChatModel, newItem: ChatModel) =
                oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: ChatModel, newItem: ChatModel) =
                oldItem == newItem
        }
    }

    fun setSelectedChatId(id: Int?) {
        val prevId = selectedChatId
        selectedChatId = id

        val oldId = currentList.indexOfFirst { it.id == prevId }
        if (oldId != -1) notifyItemChanged(oldId)

        val newId = currentList.indexOfFirst { it.id == id }
        if (newId != -1) notifyItemChanged(newId)
    }
}