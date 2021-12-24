package edu.samgarcia.repository

import edu.samgarcia.models.OPCharacter
import org.assertj.core.api.Assertions.assertThat
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.Test

@RunWith(MockitoJUnitRunner::class)
internal class OPCharacterServiceImplTest {
    @Mock
    private lateinit var repo: OPCharacterRepoImpl

    @InjectMocks
    private lateinit var characterService: OPCharacterServiceImpl

    @Test
    fun `should return correct number of pages when the number of characters is zero`() {
        Mockito.`when`(repo.getAll()).thenReturn(emptyList())
        assertThat(characterService.getNumPages()).isEqualTo(0)
    }

    @Test
    fun `should return correct number of pages when the number of characters is 5`() {
        val characterMockList = arrayListOf<OPCharacter>()

        for (i in 0 until 5) {
            characterMockList += OPCharacter(
                id = 0,
                name = "",
                img = "",
                about = "",
                rating = 0.0,
                devilFruits = emptyList(),
                family = emptyList()
            )
        }

        Mockito.`when`(repo.getAll()).thenReturn(characterMockList)

        val expected = (characterMockList.size + OPCharacterServiceImpl.CHARACTERS_PER_PAGE - 1) / OPCharacterServiceImpl.CHARACTERS_PER_PAGE
        val result = characterService.getNumPages()

        assertThat(result).isEqualTo(expected)
    }
}