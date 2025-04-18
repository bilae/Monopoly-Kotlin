package com.example.projetmonopoly

import kotlin.random.Random
import android.widget.TextView

class Joueur(val pion : Pion, val nom : String, var argent : Int, val isbot : Boolean, var toursRestantsEnPrison: Int = 0) {
    fun lancerde() : Int {
        return Random.nextInt(1,7) // génère un nbre entre 1 et 6
    }

    fun MettreAJourArgent(viewslist: List<TextView>, Joueurs: List<Joueur>) {
        //Met à jour l'affichage de l'argent de tous les joueurs
        for (i in Joueurs.indices) {
            viewslist[i].text = "${Joueurs[i].nom} : ${Joueurs[i].argent}$"
        }
    }
}