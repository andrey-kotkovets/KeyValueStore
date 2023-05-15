package com.ak.domain.command

import com.ak.domain.model.CommandType
import javax.inject.Inject

class SetCommand @Inject constructor() : BaseCommand() {

    override val type = CommandType.Set()
}
