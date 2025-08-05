package com.buildx.app.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.buildx.app.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var ivSearch: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_history, container, false)

        // Initialize views
        viewPager = root.findViewById(R.id.view_pager)
        tabLayout = root.findViewById(R.id.tab_layout)
        ivSearch = root.findViewById(R.id.iv_search)

        // Set up ViewPager with tabs
        val pagerAdapter = HistoryPagerAdapter(this)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.all)
                1 -> getString(R.string.favorites)
                else -> null
            }
        }.attach()

        // Set up click listeners
        ivSearch.setOnClickListener {
            Toast.makeText(context, "البحث", Toast.LENGTH_SHORT).show()
        }

        return root
    }
}

class HistoryPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return HistoryListFragment.newInstance(position == 1)
    }
}