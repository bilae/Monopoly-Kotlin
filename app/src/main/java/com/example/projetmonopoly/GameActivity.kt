package com.example.projetmonopoly
import android.os.Bundle
import android.util.Log
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

        // Liste des positions des cases en pourcentage du plateau (X%, Y%)
        val boardPositions = listOf(
            Case(0, 0.90f, 0.90f,"Départ"),  // Départ
            Case(1, 0.75f, 0.90f,"Rio"),
            Case(2, 0.60f, 0.90f,"Delhi"),
            Case(3, 0.45f, 0.90f,"Bangkok"),
            Case(4, 0.30f, 0.90f,"Gare bleu"),
            Case(5, 0.15f, 0.90f,"Caire"),
            Case(6, 0.00f, 0.90f,"Madrid"),
            Case(7, 0.00f, 0.75f,"Chance"),
            Case(8, 0.00f, 0.60f,"Jakarta"),
            Case(9, 0.00f, 0.45f,"Berlin"),
            Case(10, 0.00f, 0.30f,"Moscou"),
            Case(11, 0.00f, 0.15f,"Gare orange"),
            Case(12, 0.00f, 0.00f,"Toronto"),
            Case(13, 0.15f, 0.00f,"Séoul"),
            Case(14, 0.30f, 0.00f,"Prison"),
            Case(15, 0.45f, 0.00f,"Zurich"),
            Case(16, 0.60f, 0.00f,"Riyadh"),
            Case(17, 0.75f, 0.00f,"Sydney"),
            Case(18, 0.90f, 0.00f,"Gare verte"),
            Case(19, 0.90f, 0.15f,"Beijing"),
            Case(20, 0.90f, 0.30f,"Dubai"),
            Case(21, 0.90f, 0.45f,"Carte chance"),
            Case(22, 0.90f, 0.60f,"Paris"),
            Case(23, 0.90f, 0.75f,"Hong Kong"),
            Case(24, 0.75f, 0.75f,"Londres"),
            Case(25, 0.60f, 0.75f,"Gare rouge"),
            Case(26, 0.45f, 0.75f,"Tokyo"),
            Case(27, 0.30f, 0.75f,"New-York")
        )

        val pionViews = listOf(
            findViewById<ImageView>(R.id.pion_vert),
            findViewById<ImageView>(R.id.pion_jaune),
            findViewById<ImageView>(R.id.pion_rouge),
            findViewById<ImageView>(R.id.pion_bleu)
        )
        val couleurs = listOf("vert", "jaune", "rouge", "bleu")
        val pions = mutableListOf<Pion>()

        // Créer le pion du joueur
        pions.add(
            Pion(
                couleur = couleurs[0],
                xpos = 0f,
                ypos = 0f,
                prison = false,
                image = pionViews[0],
                case = 0
            )
        )

        // Créer les pions bots
        for (i in 1..numBots) {
            val couleur = couleurs[i]
            pions.add(
                Pion(
                    couleur = couleur,
                    xpos = 0f,
                    ypos = 0f,
                    prison = false,
                    image = pionViews[i],
                    case =0
                )
            )
        }

        // Afficher uniquement le nombre de bots sélectionné
        for (i in botViews.indices) {
            botViews[i].visibility = if (i < numBots) TextView.VISIBLE else TextView.INVISIBLE
        }

        for (i in pionViews.indices) {
            pionViews[i].visibility =
                if (i < numBots + 1) ImageView.VISIBLE else ImageView.INVISIBLE
        }

        //ajouter le joueur
        val Joueurs = mutableListOf<Joueur>()
        Joueurs.add(Joueur(pions[0], "Joueur", 1500, isbot = false))
        val vraijoueur = Joueurs[0]
        // Ajouter les bots
        for (i in 1..numBots) {
            Joueurs.add(Joueur(pions[i], "BOT$i", 1500, isbot = true))
        }

        var boardX = 0f
        var boardY = 0f
        var boardWidth = 0f
        var boardHeight = 0f


        Plateau.post {
            boardX = Plateau.x
            boardY = Plateau.y
            boardWidth = Plateau.width.toFloat()
            boardHeight = Plateau.height.toFloat()
            Log.d(
                "Pion",
                "Plateau (image.png): x=$boardX, y=$boardY, width=$boardWidth, height=$boardHeight"
            )
        }



        // Convertir une position relative en pixels réels
        fun getCasePosition(index: Int): Pair<Float, Float> {
            if (index !in boardPositions.indices) {
                Log.e("Pion", "Index de case invalide: $index")
                return Pair(boardX, boardY) // Retourne la position actuelle pour éviter les erreurs (fait par chatgpt)
            }

            val (relX, relY) = boardPositions[index]
            val absX = boardX + (relX * boardWidth)
            val absY = boardY + (relY * boardHeight)

            return Pair(absX, absY)
            }

        //images dé
        val DiceImage : ImageView = findViewById(R.id.de1)
        val BoutonLancer : Button = findViewById(R.id.lancerDé)

        var gamestop = false //conditions à ajouter
        var tour = 0
        //début lancement du tour
        while (gamestop == false) {
            tour += 1
            if (tour == 1){ //temporaire pour les tests
                gamestop = true
            }

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
                        pions[0].case += resultde
                        val (absx, absy) = getCasePosition(pions[0].case)
                        pions[0].goto(absx,absy,Plateau)

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
                    pions[i].case += resultde
                    val (absx, absy) = getCasePosition(pions[i].case)
                    pions[i].goto(absx,absy,Plateau)
                }
            } //fin du tour



        } //fin du while (du jeu)






    }
}
