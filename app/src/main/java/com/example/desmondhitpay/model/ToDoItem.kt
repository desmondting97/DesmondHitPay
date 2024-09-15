package com.example.desmondhitpay.model

import java.io.Serializable

data class ToDoItem(
    var columnID: String = "",
    var title: String,
    var description: String,
    var timeStamp: String,
    @Transient var position: Int = -1
): Serializable
