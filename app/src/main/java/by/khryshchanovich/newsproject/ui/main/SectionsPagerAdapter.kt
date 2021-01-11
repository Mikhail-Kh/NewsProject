package by.khryshchanovich.newsproject.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import by.khryshchanovich.newsproject.R

val TAB_TITLES = arrayOf(
    R.string.tab_text_1_world,
    R.string.tab_text_2_business,
    R.string.tab_text_3_general,
    R.string.tab_text_4_politics,
    R.string.tab_text_5_finance,
    R.string.tab_text_6_sports,
    R.string.tab_text_7_technology,
    R.string.tab_text_8_entertainment,
    R.string.tab_text_9_health,
    R.string.tab_text_10_regional,
    R.string.tab_text_11_science,
    R.string.tab_text_12_programming,
    R.string.tab_text_13_lifestyle,
    R.string.tab_text_14_food,
    R.string.tab_text_15_game,
    R.string.tab_text_16_opinion,
    R.string.tab_text_17_academia,
    R.string.tab_text_18_entrepreneur,
    R.string.tab_text_19_startup,
    R.string.tab_text_20_society,
    R.string.tab_text_21_trading,
    R.string.tab_text_22_jobs,
    R.string.tab_text_23_mobile,
    R.string.tab_text_24_gadgets,
    R.string.tab_text_25_movie,
    R.string.tab_text_26_television,
    R.string.tab_text_27_environment,
    R.string.tab_text_28_medical,
    R.string.tab_text_29_travel,
    R.string.tab_text_30_stock,
    R.string.tab_text_31_commodity,
    R.string.tab_text_32_art,
    R.string.tab_text_33_company,
    R.string.tab_text_34_market,
    R.string.tab_text_35_national,
    R.string.tab_text_36_culture,
    R.string.tab_text_37_economy,
    R.string.tab_text_38_security,
    R.string.tab_text_39_celebrity,
    R.string.tab_text_40_education,
    R.string.tab_text_41_music,
    R.string.tab_text_42_fashion
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