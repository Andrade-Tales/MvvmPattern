package com.lista.mvvmpattern.repositories

import com.lista.mvvmpattern.rest.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun getAllLives() = retrofitService.getAllLives()

}