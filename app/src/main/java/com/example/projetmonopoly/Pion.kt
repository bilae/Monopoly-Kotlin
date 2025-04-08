package com.example.projetmonopoly
import android.widget.ImageView
import android.util.Log


class Pion(
    private val imageView: ImageView,
    private val boardView: ImageView,
    val couleur: String,
    var xpos: Int,
    var ypos: Int,
    var prison: Boolean
) {
    private var boardX = 0f
    private var boardY = 0f
    private var boardWidth = 0f
    private var boardHeight = 0f // Dans case.kt

    // Liste des positions des cases en pourcentage du plateau (X%, Y%)
    private val boardPositions = listOf(
        Pair(0.05f, 0.85f),  // Case 0 (Départ) - EXEMPLE
        Pair(0.15f, 0.85f),  // Case 1
        Pair(0.25f, 0.85f),  // Case 2
        Pair(0.35f, 0.85f),  // Case 3
        // AJOUTER LES AUTRES CASES AVEC LES VRAIES COORDONNÉES
    ) // Dans le main

    fun initializeBoardDimensions() {
        boardView.post {
            boardX = boardView.x
            boardY = boardView.y
            boardWidth = boardView.width.toFloat()
            boardHeight = boardView.height.toFloat()
            Log.d("Pion", "Plateau (image.png): x=$boardX, y=$boardY, width=$boardWidth, height=$boardHeight")
        }
    } // Dans le main

    // Convertir une position relative en pixels réels
    fun getCasePosition(index: Int): Pair<Float, Float> {
        if (index !in boardPositions.indices) {
            Log.e("Pion", "Index de case invalide: $index")
            return Pair(boardX, boardY) // Retourne la position actuelle pour éviter les erreurs (fait par chatgpt)
        }

        val (relX, relY) = boardPositions[index]
        val absX = boardX + (relX * boardWidth)
        val absY = boardY + (relY * boardHeight)

        return Pair(absX, absY)
    } // Dans le main

    // Déplacer le pion avec une animation fluide
    fun moveToCase(index: Int) { // Reste ici
        boardView.post {
            val (x, y) = getCasePosition(index)
            imageView.animate() //Animation par chatgpt
                .x(x)
                .y(y)
                .setDuration(500) // Animation de 500ms
                .start()

            // Mettre à jour les positions réelles du pion
            xpos = x.toInt()
            ypos = y.toInt()
        }
    }
}
