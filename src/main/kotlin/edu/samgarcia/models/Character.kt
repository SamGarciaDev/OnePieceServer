package edu.samgarcia.models

import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val id: Int,
    val name: String,
    val img: String,
    val about: String,
    val rating: Double,
    val firstAppearance: Int,
    val devilFruits: List<String>,
    val family: List<String>
)
