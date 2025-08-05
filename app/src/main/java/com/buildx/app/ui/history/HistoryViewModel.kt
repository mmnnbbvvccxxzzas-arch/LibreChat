package com.buildx.app.ui.history

import androidx.lifecycle.ViewModel
import com.buildx.app.R

class HistoryViewModel : ViewModel() {

    fun getAllHistory(): List<HistoryItem> {
        return listOf(
            HistoryItem(
                R.drawable.ic_ai_avatar,
                "محادثة عامة",
                "مرحباً، كيف يمكنني مساعدتك اليوم؟",
                "10 أغسطس 2025",
                12,
                false
            ),
            HistoryItem(
                R.drawable.ic_ai_avatar,
                "ترجمة نص",
                "تمت ترجمة النص بنجاح إلى اللغة العربية",
                "8 أغسطس 2025",
                8,
                true
            ),
            HistoryItem(
                R.drawable.ic_ai_avatar,
                "تلخيص مقال",
                "إليك ملخص المقال الذي طلبته...",
                "5 أغسطس 2025",
                15,
                false
            ),
            HistoryItem(
                R.drawable.ic_ai_avatar,
                "مساعدة في البرمجة",
                "هذا هو كود حل المشكلة التي واجهتك...",
                "1 أغسطس 2025",
                20,
                true
            )
        )
    }

    fun getFavoriteHistory(): List<HistoryItem> {
        return getAllHistory().filter { it.isFavorite }
    }
}

data class HistoryItem(
    val iconResId: Int,
    val title: String,
    val preview: String,
    val date: String,
    val messagesCount: Int,
    var isFavorite: Boolean
)