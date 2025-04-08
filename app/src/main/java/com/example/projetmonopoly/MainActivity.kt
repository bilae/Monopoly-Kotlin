package com.example.projetmonopoly
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    /* // Liste des positions des cases en pourcentage du plateau (X%, Y%)
    private val boardPositions = listOf(
        Pair(0.05f, 0.85f),  // Case 0 (Départ) - EXEMPLE
        Pair(0.15f, 0.85f),  // Case 1
        Pair(0.25f, 0.85f),  // Case 2
        Pair(0.35f, 0.85f),  // Case 3
        // AJOUTER LES AUTRES CASES AVEC LES VRAIES COORDONNÉES
    ) // Dans le mai

*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinnerBots: Spinner = findViewById(R.id.spinnerBots)
        val btnStartGame: Button = findViewById(R.id.btnStartGame)

        // Ici on choisit le nb de joueurs (des bots enft)
        val botOptions = arrayOf("1 Bot", "2 Bots", "3 Bots")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, botOptions)
        spinnerBots.adapter = adapter

        btnStartGame.setOnClickListener {
            val selectedBots = spinnerBots.selectedItem.toString().split(" ")[0].toInt()

            // Transfère l'info vers le Game Activity
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("NUM_BOTS", selectedBots)
            startActivity(intent)
        }
        /* fun initializeBoardDimensions() {
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
        } // Dans le main */

    }
}
