package br.com.isaiasiotti.roomwordsample.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import br.com.isaiasiotti.roomwordsample.R

class NewWordActivity : AppCompatActivity() {

  private lateinit var editWordView: EditText

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_new_word)
    editWordView = findViewById(R.id.ed_word)

    val button = findViewById<Button>(R.id.btnSalvar)
    button.setOnClickListener {
      val replyIntent = Intent()

      if (TextUtils.isEmpty(editWordView.text)) {
        setResult(Activity.RESULT_CANCELED, replyIntent)
      } else {
        val word = editWordView.text.toString()
        replyIntent.putExtra(EXTRA_REPLY, word)
      }
    }
  }

  companion object {
    const val EXTRA_REPLY = "br.com.isaiasiotti.roomwordsample.REPLY"
  }
}