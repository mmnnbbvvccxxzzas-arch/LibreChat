package com.buildx.app.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Headers

/**
 * API Service for communicating with the Open All Hands local agent
 */
interface OpenHandsApiService {
    
    @Headers("Content-Type: application/json")
    @POST("v1/chat/completions")
    fun sendMessage(@Body request: ChatRequest): Call<ChatResponse>
    
    @Headers("Content-Type: application/json")
    @POST("v1/models")
    fun getModels(): Call<ModelsResponse>
}

/**
 * Chat request model
 */
data class ChatRequest(
    val model: String = "local-model",
    val messages: List<Message>,
    val temperature: Float = 0.7f,
    val max_tokens: Int = 1000,
    val stream: Boolean = false
)

/**
 * Message model
 */
data class Message(
    val role: String, // "user", "assistant", "system"
    val content: String
)

/**
 * Chat response model
 */
data class ChatResponse(
    val id: String,
    val object: String,
    val created: Long,
    val model: String,
    val choices: List<Choice>,
    val usage: Usage
)

/**
 * Choice model
 */
data class Choice(
    val index: Int,
    val message: Message,
    val finish_reason: String
)

/**
 * Usage model
 */
data class Usage(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int
)

/**
 * Models response
 */
data class ModelsResponse(
    val data: List<Model>
)

/**
 * Model information
 */
data class Model(
    val id: String,
    val object: String,
    val created: Long,
    val owned_by: String
)