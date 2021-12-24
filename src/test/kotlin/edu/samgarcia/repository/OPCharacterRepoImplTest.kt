package edu.samgarcia.repository

import io.ktor.util.reflect.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.Test

internal class OPCharacterRepoImplTest : KoinTest {
    private val repo: OPCharacterRepo by inject()

    @Before
    fun setUp() {
        startKoin {
            modules(
                module {
                    single<OPCharacterRepo> { OPCharacterRepoImpl() }
                }
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `get all characters, should return a list`() {
        assertThat(repo.getAll()).instanceOf(List::class)
    }
}