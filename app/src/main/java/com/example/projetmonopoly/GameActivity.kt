package com.example.projetmonopoly

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import GameObserver
import GameObservable
import android.os.Handler
import android.os.Looper
import android.view.View

class GameActivity : AppCompatActivity(), GameObservable {
    private var currentPlayerIndex = 0
    private lateinit var Joueurs: MutableList<Joueur>
    private lateinit var boardPositions: List<Case>
    private lateinit var pions: MutableList<Pion>
    private var gamestop = false
    private lateinit var Plateau:ImageView
    private lateinit var DiceImage: ImageView
    private lateinit var BoutonLancer: Button

    private val observers = mutableListOf<GameObserver>()

    override fun addObserver(observer: GameObserver) {
        observers.add(observer)
    }

    override fun removeObserver(observer: GameObserver) {
        observers.remove(observer)
    }

    override fun notifyObservers(event: String) {
        for (observer in observers) {
            observer.onGameEvent(event)
        }
    }
    //Pour afficher les messages dans le jeu
    fun afficherMessage(context: Context, titre: String, message: String) {
        AlertDialog.Builder(context)
            .setTitle(titre)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val numBots = intent.getIntExtra("NUM_BOTS", 1) //nombre de bots
        val botViews = mutableListOf( //Texte affichage de l'argent des bots
            findViewById<TextView>(R.id.bot1),
            findViewById<TextView>(R.id.bot2),
            findViewById<TextView>(R.id.bot3)
        )

        Plateau = findViewById<ImageView>(R.id.boardImage)
        DiceImage = findViewById(R.id.de1)
        BoutonLancer = findViewById(R.id.lancerDé)


        // Liste des positions des cases en pourcentage du plateau (X%, Y%)
        // Objet joueur à définir plus tard dans propriétaire
        boardPositions = Casesingleton.boardPositions

        //Liste des images des pions
        val pionViews = listOf(
            findViewById<ImageView>(R.id.pion_vert),
            findViewById<ImageView>(R.id.pion_jaune),
            findViewById<ImageView>(R.id.pion_rouge),
            findViewById<ImageView>(R.id.pion_bleu)
        )
        val couleurs = listOf("vert", "jaune", "rouge", "bleu")
        pions = mutableListOf()

        // Initialisation des pions
        Plateau.post {
            initialiserPions(numBots, pionViews, couleurs)

            // Initialisation des joueurs
            Joueurs = mutableListOf()
            Joueurs.add(Joueur(pions[0], "Joueur", 1500, isbot = false))

            for (i in 1..numBots) {
                Joueurs.add(Joueur(pions[i], "BOT$i", 1500, isbot = true))
            }
            addObserver(BotMortObserver(Joueurs, botViews))
            // Afficher uniquement les pions et Textbots en jeu
            for (i in pionViews.indices) {
                pionViews[i].visibility =
                    if (i < numBots + 1) ImageView.VISIBLE else ImageView.INVISIBLE
            }

            for (i in botViews.indices) {
                botViews[i].visibility = if (i < numBots) TextView.VISIBLE else TextView.INVISIBLE
            }

            // Lancer automatiquement le tour si c'est un bot
            if (currentPlayerIndex > 0) {
                jouerTourBot()
            }
        }

        // Gestion du clic sur le bouton de lancer de dé
        if (currentPlayerIndex == 0) { // Tour du joueur humain
            BoutonLancer.isClickable = true
            BoutonLancer.setOnClickListener {
                BoutonLancer.isClickable = false
                jouerTourHumain()
            }
        }

        // Commencer le jeu avec le premier bot si c'est son tour
        if (currentPlayerIndex > 0) {
            BoutonLancer.isClickable = false
            jouerTourBot()
        }
    }
    private fun initialiserPions(numBots: Int, pionViews: List<ImageView>, couleurs: List<String>) {
        val boardWidth = Plateau.width.toFloat()
        val boardHeight = Plateau.height.toFloat()
        val boardX = Plateau.x
        val boardY = Plateau.y

        // Initialisation de la liste des pions
        pions = mutableListOf()

        // Coordonnées case départ
        val (startX, startY) = getCasePosition(0, boardX, boardY, boardWidth, boardHeight)
        //Initialisation du pion joueur sur la case départ
        pions.add(
            Pion(
                couleur = couleurs[0],
                xpos = startX,
                ypos = startY,
                prison = false,
                image = pionViews[0],
                case = 0
            )
        )
        pionViews[0].x = startX
        pionViews[0].y = startY

        // Pions bots
        for (i in 1..numBots) {
            val (botX, botY) = getCasePosition(0, boardX, boardY, boardWidth, boardHeight)
            pions.add(
                Pion(
                    couleur = couleurs[i],
                    xpos = botX,
                    ypos = botY,
                    prison = false,
                    image = pionViews[i],
                    case = 0
                )
            )
            pionViews[i].x = botX
            pionViews[i].y = botY
        }
    }

