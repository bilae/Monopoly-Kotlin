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

        // Récupère le message de fin de partie depuis l’intent
        val resultMessage = intent.getStringExtra("resultatPartie") ?: "Erreur"

        // Affiche le message de fin de partie
        resultTextView.text = resultMessage

        // Action pour rejouer
        buttonReplay.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Ferme l'activité actuelle
        }

        // Action pour quitter le jeu
        buttonQuit.setOnClickListener {
            finishAffinity() // Ferme toutes les activités
        }
    }
}
