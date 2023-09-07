package pl.jacekrys.routingapp.feature.route.presentation.details

import androidx.lifecycle.SavedStateHandle
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.jacekrys.routingapp.core.base.Resource
import pl.jacekrys.routingapp.core.navigation.Navigator
import pl.jacekrys.routingapp.feature.route.domain.model.Coordinates
import pl.jacekrys.routingapp.feature.route.domain.model.Route
import pl.jacekrys.routingapp.feature.route.domain.model.RouteDetails
import pl.jacekrys.routingapp.feature.route.domain.model.Stop
import pl.jacekrys.routingapp.feature.route.domain.usecase.GetRouteDetailsUseCase
import pl.jacekrys.routingapp.feature.route.domain.usecase.GetRouteUseCase
import pl.jacekrys.routingapp.feature.route.presentation.model.RouteDetailsDisplayable
import pl.jacekrys.routingapp.feature.route.presentation.model.RouteDisplayable
import pl.jacekrys.routingapp.utils.MainCoroutineRule


class RouteDetailsViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val getRouteUseCase: GetRouteUseCase = mockk()
    private val getRouteDetailsUseCase: GetRouteDetailsUseCase = mockk()
    private val navigator: Navigator = mockk(relaxed = true)
    private val savedStateHandle: SavedStateHandle = mockk()

    private lateinit var viewModel: RouteDetailsViewModel

    private val route = Route(
        id = "route id",
        name = "route name",
        stops = listOf(
            Stop(
                id = "Stop id", coordinates = Coordinates(
                    0f,
                    0f
                )
            )
        ),
    )

    private val routeDetails = RouteDetails(
        routeId = "route id",
        distanceInMeters = 1,
        time = 123F,
        geometry = listOf(
            Coordinates(0f, 0f)
        )
    )

    @Before
    fun setup() {
        every<String?> { savedStateHandle[any()] } returns route.id
        viewModel = RouteDetailsViewModel(
            savedStateHandle = savedStateHandle,
            getRouteDetailsUseCase = getRouteDetailsUseCase,
            getRouteUseCase = getRouteUseCase,
            navigator = navigator,
        )
    }

    @Test
    fun `GIVEN getRouteUseCase success WHEN getting route details THEN route and routeDetails shown`() =
        runTest {
            // Given
            coEvery { getRouteUseCase.invoke(route.id) } returns Resource.Success(route)
            coEvery { getRouteDetailsUseCase.invoke(route) } returns Resource.Success(routeDetails)

            // When
            viewModel.getRouteDetails()

            // Then
            viewModel.state.value.route shouldBeEqualTo RouteDisplayable(route)
            viewModel.state.value.routeDetails shouldBeEqualTo RouteDetailsDisplayable(routeDetails)
            viewModel.state.value.isLoading shouldBe false
            viewModel.state.value.routeError shouldBe false
            viewModel.state.value.routeDetailsError shouldBe false
        }

    @Test
    fun `GIVEN getRouteUseCase error WHEN getting route details THEN route and routeDetails shown`() =
        runTest {
            // Given
            coEvery { getRouteUseCase.invoke(route.id) } returns Resource.Error(Throwable())
            coEvery { getRouteDetailsUseCase.invoke(route) } returns Resource.Success(routeDetails)

            // When
            viewModel.getRouteDetails()

            // Then
            viewModel.state.value.isLoading shouldBe false
            viewModel.state.value.routeError shouldBe true
            viewModel.state.value.routeDetailsError shouldBe false
        }

    @Test
    fun `GIVEN getRouteUseCase success and getRouteDetailsUseCase error WHEN getting route details THEN route and routeDetails shown`() =
        runTest {
            // Given
            coEvery { getRouteUseCase.invoke(route.id) } returns Resource.Success(route)
            coEvery { getRouteDetailsUseCase.invoke(route) } returns Resource.Error(Throwable())

            // When
            viewModel.getRouteDetails()

            // Then
            viewModel.state.value.route shouldBeEqualTo RouteDisplayable(route)
            viewModel.state.value.isLoading shouldBe false
            viewModel.state.value.routeError shouldBe false
            viewModel.state.value.routeDetailsError shouldBe true
        }

    @Test
    fun `WHEN navigation back THEN navigator's navigateBack method invoked`() =
        runTest {
            // When
            viewModel.backToList()

            // Then
            verify { navigator.navigateBack() }
        }

}