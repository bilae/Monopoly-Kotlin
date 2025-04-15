package com.example.projetmonopoly

import android.content.Context
import androidx.appcompat.app.AlertDialog

interface Command {
    fun execute()
}

class BuyPropertyCommand(val joueur : Joueur, val proprietes: Proprietes) : Command {
    override fun execute() {
        joueur.acheter(proprietes.nom,proprietes.prix)
    }
}

class RentPropertyCommand(val joueur : Joueur, val proprietes: Proprietes) : Command {
    override fun execute() {
        joueur.acheter(proprietes.nom,proprietes.location)
    }
}

