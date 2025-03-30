package com.example.projetmonopoly

import kotlin.random.Random

class Joueur(val couleur : String, val nom : String, var argent : Int){

    fun acheter(propriete: String, prix : Int){
        argent -= prix
        // à méditer pour la possession de la propriété
    }
    fun lancerDe(){
        val resultat = Random.nextInt(1,7)
        //avancerPion(resultat)
    }
    fun transaction(prix : Int, type : Int){
        if (type == 0) {
            argent -= prix
        }
        if (type == 1) {
            argent += prix
        }
    }
}