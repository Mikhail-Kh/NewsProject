package by.khryshchanovich.newsproject.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import by.khryshchanovich.newsproject.R

val TAB_TITLES = arrayOf(
    R.string.tab_text_1_world,
    R.string.tab_text_2_business,
    R.string.tab_text_3_politics,
    R.string.tab_text_4_finance,
    R.string.tab_text_5_sports,
    R.string.tab_text_6_technology,
    R.string.tab_text_7_entertainment,
    R.string.tab_text_8_health,
    R.string.tab_text_9_science,
    R.string.tab_text_10_programming,
    R.string.tab_text_11_lifestyle,
    R.string.tab_text_12_food,
    R.string.tab_text_13_game,
    R.string.tab_text_14_entrepreneur,
    R.string.tab_text_15_startup,
    R.string.tab_text_16_society,
    R.string.tab_text_17_trading,
    R.string.tab_text_18_gadgets,
    R.string.tab_text_19_movie,
    R.string.tab_text_20_environment,
    R.string.tab_text_21_medical,
    R.string.tab_text_22_travel,
    R.string.tab_text_23_art,
    R.string.tab_text_24_company,
    R.string.tab_text_25_culture,
    R.string.tab_text_26_economy,
    R.string.tab_text_27_security,
    R.string.tab_text_28_celebrity,
    R.string.tab_text_29_education,
    R.string.tab_text_30_music,
    R.string.tab_text_31_fashion
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return PlaceholderFragment.newInstance(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }
}