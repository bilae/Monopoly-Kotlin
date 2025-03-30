package com.example.projetmonopoly
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val numBots = intent.getIntExtra("NUM_BOTS", 1)

        val botViews = listOf(
            findViewById<TextView>(R.id.bot1),
            findViewById<TextView>(R.id.bot2),
            findViewById<TextView>(R.id.bot3)
        )

        val Plateau = findViewById<ImageView>(R.id.boardImage)

        val pionViews = listOf(
            findViewById<ImageView>(R.id.pion_vert),
            findViewById<ImageView>(R.id.pion_jaune),
            findViewById<ImageView>(R.id.pion_rouge),
            findViewById<ImageView>(R.id.pion_bleu)
        )


        // Afficher uniquement le nombre de bots sélectionné
        for (i in botViews.indices) {
            botViews[i].visibility = if (i < numBots) TextView.VISIBLE else TextView.INVISIBLE
        }

        for (i in pionViews.indices) {
            pionViews[i].visibility = if (i < numBots + 1) ImageView.VISIBLE else ImageView.INVISIBLE
        }

    }
}
