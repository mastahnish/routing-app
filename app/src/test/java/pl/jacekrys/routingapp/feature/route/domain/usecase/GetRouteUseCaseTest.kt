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

class GetRouteUseCaseTest {
    private val repository = mockk<RouteRepository>()

    private lateinit var useCase: GetRouteUseCase
    private val routeId = "route_id"

    @Before
    fun setup() {
        useCase = GetRouteUseCase(repository)
    }

    @Test
    fun `WHEN use case is invoked THEN call getRoute method`() = runTest {
        // When
        useCase(routeId)

        // Then
        coVerify { repository.getRoute(routeId) }
    }

    @Test
    fun `GIVEN route from repository WHEN use case is invoked THEN returns Success resource`() =
        runTest {
            // Given
            coEvery { repository.getRoute(routeId) } returns Route(
                id = routeId,
                name = "",
                stops = null
            )

            // When
            val result = useCase(routeId)

            // Then
            result `should be instance of` Resource.Success::class
        }

    @Test
    fun `GIVEN exception thrown from repository WHEN use case is invoked THEN returns Error resource`() =
        runTest {
            // Given
            val error = Throwable()
            coEvery { repository.getRoute(routeId) } throws error

            // When
            val result = useCase(routeId)

            // Then
            result `should be instance of` Resource.Error::class
        }
}