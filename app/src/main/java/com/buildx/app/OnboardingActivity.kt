package com.buildx.app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.buildx.app.ui.guest.GuestModeActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var buttonNext: Button
    private lateinit var textSkip: TextView
    private lateinit var pageIndicator: TabLayout
    
    private val onboardingItems = listOf(
        OnboardingItem(
            R.drawable.onboarding_1,
            R.string.onboarding_title_1,
            R.string.onboarding_desc_1
        ),
        OnboardingItem(
            R.drawable.onboarding_2,
            R.string.onboarding_title_2,
            R.string.onboarding_desc_2
        ),
        OnboardingItem(
            R.drawable.onboarding_3,
            R.string.onboarding_title_3,
            R.string.onboarding_desc_3
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        viewPager = findViewById(R.id.viewPager)
        buttonNext = findViewById(R.id.buttonNext)
        textSkip = findViewById(R.id.textSkip)
        pageIndicator = findViewById(R.id.pageIndicator)

        // Set up the adapter
        val adapter = OnboardingAdapter(onboardingItems)
        viewPager.adapter = adapter

        // Set up the tab layout with viewpager
        TabLayoutMediator(pageIndicator, viewPager) { _, _ -> }.attach()

        // Handle next button click
        buttonNext.setOnClickListener {
            if (viewPager.currentItem < onboardingItems.size - 1) {
                viewPager.currentItem = viewPager.currentItem + 1
            } else {
                finishOnboarding()
            }
        }

        // Handle skip text click
        textSkip.setOnClickListener {
            finishOnboarding()
        }

        // Update button text based on page
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == onboardingItems.size - 1) {
                    buttonNext.text = getString(R.string.get_started)
                } else {
                    buttonNext.text = getString(R.string.next)
                }
            }
        })
    }

    private fun finishOnboarding() {
        // Save that user has seen onboarding
        val sharedPreferences = getSharedPreferences("BuildXPrefs", MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("hasSeenOnboarding", true).apply()

        // Navigate to guest mode screen
        val intent = Intent(this, GuestModeActivity::class.java)
        startActivity(intent)
        finish()
    }
}

data class OnboardingItem(
    val imageResId: Int,
    val titleResId: Int,
    val descriptionResId: Int
)

class OnboardingAdapter(private val items: List<OnboardingItem>) :
    RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        return OnboardingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_onboarding,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class OnboardingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageOnboarding: ImageView = itemView.findViewById(R.id.imageOnboarding)
        private val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        private val textDescription: TextView = itemView.findViewById(R.id.textDescription)

        fun bind(item: OnboardingItem) {
            imageOnboarding.setImageResource(item.imageResId)
            textTitle.setText(item.titleResId)
            textDescription.setText(item.descriptionResId)
        }
    }
}