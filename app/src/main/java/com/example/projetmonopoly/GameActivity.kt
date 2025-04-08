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
        val couleurs = listOf("vert", "jaune", "rouge", "bleu")
        val pions = mutableListOf<Pion>()

        // Créer le pion du joueur
        pions.add(Pion(couleur = couleurs[0], xpos = 0, ypos = 0, prison = false))

        // Créer les pions bots
        for (i in 1..numBots) {
            val couleur = couleurs[i]
            pions.add(Pion(couleur = couleur, xpos = 0, ypos = 0, prison = false))
        }

        // Afficher uniquement le nombre de bots sélectionné
        for (i in botViews.indices) {
            botViews[i].visibility = if (i < numBots) TextView.VISIBLE else TextView.INVISIBLE
        }

        for (i in pionViews.indices) {
            pionViews[i].visibility = if (i < numBots + 1) ImageView.VISIBLE else ImageView.INVISIBLE
        }

        //ajouter le joueur
        val Joueurs = mutableListOf<Joueur>()
        Joueurs.add(Joueur(pions[0], "Joueur",1500, isbot = false))
        val vraijoueur = Joueurs[0]
        // Ajouter les bots
        for (i in 1..numBots) {
            Joueurs.add(Joueur(pions[i], "BOT$i", 1500, isbot = true))
        }

        val DiceImage : ImageView = findViewById(R.id.de1)
        val BoutonLancer : Button = findViewById(R.id.lancerDé)

        var gamestop = false //conditions à ajouter
        var tour = 0
        //début lancement du tour
        while (gamestop == false) {
            tour += 1
            for (i in 0..numBots) { //tour de chaque joueur i (0 = vraijoueur)
                //séparation tour du vrai joueur des tours des bots
                if (i==0) {
                    BoutonLancer.setOnClickListener {
                        val resultde = vraijoueur.lancerde()
                        val drawableResource = when (resultde) {
                            1 -> R.drawable.de1
                            2 -> R.drawable.de2
                            3 -> R.drawable.de3
                            4 -> R.drawable.de4
                            5 -> R.drawable.de5
                            else -> R.drawable.de6
                        }
                        DiceImage.setImageResource(drawableResource) // met à jour l'image
                        vraijoueur.goto(resultde)
                    }
                    // vraijoueur.posjoueur() -> fonction qui vérifie ou est le joueur et lance l'action d'achat/prison/loyer

                }
                //tours des bots
                else {
                    val resultde = Joueurs[i].lancerde()
                    val drawableResource = when (resultde) {
                        1 -> R.drawable.de1
                        2 -> R.drawable.de2
                        3 -> R.drawable.de3
                        4 -> R.drawable.de4
                        5 -> R.drawable.de5
                        else -> R.drawable.de6
                    }
                    DiceImage.setImageResource(drawableResource) // met à jour l'image
                    vraijoueur.goto(resultde)
                }
            }



        } //fin du while (du jeu)






    }
}
