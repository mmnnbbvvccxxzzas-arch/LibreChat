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
                "مرحباً! أنا وكيل Open All Hands المحلي. كيف يمكنني مساعدتك اليوم؟",
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
        // For demo purposes, we'll return responses that simulate Open All Hands agent
        return when {
            userMessage.contains("مرحبا", ignoreCase = true) || userMessage.contains("أهلا", ignoreCase = true) -> 
                "مرحباً بك! أنا وكيل Open All Hands المحلي. كيف يمكنني مساعدتك اليوم؟"
            
            userMessage.contains("اسمك", ignoreCase = true) -> 
                "أنا وكيل Open All Hands المحلي، مساعدك الذكي الشخصي في تطبيق Build X."
            
            userMessage.contains("كيف حالك", ignoreCase = true) -> 
                "أنا بخير، شكراً للسؤال! أنا جاهز دائماً لمساعدتك. ما الذي تريد معرفته اليوم؟"
            
            userMessage.contains("ماذا يمكنك أن تفعل", ignoreCase = true) || userMessage.contains("ما هي قدراتك", ignoreCase = true) -> 
                "يمكنني الإجابة على أسئلتك، ومساعدتك في البحث عن معلومات، وتقديم اقتراحات، وحل المشكلات البرمجية، وتحليل البيانات، والمزيد. ما الذي تحتاج مساعدة فيه؟"
            
            userMessage.contains("شكرا", ignoreCase = true) -> 
                "العفو! سعيد بمساعدتك. أنا وكيل Open All Hands المحلي وأنا هنا دائماً للمساعدة. هل هناك شيء آخر تريد مني مساعدتك فيه؟"
            
            userMessage.contains("من صنعك", ignoreCase = true) || userMessage.contains("من طورك", ignoreCase = true) -> 
                "أنا وكيل ذكاء اصطناعي تم تطويري بواسطة فريق Open All Hands. تم تصميمي لأكون مساعداً ذكياً يمكنه فهم اللغة العربية والتفاعل معها بشكل طبيعي."
            
            userMessage.contains("ذكاء اصطناعي", ignoreCase = true) || userMessage.contains("AI", ignoreCase = true) -> 
                "نعم، أنا نموذج ذكاء اصطناعي مدرب على فهم اللغة الطبيعية والإجابة على الأسئلة. أستطيع التعلم من التفاعلات وتحسين إجاباتي مع مرور الوقت."
            
            userMessage.length < 10 -> 
                "هل يمكنك توضيح سؤالك أكثر لأتمكن من مساعدتك بشكل أفضل؟ أنا وكيل Open All Hands المحلي وأريد أن أقدم لك أفضل مساعدة ممكنة."
            
            else -> {
                val responses = listOf(
                    "أفهم ما تقول. هل تريد مني مساعدتك في شيء محدد؟",
                    "هذا مثير للاهتمام. هل تريد مني تقديم المزيد من المعلومات حول هذا الموضوع؟",
                    "أنا وكيل Open All Hands المحلي وأحاول فهم استفسارك. هل يمكنك توضيح ما تحتاجه بالضبط؟",
                    "شكراً لمشاركة هذه المعلومات. هل هناك شيء محدد تريد مني مساعدتك فيه؟",
                    "أنا هنا للمساعدة في أي موضوع تريد. هل يمكنني تقديم معلومات إضافية حول هذا الموضوع؟"
                )
                responses.random()
            }
        }
    }
}

data class ChatMessage(
    val text: String,
    val isFromUser: Boolean,
    val timestamp: Long
)