package pl.jacekrys.routingapp.feature.route.presentation.list

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.`should contain all`
import org.amshove.kluent.shouldBe
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.jacekrys.routingapp.core.base.Resource
import pl.jacekrys.routingapp.core.navigation.Navigator
import pl.jacekrys.routingapp.core.navigation.Screen
import pl.jacekrys.routingapp.feature.route.domain.model.Route
import pl.jacekrys.routingapp.feature.route.domain.usecase.GetRoutesListUseCase
import pl.jacekrys.routingapp.feature.route.presentation.RoutesErrorMapper
import pl.jacekrys.routingapp.utils.MainCoroutineRule

class RouteListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val getRoutesListUseCase: GetRoutesListUseCase = mockk()
    private val routesErrorMapper: RoutesErrorMapper = mockk()
    private val navigator: Navigator = mockk()

    private val listOfRoutes = listOf(
        Route(
            "aabbcc",
            "aabbcc",
            emptyList()
        ),
        Route(
            "ccddee",
            "ccddee",
            emptyList()
        ), Route(
            "ddeeff",
            "ddeeff",
            emptyList()
        ), Route(
            "ffgghh",
            "ffgghh",
            emptyList()
        )
    )

    private lateinit var viewModel: RouteListViewModel


    @Before
    fun setup() {
        viewModel = RouteListViewModel(
            getRoutesListUseCase = getRoutesListUseCase,
            navigator = navigator,
            routesErrorMapper = routesErrorMapper
        )
    }

    @Test
    fun `GIVEN getRoutesListUseCase success WHEN getting routes THEN routes shown and error cleared`() =
        runTest {
            // Given
            coEvery { getRoutesListUseCase.invoke() } returns Resource.Success(listOfRoutes)

            // When
            viewModel.getRoutes()

            // Then
            viewModel.state.value.routes `should contain all` listOfRoutes
            viewModel.state.value.errorInfo shouldBe null
        }

    @Test
    fun `GIVEN searchText WHEN getting routes THEN filtered routes are shown`() =
        runTest {
            // Given
            val filterText = "dd"
            coEvery { getRoutesListUseCase.invoke() } returns Resource.Success(listOfRoutes)
            viewModel.updateSearchText(filterText)
            // When
            viewModel.getRoutes()

            // Then
            viewModel.state.value.routes `should contain all` listOfRoutes.filter {
                it.name.contains(
                    filterText,
                    ignoreCase = true
                )
            }
        }

    @Test
    fun `GIVEN searchText blank WHEN getting routes THEN all routes are shown`() =
        runTest {
            // Given
            val filterText = ""
            coEvery { getRoutesListUseCase.invoke() } returns Resource.Success(listOfRoutes)
            viewModel.updateSearchText(filterText)
            // When
            viewModel.getRoutes()

            // Then
            viewModel.state.value.routes `should contain all` listOfRoutes
        }


    @Test
    fun `GIVEN getRoutesListUseCase error WHEN getting routes THEN error shown`() =
        runTest {
            // Given
            val error = Throwable()
            val mappedError = "Mapped error"
            coEvery { getRoutesListUseCase.invoke() } returns Resource.Error(error)
            every { routesErrorMapper.map(error) } returns mappedError
            // When
            viewModel.getRoutes()

            // Then
            viewModel.state.value.errorInfo shouldBe mappedError
        }

    @Test
    fun `GIVEN route WHEN route chosen THEN navigated to details with chosen route id`() =
        runTest {
            // Given
            val route = listOfRoutes.first()
            every { navigator.navigateTo(Screen.RouteDetails, route.id) } returns Unit
            // When
            viewModel.chooseRoute(route)
            // Then
            verify { navigator.navigateTo(Screen.RouteDetails, route.id) }
        }

}