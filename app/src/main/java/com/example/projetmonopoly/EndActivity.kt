package com.example.projetmonopoly

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EndActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)

        val resultTextView = findViewById<TextView>(R.id.textViewResult)
        val buttonReplay = findViewById<Button>(R.id.buttonReplay)
        val buttonQuit = findViewById<Button>(R.id.buttonQuit)

        // Récupère depuis l’intent si le joueur a gagné
        val hasWon = intent.getBooleanExtra("HAS_WON", false)
        resultTextView.text = if (hasWon) "Gagné !" else "Perdu !"

        buttonReplay.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonQuit.setOnClickListener {
            finishAffinity() // Ferme toutes les activités
        }
    }
}
