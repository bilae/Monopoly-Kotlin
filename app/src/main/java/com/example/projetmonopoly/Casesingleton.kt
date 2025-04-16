package com.example.projetmonopoly

object Casesingleton {
    val boardPositions = listOf(
        //par rapport au coté gauche
        CaseDépart(0, 0f, 0.875f, "Départ"),  // Départ
        Proprietes(1, 0f, 0.75f, "Rio", 100, 10, null),
        Proprietes(2, 0f, 0.625f, "Delhi", 100, 10, null),
        Proprietes(
            3,
            0f,
            0.5f,
            "Bangkok",
            130,
            15,
            null
        ),
        Proprietes(
            4,
            0f,
            0.375f,
            "Gare bleu",
            100,
            35,
            null
        ),
        Proprietes(5, 0f, 0.25f, "Caire", 150, 15, null),
        Proprietes(6, 0f, 0.125f, "Madrid", 150, 15, null),
        CaseChance(7, 0.00f, 0.0f, "Chance"),
        //par rapport au coté haut
        Proprietes(
            8,
            0.125f,
            0.0f,
            "Jakarta",
            170,
            20,
            null
        ),
        Proprietes(
            9,
            0.250f,
            0.0f,
            "Berlin",
            180,
            20,
            null
        ),
        Proprietes(
            10,
            0.375f,
            0.0f,
            "Moscou",
            200,
            30,
            null
        ),
        Proprietes(
            11,
            0.5f,
            0.0f,
            "Gare orange",
            150,
            35,
            null
        ),
        Proprietes(
            12,
            0.625f,
            0.00f,
            "Toronto",
            200,
            30,
            null
        ),
        Proprietes(
            13,
            0.75f,
            0.00f,
            "Séoul",
            200,
            30,
            null
        ),
        CasePrison(14, 0.875f, 0.00f, "Prison"),
        //par rapport au coté droit
        Proprietes(
            15,
            0.875f,
            0.125f,
            "Zurich",
            250,
            35,
            null
        ),
        Proprietes(
            16,
            0.875f,
            0.25f,
            "Riyadh",
            250,
            35,
            null
        ),
        Proprietes(
            17,
            0.875f,
            0.375f,
            "Sydney",
            300,
            40,
            null
        ),
        Proprietes(
            18,
            0.875f,
            0.5f,
            "Gare verte",
            200,
            35,
            null
        ),
        Proprietes(
            19,
            0.875f,
            0.625f,
            "Beijing",
            300,
            40,
            null
        ),
        Proprietes(20, 0.875f , 0.75f, "Dubai", 300, 40, null),
        CaseChance(21, 0.875f, 0.875f, "Carte chance"),
        //par rapport au coté bas
        Proprietes(22, 0.75f, 0.875f, "Paris", 350, 45, null),
        Proprietes(
            23,
            0.625f,
            0.875f,
            "Hong Kong",
            350,
            50,
            null
        ),
        Proprietes(24, 0.5f, 0.875f, "Londres", 420, 70, null),
        Proprietes(
            25,
            0.375f,
            0.875f,
            "Gare rouge",
            250,
            35,
            null
        ),
        Proprietes(
            26,
            0.25f,
            0.875f,
            "Tokyo",
            420,
            70,
            null
        ),
        Proprietes(
            27,
            0.125f,
            0.875f,
            "New-York",
            450,
            80,
            null
        )
    )
}
