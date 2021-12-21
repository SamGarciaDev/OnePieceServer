package edu.samgarcia.di

import edu.samgarcia.repository.CharacterRepository
import edu.samgarcia.repository.CharacterRepositoryImpl
import org.koin.dsl.module

val koinModule = module {
    single<CharacterRepository> {
        CharacterRepositoryImpl()
    }
}