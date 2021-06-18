package com.example.gazettes.viewmodels

interface SignupListener {
    fun on_Started()
    fun onSuccess()
    fun onFailure(message: String)
}