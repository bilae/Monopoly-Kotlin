package com.example.projetmonopoly

open class Case(
    protected val index: Int,
    protected val xpos: Float,
    protected val ypos: Float,
    protected val nom: String
) {
    val NAMECASE: String get() = nom
    operator fun component1() = xpos
    operator fun component2() = ypos
}

class Proprietes(index: Int , xpos : Float, ypos : Float, nom : String, val prix : Int, var location : Int,
                 var proprietaire :Joueur? = null) : Case(index, xpos, ypos, nom){
    fun acheter(argent : Int, prix : Int, joueur : String){

    }
}

class CasePrison(index: Int , xpos : Float, ypos : Float,nom : String) : Case(index, xpos, ypos,nom){
    fun estenprison(jours_restants : Int){
    }
}

class CaseChance(index: Int , xpos : Float, ypos : Float,nom : String) : Case(index, xpos, ypos,nom){
    fun piochercarte(){
    }
}

class CaseDépart(index: Int , xpos : Float, ypos : Float,nom : String) : Case(index, xpos, ypos,nom){
    companion object {
        const val GAIN = 100
    }
}