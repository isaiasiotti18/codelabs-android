package br.com.isaiasiotti.roomwordsample.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import br.com.isaiasiotti.roomwordsample.entities.Word

class WordListAdapter: ListAdapter<Word, WordViewHolder>(WordsComparator()) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
    return WordViewHolder.create(parent)
  }

  override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
    val current = getItem(position)
    holder.bind(current.word)
  }

  class WordsComparator: DiffUtil.ItemCallback<Word>() {
    override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
      return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
      return oldItem.word == newItem.word
    }
  }
}