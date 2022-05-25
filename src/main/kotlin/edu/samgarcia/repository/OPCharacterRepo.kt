package edu.samgarcia.repository

import edu.samgarcia.models.OPCharacter

interface OPCharacterRepo {
    fun getAll() : List<OPCharacter>
    fun initdb()
}