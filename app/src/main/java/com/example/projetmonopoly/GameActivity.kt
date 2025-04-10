package com.example.projetmonopoly
import android.os.Bundle
import android.util.Log
import android.widget.Button
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

        // Liste des positions des cases en pourcentage du plateau (X%, Y%)
        // Objet joueur à définir plus tard dans propriétaire
        val boardPositions = listOf(
            CaseDépart(0, 0.90f, 0.90f,"Départ"),  // Départ
            Proprietes(1, 0.75f, 0.90f,"Rio",100,10,"Ce sera un objet joueur"),
            Proprietes(2, 0.60f, 0.90f,"Delhi",100,10,""),
            Proprietes(3, 0.45f, 0.90f,"Bangkok",130,15,""),
            Proprietes(4, 0.30f, 0.90f,"Gare bleu",100,35,""),
            Proprietes(5, 0.15f, 0.90f,"Caire",150,15,""),
            Proprietes(6, 0.00f, 0.90f,"Madrid",150,15,""),
            CaseChance(7, 0.00f, 0.75f,"Chance",),
            Proprietes(8, 0.00f, 0.60f,"Jakarta",170,20,""),
            Proprietes(9, 0.00f, 0.45f,"Berlin",180,20,""),
            Proprietes(10, 0.00f, 0.30f,"Moscou",200,30,""),
            Proprietes(11, 0.00f, 0.15f,"Gare orange",150,35,""),
            Proprietes(12, 0.00f, 0.00f,"Toronto",200,30,""),
            Proprietes(13, 0.15f, 0.00f,"Séoul",200,30,""),
            CasePrison(14, 0.30f, 0.00f,"Prison"),
            Proprietes(15, 0.45f, 0.00f,"Zurich",250,35,""),
            Proprietes(16, 0.60f, 0.00f,"Riyadh",250,35,""),
            Proprietes(17, 0.75f, 0.00f,"Sydney",300,40,""),
            Proprietes(18, 0.90f, 0.00f,"Gare verte",200,35,""),
            Proprietes(19, 0.90f, 0.15f,"Beijing",300,40,""),
            Proprietes(20, 0.90f, 0.30f,"Dubai",300,40,""),
            CaseChance(21, 0.90f, 0.45f,"Carte chance"),
            Proprietes(22, 0.90f, 0.60f,"Paris",350,45,""),
            Proprietes(23, 0.90f, 0.75f,"Hong Kong",350,50,""),
            Proprietes(24, 0.75f, 0.75f,"Londres",420,70,""),
            Proprietes(25, 0.60f, 0.75f,"Gare rouge",250,35,""),
            Proprietes(26, 0.45f, 0.75f,"Tokyo",420,70,""),
            Proprietes(27, 0.30f, 0.75f,"New-York",450,80,"")
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

        //début lancement du tour
        while (gamestop == false) {



            for (i in 0..numBots) { //tour de chaque joueur i (0 = vraijoueur)
                //séparation tour du vrai joueur des tours des bots
                if (i == 0) {
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
                        pions[0].goto(absx, absy, Plateau)
                        vraijoueur.argent -= 100

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
                    pions[i].goto(absx, absy, Plateau)
                    for (i in 1..numBots) {
                        Joueurs[i].argent -= 100
                    }
                }
            } //fin du tour

            // conditions d'arrêts de la partie
            var nbreBotEnNegatif = 0     // variable pour connaitre nbre de bot qui sont en dessous de 0 euros
            for (i in 1..numBots){
               if(Joueurs[i].argent < 0) nbreBotEnNegatif += 1
            }
            if (Joueurs[0].argent < 0) gamestop = true    // si vraijoueur a moins que 0 euros -> fin de partie
            else if ( nbreBotEnNegatif == numBots ) gamestop = true   // si tt les bots ont moins que 0 -> fin de partie
            else gamestop = false

        } //fin du while (du jeu)






    }
}
