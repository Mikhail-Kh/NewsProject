package by.khryshchanovich.newsproject

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import by.khryshchanovich.newsproject.database.ShowVocabularyActivity
import by.khryshchanovich.newsproject.ui.main.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)

        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        val seeVocabularyButton = findViewById<ImageView>(R.id.see_vocabulary_image)
        seeVocabularyButton.setOnClickListener {
            val intent = Intent(this, ShowVocabularyActivity::class.java)
            startActivity(intent)
        }
    }
}