    private fun jouerTourHumain() {
        val joueur = Joueurs[currentPlayerIndex] //Définit celui qui joue
        //vérifie l'état du joueur (prison ou non)
        if (joueur.pion.prison) {
            if (joueur.toursRestantsEnPrison > 0) {
                afficherMessage(this, "Prison", "Vous êtes en prison. Il vous reste ${joueur.toursRestantsEnPrison} tour(s).")
                joueur.toursRestantsEnPrison--
                passerAuJoueurSuivant()
                return
            } else {
                joueur.pion.prison = false
                afficherMessage(this, "Prison", "Vous êtes libre ! Vous pouvez jouer.")
            }
        }

        val resultde = joueur.lancerde()
        val drawableResource = when (resultde) {
            1 -> R.drawable.de1
            2 -> R.drawable.de2
            3 -> R.drawable.de3
            4 -> R.drawable.de4
            5 -> R.drawable.de5
            else -> R.drawable.de6
        }
        findViewById<ImageView>(R.id.de1).setImageResource(drawableResource) //met à jour l'image du dé

        val pion = joueur.pion
        pion.case = (pion.case + resultde) % boardPositions.size //ajout du nombre de case
        Plateau.post {
            val (absx, absy) = useIndex(pion.case)
            pion.goto(absx, absy, findViewById(R.id.boardImage)) //déplacement du pion
            joueurArriveSurCase(this, joueur, pion)
        }
    }

    private fun jouerTourBot() {
        val bot = Joueurs[currentPlayerIndex]
        //Vérifie que le bot peut jouer
        if(bot.argent <=0) {
            this.notifyObservers("bot_mort_$currentPlayerIndex")
            passerAuJoueurSuivant()
            return
        }
        if (bot.pion.prison) {
            if (bot.toursRestantsEnPrison > 0) {
                bot.toursRestantsEnPrison--
                passerAuJoueurSuivant()
                return
            } else {
                bot.pion.prison = false
            }
        }

        val resultde = bot.lancerde()
        val drawableResource = when (resultde) {
            1 -> R.drawable.de1
            2 -> R.drawable.de2
            3 -> R.drawable.de3
            4 -> R.drawable.de4
            5 -> R.drawable.de5
            else -> R.drawable.de6
        }
        findViewById<ImageView>(R.id.de1).setImageResource(drawableResource)

        val pion = bot.pion
        pion.case = (pion.case + resultde) % boardPositions.size
        Plateau.post {
            val (absx, absy) = useIndex(pion.case)
            pion.goto(absx, absy, findViewById(R.id.boardImage))
            joueurArriveSurCase(this, bot, pion)
        }
    }

    private fun passerAuJoueurSuivant() {
        currentPlayerIndex = (currentPlayerIndex + 1) % Joueurs.size
        verifierFinDePartie()

        if (gamestop) return

        if (currentPlayerIndex == 0) {
            // Tour du joueur humain
            BoutonLancer.isClickable = true
            BoutonLancer.visibility = View.VISIBLE
        } else {
            // Tour d'un bot
            BoutonLancer.isClickable = false
            BoutonLancer.visibility = View.INVISIBLE
            Handler(Looper.getMainLooper()).postDelayed({
                jouerTourBot()
            }, 1500)
        }
    }

