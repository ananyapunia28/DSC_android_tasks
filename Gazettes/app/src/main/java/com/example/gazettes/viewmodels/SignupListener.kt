package com.example.gazettes.viewmodels

interface SignupListener {
    fun on_Started()
    fun on_Success()
    fun on_Failure(message: String)
}