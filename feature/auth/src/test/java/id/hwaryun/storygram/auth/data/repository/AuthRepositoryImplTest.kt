package id.hwaryun.storygram.auth.data.repository

import id.hwaryun.storygram.auth.data.network.datasource.FakeAuthDataSource
import id.hwaryun.storygram.auth.utils.AuthDummy
import id.hwaryun.storygram.utils.TestDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AuthRepositoryImplTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var authDataSource: FakeAuthDataSource
    private lateinit var repository: AuthRepository

    private val authenticated = AuthDummy.authenticated()
    private val registered = AuthDummy.registered()

    private val loginRequest = AuthDummy.validLoginRequest()
    private val registerRequest = AuthDummy.validRegisterRequest()

    @Before
    fun setUp() {
        authDataSource = FakeAuthDataSource()
        repository = AuthRepositoryImpl(authDataSource)
    }

    @Test
    fun `when login add all input valid then return response user`() = runTest {
        val expected = authenticated
        val actual = authDataSource.loginUser(loginRequest)
        assertNotNull(actual)
        assertEquals(expected, actual)
    }

    @Test
    fun `when register add all input valid then return response nothing`() = runTest {
        val expected = registered
        val actual = authDataSource.registerUser(registerRequest)
        assertNotNull(actual)
        assertEquals(expected, actual)
    }
}