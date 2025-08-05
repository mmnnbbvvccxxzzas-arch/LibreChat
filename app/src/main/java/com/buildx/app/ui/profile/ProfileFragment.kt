package com.buildx.app.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.buildx.app.LoginActivity
import com.buildx.app.R

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var recyclerSettings: RecyclerView
    private lateinit var btnEditProfile: Button
    private lateinit var btnLogout: Button
    private lateinit var ivSettings: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvChatsCount: TextView
    private lateinit var tvMessagesCount: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize views
        recyclerSettings = root.findViewById(R.id.recycler_settings)
        btnEditProfile = root.findViewById(R.id.btn_edit_profile)
        btnLogout = root.findViewById(R.id.btn_logout)
        ivSettings = root.findViewById(R.id.iv_settings)
        tvName = root.findViewById(R.id.tv_name)
        tvEmail = root.findViewById(R.id.tv_email)
        tvChatsCount = root.findViewById(R.id.tv_chats_count)
        tvMessagesCount = root.findViewById(R.id.tv_messages_count)

        // Set up settings recycler view
        val settingsAdapter = SettingsAdapter(profileViewModel.getSettings())
        recyclerSettings.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = settingsAdapter
        }

        // Set up click listeners
        btnEditProfile.setOnClickListener {
            Toast.makeText(context, "تعديل الملف الشخصي", Toast.LENGTH_SHORT).show()
        }

        btnLogout.setOnClickListener {
            // Clear user session
            val sharedPreferences = requireActivity().getSharedPreferences("BuildXPrefs", 0)
            sharedPreferences.edit().clear().apply()

            // Navigate to login screen
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        ivSettings.setOnClickListener {
            Toast.makeText(context, "الإعدادات", Toast.LENGTH_SHORT).show()
        }

        // Set user data
        val user = profileViewModel.getUserData()
        tvName.text = user.name
        tvEmail.text = user.email
        tvChatsCount.text = user.chatsCount.toString()
        tvMessagesCount.text = user.messagesCount.toString()

        return root
    }
}

class SettingsAdapter(private val settings: List<Setting>) :
    RecyclerView.Adapter<SettingsAdapter.SettingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_setting, parent, false)
        return SettingViewHolder(view)
    }

    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
        holder.bind(settings[position])
    }

    override fun getItemCount(): Int = settings.size

    class SettingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivIcon: ImageView = itemView.findViewById(R.id.iv_setting_icon)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_setting_title)

        fun bind(setting: Setting) {
            ivIcon.setImageResource(setting.iconResId)
            tvTitle.text = setting.title

            itemView.setOnClickListener {
                Toast.makeText(itemView.context, setting.title, Toast.LENGTH_SHORT).show()
            }
        }
    }
}