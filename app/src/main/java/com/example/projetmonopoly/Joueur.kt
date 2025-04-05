package com.example.projetmonopoly

import kotlin.random.Random

open class Joueur(val pion : Pion, val nom : String, var argent : Int, val isbot : Boolean) {

    fun acheter(propriete: String, prix : Int) {
        argent -= prix
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
}