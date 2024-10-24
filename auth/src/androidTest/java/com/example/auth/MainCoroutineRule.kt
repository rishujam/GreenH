package com.example.auth

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/*
 * Created by Sudhanshu Kumar on 06/05/24.
 */

@OptIn(ExperimentalCoroutinesApi::class)
class MainCoroutineRule (
    val testDispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }

}

@OptIn(ExperimentalCoroutinesApi::class)
fun MainCoroutineRule.runBlockingTest(block: suspend () -> Unit) = runTest(this.testDispatcher) {
    block()
}