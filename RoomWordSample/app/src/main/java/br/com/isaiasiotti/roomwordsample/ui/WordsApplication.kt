package br.com.isaiasiotti.roomwordsample.ui

import android.app.Application
import br.com.isaiasiotti.roomwordsample.database.WordRoomDatabase
import br.com.isaiasiotti.roomwordsample.repositories.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordsApplication: Application() {
  val applicationScope = CoroutineScope(SupervisorJob())

  val database by lazy { WordRoomDatabase.getDatabase(this, applicationScope) }
  val repository by lazy { WordRepository(database.wordDao()) }
}