import com.example.pokemon_listing.data.remote.JsonPokemonDetails
import com.example.pokemon_listing.data.remote.JsonSprites
import com.example.pokemon_listing.data.remote.PokemonDetailsResponse
import com.example.pokemon_listing.domain.interactor.GetPokemonDetailsInteractor
import com.example.pokemon_listing.domain.interactor.PokemonDetailsStatus
import com.example.pokemon_listing.domain.model.PokemonDetailsEntity
import com.example.pokemon_listing.domain.repository.PokemonRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetPokemonDetailsInteractorTest {

    @Mock
    private lateinit var mockRepository: PokemonRepository

    private lateinit var getPokemonDetailsInteractor: GetPokemonDetailsInteractor

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getPokemonDetailsInteractor = GetPokemonDetailsInteractor(mockRepository)
    }

    @Test
    fun `getPokemonDetails should return Success when repository returns success`() = runBlocking {
        // Given
        val pokemonName = "pikachu"
        val mockPokemonDetails = JsonPokemonDetails(
            id = 25,
            name = "Pikachu",
            height = 4,
            weight = 60,
            sprites = JsonSprites("https://pokeapi.co/sprites/pikachu.png")
        )
        val expectedEntity = PokemonDetailsEntity(
            id = 25,
            name = "Pikachu",
            height = 4,
            weight = 60,
            imageUrl = "https://pokeapi.co/sprites/pikachu.png"
        )
        given(mockRepository.getPokemonDetails(pokemonName)).willReturn(
            PokemonDetailsResponse.Success(mockPokemonDetails)
        )

        // When
        val result = getPokemonDetailsInteractor.getPokemonDetails(pokemonName)

        // Then
        assertEquals(PokemonDetailsStatus.Success(expectedEntity), result)
    }

    @Test
    fun `getPokemonDetails should return Error when repository returns failure`() = runBlocking {
        // Given
        val pokemonName = "unknown"
        val errorMessage = "Pokemon not found"
        given(mockRepository.getPokemonDetails(pokemonName)).willReturn(
            PokemonDetailsResponse.Failure(errorMessage)
        )

        // When
        val result = getPokemonDetailsInteractor.getPokemonDetails(pokemonName)

        // Then
        assertEquals(PokemonDetailsStatus.Error(message = errorMessage), result)
    }
}
