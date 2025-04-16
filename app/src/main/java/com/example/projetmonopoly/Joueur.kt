package com.example.projetmonopoly

import kotlin.random.Random
import android.widget.TextView



class Joueur(val pion : Pion, val nom : String, var argent : Int, val isbot : Boolean, var toursRestantsEnPrison: Int = 0) {
    fun lancerde() : Int {
        return Random.nextInt(1,7) // génère un nbre entre 1 et 6
    }

    fun transaction(prix: Int, type: Int) {
        //Type sera genre si c'est donner ou recevoir de l'argent (carte chance ou case propriété)
        if (type == 0) {
            argent -= prix
        }
        if (type == 1) {
            argent += prix
        }
    }
    fun MettreAJourArgent(viewslist: List<TextView>, Joueurs: List<Joueur>) {
        for (i in Joueurs.indices) {
            viewslist[i].text = "${Joueurs[i].nom} : ${Joueurs[i].argent}$"
        }
    }

}