package com.buildx.app.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.buildx.app.R

class ChatFragment : Fragment() {

    private lateinit var chatViewModel: ChatViewModel
    private lateinit var recyclerChat: RecyclerView
    private lateinit var etMessage: EditText
    private lateinit var btnSend: ImageButton
    private lateinit var ivSettings: ImageView
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        chatViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_chat, container, false)

        // Initialize views
        recyclerChat = root.findViewById(R.id.recycler_chat)
        etMessage = root.findViewById(R.id.et_message)
        btnSend = root.findViewById(R.id.btn_send)
        ivSettings = root.findViewById(R.id.iv_settings)

        // Set up RecyclerView
        chatAdapter = ChatAdapter(chatViewModel.messages.value ?: emptyList())
        recyclerChat.apply {
            layoutManager = LinearLayoutManager(context).apply {
                stackFromEnd = true
            }
            adapter = chatAdapter
        }

        // Observe messages
        chatViewModel.messages.observe(viewLifecycleOwner) { messages ->
            chatAdapter.updateMessages(messages)
            recyclerChat.scrollToPosition(messages.size - 1)
        }

        // Set up send button
        btnSend.setOnClickListener {
            val message = etMessage.text.toString().trim()
            if (message.isNotEmpty()) {
                chatViewModel.sendMessage(message)
                etMessage.text.clear()
            }
        }

        // Set up settings button
        ivSettings.setOnClickListener {
            // Open settings or chat options
        }

        return root
    }
}

class ChatAdapter(private var messages: List<ChatMessage>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_USER = 1
        private const val VIEW_TYPE_AI = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_USER) {
            UserMessageViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_message_user,
                    parent,
                    false
                )
            )
        } else {
            AIMessageViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_message_ai,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        if (holder is UserMessageViewHolder && message.isFromUser) {
            holder.bind(message)
        } else if (holder is AIMessageViewHolder && !message.isFromUser) {
            holder.bind(message)
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isFromUser) VIEW_TYPE_USER else VIEW_TYPE_AI
    }

    fun updateMessages(newMessages: List<ChatMessage>) {
        this.messages = newMessages
        notifyDataSetChanged()
    }

    class UserMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: ChatMessage) {
            // Bind user message
        }
    }

    class AIMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: ChatMessage) {
            // Bind AI message
        }
    }
}