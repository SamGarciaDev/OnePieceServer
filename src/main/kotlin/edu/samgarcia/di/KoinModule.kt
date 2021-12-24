package edu.samgarcia.di

import edu.samgarcia.repository.OPCharacterRepo
import edu.samgarcia.repository.OPCharacterRepoImpl
import edu.samgarcia.repository.OPCharacterService
import edu.samgarcia.repository.OPCharacterServiceImpl
import org.koin.dsl.module

val koinModule = module {
    single<OPCharacterRepo> { OPCharacterRepoImpl() }
    single<OPCharacterService> { OPCharacterServiceImpl(get()) }
}