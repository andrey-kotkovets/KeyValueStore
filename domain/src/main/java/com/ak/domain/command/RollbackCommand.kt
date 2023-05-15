package com.ak.domain.command

import com.ak.domain.model.CommandType
import javax.inject.Inject

class RollbackCommand @Inject constructor() : BaseCommand() {

    override val type = CommandType.Rollback()
}
