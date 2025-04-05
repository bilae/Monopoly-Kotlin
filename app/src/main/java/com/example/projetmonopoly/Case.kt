package com.example.projetmonopoly

open class Case(val xpos : Int, val ypos : Int, val nom : String) {

}


class Proprietes(xpos : Int, ypos : Int, nom : String, val prix : Int, var location : Int,
                 var proprietaire : String) : Case(xpos, ypos, nom){
    fun acheter(argent : Int, prix : Int, joueur : String){

    }
}