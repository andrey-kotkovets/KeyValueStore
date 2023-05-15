package com.ak.domain.di

import com.ak.domain.command.BaseCommand
import com.ak.domain.command.BeginCommand
import com.ak.domain.command.CommitCommand
import com.ak.domain.command.CountCommand
import com.ak.domain.command.DeleteCommand
import com.ak.domain.command.GetCommand
import com.ak.domain.command.RollbackCommand
import com.ak.domain.command.SetCommand
import com.ak.domain.manager.CommandManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Singleton
    @Provides
    fun provideCommands(
        setCommand: SetCommand,
        getCommand: GetCommand,
        deleteCommand: DeleteCommand,
        countCommand: CountCommand,
        beginCommand: BeginCommand,
        commitCommand: CommitCommand,
        rollbackCommand: RollbackCommand
    ): Set<@JvmSuppressWildcards BaseCommand> {
        return linkedSetOf(
            setCommand,
            getCommand,
            deleteCommand,
            countCommand,
            beginCommand,
            commitCommand,
            rollbackCommand
        )
    }

    @Provides
    @Singleton
    fun provideCommandManager(commands: Set<@JvmSuppressWildcards BaseCommand>): CommandManager {
        return CommandManager(commands)
    }
}
