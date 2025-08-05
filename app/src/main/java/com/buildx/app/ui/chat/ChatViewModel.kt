package com.buildx.app.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val _messages = MutableLiveData<List<ChatMessage>>().apply {
        value = listOf(
            ChatMessage(
                "مرحباً، كيف يمكنني مساعدتك اليوم؟",
                false,
                System.currentTimeMillis()
            )
        )
    }
    val messages: LiveData<List<ChatMessage>> = _messages

    fun sendMessage(text: String) {
        val currentMessages = _messages.value?.toMutableList() ?: mutableListOf()
        
        // Add user message
        currentMessages.add(
            ChatMessage(
                text,
                true,
                System.currentTimeMillis()
            )
        )
        _messages.value = currentMessages

        // Simulate AI response after a delay
        viewModelScope.launch {
            delay(1000) // 1 second delay
            
            val updatedMessages = _messages.value?.toMutableList() ?: mutableListOf()
            
            // Add AI response
            updatedMessages.add(
                ChatMessage(
                    getAIResponse(text),
                    false,
                    System.currentTimeMillis()
                )
            )
            
            _messages.postValue(updatedMessages)
        }
    }

    private fun getAIResponse(userMessage: String): String {
        // In a real app, this would call an AI service
        // For demo purposes, we'll return simple responses
        return when {
            userMessage.contains("مرحبا", ignoreCase = true) -> "مرحباً! كيف يمكنني مساعدتك اليوم؟"
            userMessage.contains("اسمك", ignoreCase = true) -> "أنا Build X، مساعدك الذكي الشخصي."
            userMessage.contains("شكرا", ignoreCase = true) -> "العفو! هل هناك شيء آخر يمكنني مساعدتك به؟"
            userMessage.length < 10 -> "هل يمكنك توضيح سؤالك أكثر؟"
            else -> "أفهم ما تقول. هل تريد مني مساعدتك في شيء محدد؟"
        }
    }
}

data class ChatMessage(
    val text: String,
    val isFromUser: Boolean,
    val timestamp: Long
)