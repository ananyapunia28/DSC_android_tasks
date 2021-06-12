package com.example.gazettes.viewmodels

interface LoginListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}