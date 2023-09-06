package pl.jacekrys.routingapp.feature.route.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.`should be instance of`
import org.junit.Before
import org.junit.Test
import pl.jacekrys.routingapp.core.base.Resource
import pl.jacekrys.routingapp.feature.route.domain.model.Route
import pl.jacekrys.routingapp.feature.route.domain.repository.RouteRepository

class GetRoutesListUseCaseTest {
    private val repository = mockk<RouteRepository>()

    private lateinit var useCase: GetRoutesListUseCase

    @Before
    fun setup() {
        useCase = GetRoutesListUseCase(repository)
    }

    @Test
    fun `WHEN use case is invoked THEN call getRoutes method`() = runTest {
        // When
        useCase()

        // Then
        coVerify { repository.getRoutes() }
    }

    @Test
    fun `GIVEN routes list from repository WHEN use case is invoked THEN returns Success resource`() =
        runTest {
            // Given
            coEvery { repository.getRoutes() } returns listOf(
                Route(
                    id = "",
                    name = "",
                    stops = null
                ), Route(id = "", name = "", stops = null)
            )

            // When
            val result = useCase()

            // Then
            result `should be instance of` Resource.Success::class
        }

    @Test
    fun `GIVEN exception thrown from repository WHEN use case is invoked THEN returns Error resource`() =
        runTest {
            // Given
            val error = Throwable()
            coEvery { repository.getRoutes() } throws error

            // When
            val result = useCase()

            // Then
            result `should be instance of` Resource.Error::class
        }
}