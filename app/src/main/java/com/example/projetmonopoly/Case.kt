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
                 }

object CasePrison : Case(
    index = 14,
    xpos = 0.875f,
    ypos = 0.00f,
    nom = "Prison"
)

class CaseChance(index: Int , xpos : Float, ypos : Float,nom : String) : Case(index, xpos, ypos,nom){

}

class CaseDépart(index: Int , xpos : Float, ypos : Float,nom : String) : Case(index, xpos, ypos,nom){
    companion object {
        const val GAIN = 100
    }
}