    private fun verifierFinDePartie() {
        var nbreBotEnNegatif = 0

        // Comptabiliser les bots en négatif
        for (i in 1 .. Joueurs.size - 1) {
            if (Joueurs[i].argent <= 0) {
                nbreBotEnNegatif++
            }
        }

        // Si tous les bots sont en négatif, c'est la victoire du joueur humain
        if (nbreBotEnNegatif == Joueurs.size - 1) {
            gamestop = true
            val intent = Intent(this, EndActivity::class.java)
            intent.putExtra("resultatPartie", "Vous avez gagné !")
            startActivity(intent)
            finish() // Fermeture de l'activité actuelle
        }

        // Si l'argent du joueur humain (Joueur[0]) est inférieur ou égal à 0, il perd
        else if (Joueurs[0].argent <= 0) {
            gamestop = true
            val intent = Intent(this, EndActivity::class.java)
            intent.putExtra("resultatPartie", "Vous avez perdu !")
            startActivity(intent)
            finish() // Fermeture de l'activité actuelle
        }
    }

    //délai entre les tours des joueurs
    private fun attendrePuisPasserAuJoueurSuivant(delayMillis: Long = 1500L) {
        Handler(Looper.getMainLooper()).postDelayed({
            passerAuJoueurSuivant()
        }, delayMillis)
    }

    //Utiliser l'index de la case
    private fun useIndex(index: Int): Pair<Float, Float> {
        val Plateau = findViewById<ImageView>(R.id.boardImage)
        return getCasePosition(index, Plateau.x, Plateau.y, Plateau.width.toFloat(), Plateau.height.toFloat())
    }

    //Trouver la position de la case
    private fun getCasePosition(index: Int, boardX: Float, boardY: Float, boardWidth: Float, boardHeight: Float): Pair<Float, Float> {
        if (index !in boardPositions.indices) {
            Log.e("Pion", "Index de case invalide: $index")
            return Pair(boardX, boardY)
        }
        val (relX, relY) = boardPositions[index]
        val absX = boardX + (relX * boardWidth)
        val absY = boardY + (relY * boardHeight)
        return Pair(absX, absY)
    }

