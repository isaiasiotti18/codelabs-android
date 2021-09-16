package br.com.isaiasiotti.roomwordsample.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "words")
data class Word(
  @ColumnInfo(name = "word")
  val word: String
)
