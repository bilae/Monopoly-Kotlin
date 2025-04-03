package com.example.projetmonopoly
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import kotlin.random.Random

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
        val DiceImage : ImageView = findViewById(R.id.de1)
        val BoutonLancer : Button = findViewById(R.id.lancerDé)
        BoutonLancer.setOnClickListener{
            val randomNumber = Random.nextInt(1,7) // génère un nbre entre 1 et 6
            val drawableResource = when(randomNumber){
                1 -> R.drawable.de1
                2 -> R.drawable.de2
                3 -> R.drawable.de3
                4 -> R.drawable.de4
                5 -> R.drawable.de5
                else -> R.drawable.de6

            }
            DiceImage.setImageResource(drawableResource) // met à jour l'image
        }



    }
}
