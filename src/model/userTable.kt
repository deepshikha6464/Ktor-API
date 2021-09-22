package com.deeps.data

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object userTable: Table() {

    val userID:Column<Int> = integer("userID").autoIncrement()
    val name:Column<String> = varchar("name",520)
    val age:Column<Int> = integer("age")

    override val primaryKey: PrimaryKey = PrimaryKey(userID)

}