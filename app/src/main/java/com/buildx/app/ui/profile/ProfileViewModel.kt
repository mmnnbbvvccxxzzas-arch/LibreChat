package com.buildx.app.ui.profile

import androidx.lifecycle.ViewModel
import com.buildx.app.R

class ProfileViewModel : ViewModel() {

    fun getSettings(): List<Setting> {
        return listOf(
            Setting(
                R.drawable.ic_language,
                "اللغة"
            ),
            Setting(
                R.drawable.ic_theme,
                "المظهر"
            ),
            Setting(
                R.drawable.ic_notifications,
                "الإشعارات"
            ),
            Setting(
                R.drawable.ic_privacy,
                "الخصوصية والأمان"
            )
        )
    }

    fun getUserData(): User {
        // In a real app, this would come from a repository or API
        return User(
            "محمد أحمد",
            "mohammed@example.com",
            24,
            128
        )
    }
}

data class Setting(
    val iconResId: Int,
    val title: String
)

data class User(
    val name: String,
    val email: String,
    val chatsCount: Int,
    val messagesCount: Int
)