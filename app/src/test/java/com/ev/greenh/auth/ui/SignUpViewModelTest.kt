package com.ev.greenh.auth.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.auth.ui.SignUpViewModel
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Rule

/*
 * Created by Sudhanshu Kumar on 17/07/23.
 */

class SignUpViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SignUpViewModel

//    private val repository = mockk<AuthRepository>()

//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Before
//    fun setUp() {
//        viewModel = SignUpViewModel(repository)
//        Dispatchers.setMain(testDispatcher)
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `when event is NextClick, set phone and emit loading`() = runTest {
//        val events = mutableListOf<SignUpUiEvents>()
//        val job = launch {
//            viewModel.eventFlow.collect {
//                events.add(it)
//            }
//        }
//        val phone = "9999999999"
//        viewModel.onEvent(SignUpEvents.NextClick(phone))
//        testDispatcher.scheduler.advanceUntilIdle()
//        var result = true
//        if (viewModel.state.value.phoneNo != phone) result = false
//        if (events.size == 1 && events.getOrNull(0) != SignUpUiEvents.Loading(true)) result = false
//        assert(result)
//        job.cancel()
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `when event is OtpOption and options is null, emit not loading and toast`() = runTest {
//        val events = mutableListOf<SignUpUiEvents>()
//        val job = launch {
//            viewModel.eventFlow.collect {
//                events.add(it)
//            }
//        }
//        val errorMsg = "Error"
//        viewModel.onEvent(SignUpEvents.OtpOption(null, errorMsg, null))
//        testDispatcher.scheduler.advanceUntilIdle()
//        var result = true
//        if (events.size == 2 && events.getOrNull(0) != SignUpUiEvents.Loading(false)) result = false
//        if (events.getOrNull(1) != SignUpUiEvents.ShowToast(errorMsg)) result = false
//        assert(result)
//        job.cancel()
//    }
//
//    @Test
//    fun `when event is OtpOption, set phone`() {
//        val phone = "999999998"
//        viewModel.onEvent(SignUpEvents.OtpOption(null, null, phone))
//        var result = true
//        if (viewModel.state.value.phoneNo != phone) result = false
//        assert(result)
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `when event is WrongNo, set phone empty and emit enterPhoneStage`() = runTest {
//        val events = mutableListOf<SignUpUiEvents>()
//        val job = launch {
//            viewModel.eventFlow.collect {
//                events.add(it)
//            }
//        }
//        viewModel.onEvent(SignUpEvents.WrongNo)
//        advanceUntilIdle()
//        var result = true
//        if (viewModel.state.value.phoneNo != "") result = false
//        if (events.size == 1 && events.getOrNull(0) != SignUpUiEvents.ScreenChanged(SignUpProgress.EnterPhoneStage)) result = false
//        assert(result)
//        job.cancel()
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `when event is VerifyClick with correct inputs, emit Verified`() = runTest {
//        val list = mutableListOf<SignUpUiEvents>()
//        val job = launch {
//            viewModel.eventFlow.test {
//                list.add(awaitItem())
//            }
//        }
//        //set
//        val otp = "123456"
//        val verifyId = "13431"
//        val phone = "9999999999"
//        val userColl = "user"
//        val tokenColl = "token"
//        viewModel.state.value.phoneNo = phone
//        viewModel.verifyId = verifyId
//        coEvery { repository.verifyUser(otp, verifyId, phone, userColl, tokenColl) } returns true
//
//        //act
//        viewModel.onEvent(SignUpEvents.VerifyClick(otp, userColl, tokenColl))
//        job.join()
//        if(list.size == 2 && list.getOrNull(0) == SignUpUiEvents.Loading(true)) assert(true)
//        if(list.getOrNull(1) == SignUpUiEvents.ScreenChanged(SignUpProgress.VerifiedPhoneStage)) assert(true)
//        job.cancel()
//    }
}