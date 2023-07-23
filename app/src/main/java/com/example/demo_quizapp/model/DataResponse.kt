package com.example.demo_quizapp.model

data class DataResponse(
    val response_code: Int,
    val results: List<Result>
)