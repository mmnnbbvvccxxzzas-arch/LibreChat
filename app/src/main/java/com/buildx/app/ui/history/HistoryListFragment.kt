package com.buildx.app.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.buildx.app.R

class HistoryListFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var recyclerHistory: RecyclerView
    private lateinit var layoutEmpty: ConstraintLayout
    private lateinit var btnStartChat: Button
    private var isFavorites = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_history_list, container, false)

        // Get arguments
        isFavorites = arguments?.getBoolean(ARG_IS_FAVORITES) ?: false

        // Initialize views
        recyclerHistory = root.findViewById(R.id.recycler_history)
        layoutEmpty = root.findViewById(R.id.layout_empty)
        btnStartChat = root.findViewById(R.id.btn_start_chat)

        // Get history items based on tab
        val historyItems = if (isFavorites) {
            historyViewModel.getFavoriteHistory()
        } else {
            historyViewModel.getAllHistory()
        }

        // Show empty state if no items
        if (historyItems.isEmpty()) {
            recyclerHistory.visibility = View.GONE
            layoutEmpty.visibility = View.VISIBLE
        } else {
            recyclerHistory.visibility = View.VISIBLE
            layoutEmpty.visibility = View.GONE

            // Set up recycler view
            val adapter = HistoryAdapter(historyItems)
            recyclerHistory.adapter = adapter
        }

        // Set up click listeners
        btnStartChat.setOnClickListener {
            findNavController().navigate(R.id.navigation_chat)
        }

        return root
    }

    companion object {
        private const val ARG_IS_FAVORITES = "is_favorites"

        fun newInstance(isFavorites: Boolean): HistoryListFragment {
            val fragment = HistoryListFragment()
            val args = Bundle()
            args.putBoolean(ARG_IS_FAVORITES, isFavorites)
            fragment.arguments = args
            return fragment
        }
    }
}

class HistoryAdapter(private val historyItems: List<HistoryItem>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(historyItems[position])
    }

    override fun getItemCount(): Int = historyItems.size

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivIcon: ImageView = itemView.findViewById(R.id.iv_chat_icon)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_chat_title)
        private val tvPreview: TextView = itemView.findViewById(R.id.tv_chat_preview)
        private val tvDate: TextView = itemView.findViewById(R.id.tv_chat_date)
        private val tvMessages: TextView = itemView.findViewById(R.id.tv_chat_messages)
        private val ivFavorite: ImageView = itemView.findViewById(R.id.iv_favorite)

        fun bind(item: HistoryItem) {
            ivIcon.setImageResource(item.iconResId)
            tvTitle.text = item.title
            tvPreview.text = item.preview
            tvDate.text = item.date
            tvMessages.text = "${item.messagesCount} رسالة"
            
            // Set favorite icon
            ivFavorite.setImageResource(
                if (item.isFavorite) R.drawable.ic_favorite
                else R.drawable.ic_favorite_border
            )

            // Set click listener for favorite icon
            ivFavorite.setOnClickListener {
                // Toggle favorite status
                item.isFavorite = !item.isFavorite
                ivFavorite.setImageResource(
                    if (item.isFavorite) R.drawable.ic_favorite
                    else R.drawable.ic_favorite_border
                )
            }

            // Set click listener for item
            itemView.setOnClickListener {
                // Navigate to chat with this history item
            }
        }
    }
}