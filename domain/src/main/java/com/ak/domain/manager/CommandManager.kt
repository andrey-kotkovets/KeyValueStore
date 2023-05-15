package com.ak.domain.manager

import com.ak.domain.command.BaseCommand

class CommandManager(
    private val commands: Set<BaseCommand>
) {

    fun getCommand(commandText: String): BaseCommand? {
        return commands.find { it.matches(commandText) }
    }
}
