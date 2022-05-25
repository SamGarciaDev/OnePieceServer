package edu.samgarcia.models

import kotlinx.serialization.Serializable

@Serializable
data class OPCharacter(
    val id: Int,
    val name: String,
    val img: String,
    val about: String,
    val origin: String,
    val bounty: Long,
    val rating: Double,
    val devilFruits: List<String>,
    val family: List<String>
)