    //Définir ce que doit faire le joueur sur la case où il arrive
    fun joueurArriveSurCase(context: Context, Joueur: Joueur, pion: Pion) {
        val case = boardPositions[pion.case]
        val Views = listOf(
            findViewById<TextView>(R.id.Joueur),
            findViewById<TextView>(R.id.bot1),
            findViewById<TextView>(R.id.bot2),
            findViewById<TextView>(R.id.bot3)
        )

        when (case) {
            is CaseDépart -> {
                if (!Joueur.isbot) {
                    afficherMessage(
                        context,
                        "Case Départ",
                        "Vous êtes sur la case ${case.NAMECASE}. Vous recevez 100$ !"
                    )
                    val ArriveSurCaseDépart = CaseDépartCommand(Joueur)
                    ArriveSurCaseDépart.execute()
                    Joueur.MettreAJourArgent(Views, Joueurs)
                    attendrePuisPasserAuJoueurSuivant()
                } else {
                    val BotArriveSurCaseDépart = CaseDépartCommand(Joueur)
                    BotArriveSurCaseDépart.execute()
                    Joueur.MettreAJourArgent(Views, Joueurs)
                    attendrePuisPasserAuJoueurSuivant()
                }
            }

            is CasePrison -> {
                if (!Joueur.isbot) {
                    afficherMessage(
                        context,
                        "Prison",
                        "Vous êtes sur la case ${case.NAMECASE}. Allez directement en prison !"
                    )
                    Joueur.pion.prison = true
                    Joueur.toursRestantsEnPrison = 2
                    attendrePuisPasserAuJoueurSuivant()
                } else {
                    Joueur.pion.prison = true
                    Joueur.toursRestantsEnPrison = 2
                    attendrePuisPasserAuJoueurSuivant()
                }
            }

            is Proprietes -> {
                if (case.proprietaire != null && case.proprietaire != Joueur) {
                    if (!Joueur.isbot) { //Si joueur humain, afficher message de transaction

                        afficherMessage(
                            context,
                            "Loyer",
                            "${case.NAMECASE} appartient déjà à ${case.proprietaire!!.nom}. Vous devez payer un loyer de ${case.location}$."
                        )
                        if (Joueur.argent >= case.location) {
                            val commandeLocation = LouerProprieteCommand(Joueur, case)
                            commandeLocation.execute()
                            Joueur.MettreAJourArgent(Views, Joueurs)
                            afficherMessage(
                                context,
                                "Paiement effectué",
                                "${Joueur.nom} a payé ${case.location}$ à ${case.proprietaire!!.nom}."
                            )
                        } else {
                            afficherMessage(
                                context,
                                "Fonds insuffisants",
                                "Vous n'avez pas assez d'argent pour payer le loyer."
                            )
                            Joueur.argent = 0
                            Joueur.MettreAJourArgent(Views, Joueurs)
                        }
                        attendrePuisPasserAuJoueurSuivant()

                    } else {
                        if (Joueur.argent >= case.location) {
                            val commandeLocation = LouerProprieteCommand(Joueur, case)
                            commandeLocation.execute()
                            Joueur.MettreAJourArgent(Views, Joueurs)
                        } else {
                            Joueur.argent = 0
                            Joueur.MettreAJourArgent(Views, Joueurs)
                        }
                        attendrePuisPasserAuJoueurSuivant()
                    }
                } else if (case.proprietaire == null) {
                    if (!Joueur.isbot) { //Si joueur humain, proposer l'achat de la proprieté

                        AlertDialog.Builder(context)
                            .setTitle("Acheter ${case.NAMECASE} ?")
                            .setMessage("Voulez-vous acheter ${case.NAMECASE} pour ${case.prix}$ ?")
                            .setPositiveButton("Oui") { _, _ ->
                                if (Joueur.argent >= case.prix) {
                                    val commandeAchat = AchatProprieteCommand(Joueur, case)
                                    commandeAchat.execute()
                                    afficherMessage(
                                        context,
                                        "Achat réussi",
                                        "${Joueur.nom} a acheté ${case.NAMECASE} pour ${case.prix}$. Argent restant : ${Joueur.argent}$."
                                    )
                                } else {
                                    afficherMessage(
                                        context,
                                        "Achat refusé",
                                        "Vous n'avez pas assez d'argent pour acheter ${case.NAMECASE}."
                                    )
                                }
                                Joueur.MettreAJourArgent(Views, Joueurs)
                                attendrePuisPasserAuJoueurSuivant()
                            }
                            .setNegativeButton("Non") { _, _ ->
                                afficherMessage(
                                    context,
                                    "Achat annulé",
                                    "${Joueur.nom} a choisi de ne pas acheter ${case.NAMECASE}."
                                )
                                attendrePuisPasserAuJoueurSuivant()
                            }
                            .show()
                    } else { //Sinon, le bot choisi aléatoirement
                        if (Joueur.argent >= case.prix && (0..1).random() == 1) {
                            val commandeAchatBot = AchatProprieteCommand(Joueur, case)
                            commandeAchatBot.execute()
                            Joueur.MettreAJourArgent(Views, Joueurs)
                        }
                        passerAuJoueurSuivant()
                    }
                } else {
                    passerAuJoueurSuivant()
                }
            }

            is CaseChance -> {
                if (case is CaseChance) {
                    val commandCarteChance = CaseChanceCommand(Joueur, case)
                    commandCarteChance.execute()
                }

                afficherMessage(
                    context,
                    "Carte chance",
                    "${Joueur.nom} a maintenant ${Joueur.argent}$"
                )
                Joueur.MettreAJourArgent(Views, Joueurs)
                attendrePuisPasserAuJoueurSuivant()
            }


            else -> {
                passerAuJoueurSuivant()
            }
        }
    }
}