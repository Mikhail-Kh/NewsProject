package by.khryshchanovich.newsproject.database.adapter_vocabulary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.khryshchanovich.newsproject.R
import by.khryshchanovich.newsproject.database.entity.Vocabulary
import kotlinx.android.synthetic.main.item_vocabulary.view.*

class VocabularyAdapter(private val vocabularySet: List<Vocabulary>) :
    RecyclerView.Adapter<VocabularyAdapter.VocabularyViewHolder>() {

    class VocabularyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VocabularyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_vocabulary, parent, false)
        return VocabularyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return vocabularySet.size
    }

    override fun onBindViewHolder(holder: VocabularyViewHolder, position: Int) {
        holder.itemView.word_item_text_view.text = vocabularySet[position].word
        holder.itemView.meaning_item_text_view.text = vocabularySet[position].meaning
    }
}