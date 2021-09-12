package com.example.seriesapp.data.series

import java.io.Serializable

data class Data(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<Result>,
    val total: Int
)