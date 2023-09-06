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
import pl.jacekrys.routingapp.feature.route.domain.model.RouteDetails
import pl.jacekrys.routingapp.feature.route.domain.repository.RouteRepository

class GetRouteDetailsUseCaseTest {
    private val repository = mockk<RouteRepository>()

    private lateinit var useCase: GetRouteDetailsUseCase
    private val route = Route("", "", emptyList())

    @Before
    fun setup() {
        useCase = GetRouteDetailsUseCase(repository)
    }

    @Test
    fun `GIVEN route object WHEN use case is invoked THEN call getRouteDetails method`() = runTest {
        // When
        useCase(route)

        // Then
        coVerify { repository.getRouteDetails(route) }
    }

    @Test
    fun `GIVEN RouteDetails from repository WHEN use case is invoked THEN returns Success resource`() =
        runTest {
            // Given
            coEvery { repository.getRouteDetails(route) } returns RouteDetails(
                routeId = route.id, 1, 1f,
                listOf()
            )

            // When
            val result = useCase(route)

            // Then
            result `should be instance of` Resource.Success::class
        }

    @Test
    fun `GIVEN exception thrown from repository WHEN use case is invoked THEN returns Error resource`() =
        runTest {
            // Given
            val error = Throwable()
            coEvery { repository.getRouteDetails(route) } throws error

            // When
            val result = useCase(route)

            // Then
            result `should be instance of` Resource.Error::class
        }
}