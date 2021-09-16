package br.com.isaiasiotti.roomwordsample.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.isaiasiotti.roomwordsample.R
import br.com.isaiasiotti.roomwordsample.adapter.WordListAdapter
import br.com.isaiasiotti.roomwordsample.entities.Word
import br.com.isaiasiotti.roomwordsample.viewmodel.WordViewModel
import br.com.isaiasiotti.roomwordsample.viewmodel.WordViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MainActivity : AppCompatActivity() {

  private val newWordActivityRequestCode = 1

  private val wordViewModel: WordViewModel by viewModels {
    WordViewModelFactory((application as WordsApplication).repository)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val recyclerView = findViewById<RecyclerView>(R.id.rv_word)
    val adapter = WordListAdapter()
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(this)

    val fab = findViewById<FloatingActionButton>(R.id.fab_word)
    fab.setOnClickListener {
      val intent = Intent(this@MainActivity, NewWordActivity::class.java)
      startActivityForResult(intent, newWordActivityRequestCode)
    }

    wordViewModel.allWords.observe(this, Observer { words ->
      words?.let { adapter.submitList(it) }
    })
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
      data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let {
        val word = Word(word = it)
        wordViewModel.insert(word)
      }
    } else {
      Toast.makeText(
        applicationContext,
        R.string.empty_not_saved,
        Toast.LENGTH_SHORT
      ).show()
    }
  }
}