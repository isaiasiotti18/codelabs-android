package br.com.isaiasiotti.roomwordsample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.isaiasiotti.roomwordsample.R

class WordViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
  private val wordItemView: TextView = itemView.findViewById(R.id.tv_word_item)

  fun bind(text: String?) {
    wordItemView.text = text
  }

  companion object {
    fun create(parent: ViewGroup): WordViewHolder {
      val view: View = LayoutInflater.from(parent.context)
        .inflate(R.layout.recyclerview_item, parent, false)
      return WordViewHolder(view)
    }
  }
}