package id.hwaryun.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import id.hwaryun.core.wrapper.ViewResource
import id.hwaryun.splash.utils.TestDispatcherRule
import id.hwaryun.splash.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SplashViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    private lateinit var splashViewModel: SplashViewModel

    @Test
    fun `when getToken should return string`() = runTest {
        val expected = MutableLiveData<ViewResource<String>>()
        expected.value = ViewResource.Success("token")

        `when`(splashViewModel.tokenResult).thenReturn(expected)

        val actual = splashViewModel.tokenResult.getOrAwaitValue()
        verify(splashViewModel).tokenResult
        assertNotNull(actual)
        assertEquals(expected.value?.payload, actual.payload)
    }
}