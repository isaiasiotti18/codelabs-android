package br.com.isaiasiotti.roomwordsample.repositories

import androidx.annotation.WorkerThread
import br.com.isaiasiotti.roomwordsample.dao.WordDao
import br.com.isaiasiotti.roomwordsample.entities.Word
import kotlinx.coroutines.flow.Flow
import java.util.*

class WordRepository(private val wordDao: WordDao) {
  val allWords: Flow<List<Word>> = wordDao.getAlphabetizedWords()

  @Suppress("RedudantSuspendModifier")
  @WorkerThread
  suspend fun insert(word: Word) {
    wordDao.insert(word)
  }
}