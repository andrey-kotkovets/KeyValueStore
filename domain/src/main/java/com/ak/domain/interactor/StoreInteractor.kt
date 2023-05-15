package com.ak.domain.interactor

import com.ak.domain.command.BeginCommand
import com.ak.domain.command.CommitCommand
import com.ak.domain.command.CountCommand
import com.ak.domain.command.DeleteCommand
import com.ak.domain.command.GetCommand
import com.ak.domain.command.RollbackCommand
import com.ak.domain.command.SetCommand
import com.ak.domain.exception.UnknownCommandException
import com.ak.domain.manager.CommandManager
import com.ak.domain.repository.StoreRepository
import javax.inject.Inject

class StoreInteractor @Inject constructor(
    private val commandManager: CommandManager,
    private val storeRepository: StoreRepository
) {

    fun process(commandText: String): Any? {
        return when (val command = commandManager.getCommand(commandText)) {
            is SetCommand -> storeRepository.set(command.arg1, command.arg2)
            is GetCommand -> storeRepository.get(command.arg1)
            is DeleteCommand -> storeRepository.delete(command.arg1)
            is CountCommand -> storeRepository.count(command.arg1)
            is BeginCommand -> storeRepository.begin()
            is CommitCommand -> storeRepository.commit()
            is RollbackCommand -> storeRepository.rollback()
            else -> throw UnknownCommandException()
        }
    }
}
