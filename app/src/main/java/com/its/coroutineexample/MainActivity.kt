package com.its.coroutineexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private val RESULT1 = "RESULT #1"
    private val RESULT2 = "RESULT #2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            CoroutineScope(IO).launch {
                fakeApiCall()
            }

        }
    }

    // set text in text
    private fun setText(input: String) {

        val newText = text.text.toString() + "\n$input"
        text.text = newText

    }

    /**
     * Update your UI using with WithContext
     * */
    private suspend fun updateMainThread(input: String) {

        printThread("setTextOnMainThread: $input")
        withContext(Main) {
            setText(input)
        }
    }

    /**
     * Get your api result
     * */
    private suspend fun fakeApiCall() {

        printThread("fakeApiCall")
        val result1 = getResultFromApi();

        if (result1 == RESULT1) {
            updateMainThread("Go $result1")

            val result2 = getResult2FromApi();

            if (result2 == RESULT2) {
                updateMainThread("Go $result2")
            } else {
                updateMainThread("Could not get result #2")
            }
        } else {
            updateMainThread("Could not get result #1")
        }

    }

    /**
     * Call your api here.
     * replace this code from api call method
     * */
    private suspend fun getResultFromApi(): String {

        printThread("getResultFromApi")
        delay(1000)
        return RESULT1
    }

    /**
     * Call your api here.
     * replace this code from api call method
     * */
    private suspend fun getResult2FromApi(): String {
        printThread("getResult2FromApi")
        delay(1000)
        return RESULT2
    }

    /**
     * TODO find out current thread name
     * */
    private fun printThread(methodName: String) {
        println("debug: $methodName : ${Thread.currentThread().name}")
    }
}