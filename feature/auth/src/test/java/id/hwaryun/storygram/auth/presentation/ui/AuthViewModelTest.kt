package id.hwaryun.storygram.auth.presentation.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import id.hwaryun.core.wrapper.ViewResource
import id.hwaryun.storygram.auth.data.model.viewparam.UserViewParam
import id.hwaryun.storygram.auth.domain.LoginUserUseCase
import id.hwaryun.storygram.auth.domain.RegisterUserUseCase
import id.hwaryun.storygram.auth.utils.AuthDummy
import id.hwaryun.storygram.auth.utils.getOrAwaitValue
import id.hwaryun.storygram.utils.TestDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AuthViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    private lateinit var loginUserUseCase: LoginUserUseCase

    @Mock
    private lateinit var registerUserUseCase: RegisterUserUseCase

    private lateinit var authViewModel: AuthViewModel

    private val user = AuthDummy.getUser()

    private val loginParam = AuthDummy.validLoginParam()
    private val loginRequest = AuthDummy.validLoginRequest()

    private val registerParam = AuthDummy.validRegisterParam()
    private val registerRequest = AuthDummy.validRegisterRequest()

    @Before
    fun setUp() {
        authViewModel = AuthViewModel(loginUserUseCase, registerUserUseCase)
    }

    @Test
    fun `when login should return success`() = runTest {
        val expected = MutableLiveData<ViewResource<UserViewParam>>()
        expected.value = ViewResource.Success(user)

        whenever(loginUserUseCase(loginParam))
            .thenReturn(flow { emit(ViewResource.Success(user)) })

        authViewModel.loginUser(loginRequest.email, loginRequest.password)

        advanceUntilIdle()

        val actual = authViewModel.loginResult.getOrAwaitValue()
        verify(loginUserUseCase)(loginParam)
        assertNotNull(actual)
        assertEquals(expected.value?.payload, actual.payload)
    }

    @Test
    fun `when register should return success`() = runTest {
        val expected = MutableLiveData<ViewResource<UserViewParam>>()
        expected.value = ViewResource.Success(user)

        whenever(registerUserUseCase(registerParam))
            .thenReturn(flow { emit(ViewResource.Success(user)) })

        authViewModel.registerUser(
            registerRequest.name,
            registerRequest.email,
            registerRequest.password
        )

        advanceUntilIdle()

        val actual = authViewModel.registerResult.getOrAwaitValue()
        verify(registerUserUseCase)(registerParam)
        assertNotNull(actual)
        assertEquals(expected.value?.payload, actual.payload)
    }

    @Test
    fun `when login user not found should return error`() = runTest {
        val expected = MutableLiveData<ViewResource<UserViewParam>>()
        expected.value = ViewResource.Error(NullPointerException())

        whenever(loginUserUseCase(loginParam))
            .thenReturn(flow { emit(ViewResource.Error(NullPointerException())) })

        authViewModel.loginUser(loginRequest.email, loginRequest.password)

        advanceUntilIdle()

        val actual = authViewModel.loginResult.getOrAwaitValue()
        verify(loginUserUseCase)(loginParam)
        assertNotNull(actual)
        assertEquals(expected.value?.payload, actual.payload)
    }

    @Test
    fun `when register failed should return error`() = runTest {
        val expected = MutableLiveData<ViewResource<UserViewParam>>()
        expected.value = ViewResource.Error(RuntimeException())

        whenever(registerUserUseCase(registerParam))
            .thenReturn(flow { emit(ViewResource.Error(RuntimeException())) })

        authViewModel.registerUser(
            registerRequest.name,
            registerRequest.email,
            registerRequest.password
        )

        advanceUntilIdle()

        val actual = authViewModel.registerResult.getOrAwaitValue()
        verify(registerUserUseCase)(registerParam)
        assertNotNull(actual)
        assertEquals(expected.value?.payload, actual.payload)
    }
}