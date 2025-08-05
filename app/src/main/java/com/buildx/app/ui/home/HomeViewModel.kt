package com.buildx.app.ui.home

import androidx.lifecycle.ViewModel
import com.buildx.app.R
import java.util.Calendar

class HomeViewModel : ViewModel() {

    fun getFeatures(): List<Feature> {
        return listOf(
            Feature(
                R.drawable.ic_feature_chat,
                "محادثة ذكية",
                "تحدث مع الذكاء الاصطناعي بشكل طبيعي"
            ),
            Feature(
                R.drawable.ic_feature_translate,
                "ترجمة فورية",
                "ترجمة النصوص بين مختلف اللغات"
            ),
            Feature(
                R.drawable.ic_feature_summarize,
                "تلخيص النصوص",
                "الحصول على ملخص للنصوص الطويلة"
            )
        )
    }

    fun getRecentChats(): List<RecentChat> {
        return listOf(
            RecentChat(
                R.drawable.ic_ai_avatar,
                "محادثة عامة",
                "مرحباً، كيف يمكنني مساعدتك اليوم؟",
                "منذ 10 دقائق"
            ),
            RecentChat(
                R.drawable.ic_ai_avatar,
                "ترجمة نص",
                "تمت ترجمة النص بنجاح إلى اللغة العربية",
                "منذ ساعة"
            ),
            RecentChat(
                R.drawable.ic_ai_avatar,
                "تلخيص مقال",
                "إليك ملخص المقال الذي طلبته...",
                "منذ يومين"
            )
        )
    }

    fun getGreeting(): String {
        val calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.HOUR_OF_DAY)) {
            in 5..11 -> "صباح الخير"
            in 12..16 -> "مساء الخير"
            in 17..20 -> "مساء الخير"
            else -> "مساء الخير"
        }
    }
}

data class Feature(
    val iconResId: Int,
    val title: String,
    val description: String
)

data class RecentChat(
    val iconResId: Int,
    val title: String,
    val preview: String,
    val time: String
)