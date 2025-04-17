package com.example.projetmonopoly

import android.content.Context
import androidx.appcompat.app.AlertDialog

interface Command {
    fun execute()
}
class GainArgentCommand(val joueur: Joueur, val montant: Int) : Command {
    override fun execute() {
        joueur.argent += montant
    }
}
class PerteArgentCommand(val joueur: Joueur, val montant: Int) : Command {
    override fun execute() {
        joueur.argent -= montant
    }
}
class AchatProprieteCommand(val joueur : Joueur, val proprietes: Proprietes) : Command {
    override fun execute() {
        PerteArgentCommand(joueur,proprietes.prix).execute()
        proprietes.proprietaire = joueur

    }
}

class LouerProprieteCommand(val joueur : Joueur, val proprietes: Proprietes) : Command {
    override fun execute() {
        PerteArgentCommand(joueur,proprietes.location).execute()
        GainArgentCommand(proprietes.proprietaire!!, proprietes.location).execute()
    }
}
class CaseDépartCommand(val joueur: Joueur) : Command {
    override fun execute() {
        GainArgentCommand(joueur, CaseDépart.GAIN ).execute()
    }
}
class CaseChanceCommand(
    private val joueur: Joueur,
    private val case: Case
) : Command {
    override fun execute() {
        when (case) {
            is CaseChance -> {
                val chanceCommands = listOf<Command>(
                    GainArgentCommand(joueur, 100),
                    PerteArgentCommand(joueur, 50),
                    PerteArgentCommand(joueur, 200)
                )
                chanceCommands.random().execute()
            }
        }
    }
}