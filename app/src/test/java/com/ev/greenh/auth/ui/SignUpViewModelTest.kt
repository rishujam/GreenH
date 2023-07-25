package com.ev.greenh.auth.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModelProvider
import com.ev.greenh.auth.ui.events.SignUpEvents
import com.ev.greenh.auth.ui.events.SignUpUiEvents
import com.ev.greenh.auth.ui.states.SignUpProgress
import com.ev.greenh.models.Profile
import com.ev.greenh.models.Response
import com.ev.greenh.repository.AuthRepository
import com.ev.greenh.util.Resource
import com.ev.greenh.viewmodels.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

/*
 * Created by Sudhanshu Kumar on 17/07/23.
 */

@RunWith(MockitoJUnitRunner::class)
class SignUpViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SignUpViewModel

    @Mock
    lateinit var repository: AuthRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        viewModel = SignUpViewModel(repository)
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when event is NextClick, set phone and emit loading`() = runTest {
        val events = mutableListOf<SignUpUiEvents>()
        val job = launch {
            viewModel.eventFlow.collect {
                events.add(it)
            }
        }
        val phone = "9999999999"
        viewModel.onEvent(SignUpEvents.NextClick(phone))
        testDispatcher.scheduler.advanceUntilIdle()
        var result = true
        if (viewModel.state.value.phoneNo != phone) result = false
        if (events.size == 1 && events.getOrNull(0) != SignUpUiEvents.Loading(true)) result = false
        assert(result)
        job.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when event is OtpOption and options is null, emit not loading and toast`() = runTest {
        val events = mutableListOf<SignUpUiEvents>()
        val job = launch {
            viewModel.eventFlow.collect {
                events.add(it)
            }
        }
        val errorMsg = "Error"
        viewModel.onEvent(SignUpEvents.OtpOption(null, errorMsg, null))
        testDispatcher.scheduler.advanceUntilIdle()
        var result = true
        if (events.size == 2 && events.getOrNull(0) != SignUpUiEvents.Loading(false)) result = false
        if (events.getOrNull(1) != SignUpUiEvents.ShowToast(errorMsg)) result = false
        assert(result)
        job.cancel()
    }

    @Test
    fun `when event is OtpOption, set phone`() {
        val phone = "999999998"
        viewModel.onEvent(SignUpEvents.OtpOption(null, null, phone))
        var result = true
        if (viewModel.state.value.phoneNo != phone) result = false
        assert(result)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when event is VerifyClick, emit loading`() = runTest {
        val events = mutableListOf<SignUpUiEvents>()
        val job = launch {
            viewModel.eventFlow.collect {
                events.add(it)
            }
        }
        viewModel.onEvent(SignUpEvents.VerifyClick("123456", "user", "token"))
        testDispatcher.scheduler.advanceUntilIdle()
        var result = true
        if (events.size == 1 && events.getOrNull(0) != SignUpUiEvents.Loading(true)) result = false
        assert(result)
        job.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when event is WrongNo, set phone empty and emit enterPhoneStage`() = runTest {
        val events = mutableListOf<SignUpUiEvents>()
        val job = launch {
            viewModel.eventFlow.collect {
                events.add(it)
            }
        }
        viewModel.onEvent(SignUpEvents.WrongNo)
        advanceUntilIdle()
        var result = true
        if (viewModel.state.value.phoneNo != "") result = false
        if (events.size == 1 && events.getOrNull(0) != SignUpUiEvents.ScreenChanged(SignUpProgress.EnterPhoneStage)) result = false
        assert(result)
        job.cancel()
    }
}