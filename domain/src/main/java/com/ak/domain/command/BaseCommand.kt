package com.ak.domain.command

import com.ak.domain.model.CommandType

abstract class BaseCommand {

    abstract val type: CommandType

    lateinit var arg1: String
    lateinit var arg2: String

    fun matches(text: String): Boolean {
        val parts = text.split(" ")
        if (parts.size == type.parts && parts.first() == type.name) {
            if (parts.size > 1) arg1 = parts[1]
            if (parts.size > 2) arg2 = parts[2]
            return true
        }
        return false
    }
}
