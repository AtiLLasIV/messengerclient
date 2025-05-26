package com.hwmipt.messenger.ui

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hwmipt.messenger.R
import com.hwmipt.messenger.data.model.MessageModel

class MessageListAdapter : ListAdapter<MessageModel, MessageListAdapter.MessageViewHolder>(DIFF) {

    inner class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val messageText: TextView = view.findViewById(R.id.item_message)
        fun bind(message: MessageModel) {
            messageText.text = message.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<MessageModel>() {
            override fun areItemsTheSame(oldItem: MessageModel, newItem: MessageModel) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MessageModel, newItem: MessageModel) =
                oldItem == newItem
        }
    }
}
