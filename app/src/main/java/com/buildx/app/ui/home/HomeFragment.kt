package com.buildx.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.buildx.app.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerFeatures: RecyclerView
    private lateinit var recyclerRecent: RecyclerView
    private lateinit var btnStartChat: Button
    private lateinit var ivNotifications: ImageView
    private lateinit var tvGreeting: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize views
        recyclerFeatures = root.findViewById(R.id.recycler_features)
        recyclerRecent = root.findViewById(R.id.recycler_recent)
        btnStartChat = root.findViewById(R.id.btn_start_chat)
        ivNotifications = root.findViewById(R.id.iv_notifications)
        tvGreeting = root.findViewById(R.id.tv_greeting)

        // Set up features recycler view
        val featuresAdapter = FeaturesAdapter(homeViewModel.getFeatures())
        recyclerFeatures.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = featuresAdapter
        }

        // Set up recent chats recycler view
        val recentChatsAdapter = RecentChatsAdapter(homeViewModel.getRecentChats())
        recyclerRecent.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recentChatsAdapter
        }

        // Set up click listeners
        btnStartChat.setOnClickListener {
            findNavController().navigate(R.id.navigation_chat)
        }

        ivNotifications.setOnClickListener {
            // Open notifications
        }

        // Set greeting based on time of day
        tvGreeting.text = homeViewModel.getGreeting()

        return root
    }
}

class FeaturesAdapter(private val features: List<Feature>) :
    RecyclerView.Adapter<FeaturesAdapter.FeatureViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_feature, parent, false)
        return FeatureViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
        holder.bind(features[position])
    }

    override fun getItemCount(): Int = features.size

    class FeatureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivIcon: ImageView = itemView.findViewById(R.id.iv_feature_icon)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_feature_title)
        private val tvDesc: TextView = itemView.findViewById(R.id.tv_feature_desc)

        fun bind(feature: Feature) {
            ivIcon.setImageResource(feature.iconResId)
            tvTitle.text = feature.title
            tvDesc.text = feature.description
        }
    }
}

class RecentChatsAdapter(private val chats: List<RecentChat>) :
    RecyclerView.Adapter<RecentChatsAdapter.RecentChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recent_chat, parent, false)
        return RecentChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecentChatViewHolder, position: Int) {
        holder.bind(chats[position])
    }

    override fun getItemCount(): Int = chats.size

    class RecentChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivIcon: ImageView = itemView.findViewById(R.id.iv_chat_icon)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_chat_title)
        private val tvPreview: TextView = itemView.findViewById(R.id.tv_chat_preview)
        private val tvTime: TextView = itemView.findViewById(R.id.tv_chat_time)

        fun bind(chat: RecentChat) {
            ivIcon.setImageResource(chat.iconResId)
            tvTitle.text = chat.title
            tvPreview.text = chat.preview
            tvTime.text = chat.time
        }
    }
}