package com.buildx.app.repository

import android.util.Log
import com.buildx.app.api.ApiClient
import com.buildx.app.api.ChatRequest
import com.buildx.app.api.ChatResponse
import com.buildx.app.api.Message
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Repository for chat functionality
 */
class ChatRepository {
    
    private val TAG = "ChatRepository"
    
    /**
     * Send a message to the Open All Hands agent
     */
    fun sendMessage(
        messages: List<Message>,
        onSuccess: (ChatResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        val request = ChatRequest(
            messages = messages
        )
        
        ApiClient.openHandsApiService.sendMessage(request).enqueue(object : Callback<ChatResponse> {
            override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { chatResponse ->
                        onSuccess(chatResponse)
                    } ?: run {
                        onError("Response body is empty")
                    }
                } else {
                    onError("Error: ${response.code()} ${response.message()}")
                }
            }
            
            override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                Log.e(TAG, "API call failed", t)
                onError("Network error: ${t.message}")
            }
        })
    }
    
    /**
     * Get a simulated response for offline testing
     */
    fun getSimulatedResponse(userMessage: String): Message {
        // For testing purposes, we'll simulate a response
        val responses = listOf(
            "مرحباً! أنا وكيل Open All Hands المحلي. كيف يمكنني مساعدتك اليوم؟",
            "أهلاً بك في تطبيق BuildX! أنا هنا للإجابة على أسئلتك.",
            "يسعدني التحدث معك. هل لديك أي استفسارات محددة؟",
            "أنا وكيل ذكاء اصطناعي محلي مصمم لمساعدتك. ماذا تريد أن تعرف؟",
            "مرحباً! أنا هنا لمساعدتك في أي موضوع تريد التحدث عنه."
        )
        
        // Simple response based on user input
        val responseText = when {
            userMessage.contains("مرحبا") || userMessage.contains("أهلا") -> 
                "مرحباً بك! كيف يمكنني مساعدتك اليوم؟"
            
            userMessage.contains("كيف حالك") -> 
                "أنا بخير، شكراً للسؤال! كيف يمكنني مساعدتك؟"
            
            userMessage.contains("ماذا يمكنك أن تفعل") || userMessage.contains("ما هي قدراتك") -> 
                "يمكنني الإجابة على أسئلتك، ومساعدتك في البحث عن معلومات، وتقديم اقتراحات، والمزيد. ما الذي تحتاج مساعدة فيه؟"
            
            userMessage.contains("شكرا") -> 
                "العفو! سعيد بمساعدتك. هل هناك شيء آخر تريد مني مساعدتك فيه؟"
            
            userMessage.length < 10 -> 
                "هل يمكنك توضيح سؤالك أكثر لأتمكن من مساعدتك بشكل أفضل؟"
            
            else -> responses.random()
        }
        
        return Message("assistant", responseText)
    }
}