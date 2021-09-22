package com.deeps.Repository

import com.deeps.data.userTable
import com.deeps.data.users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement
import com.deeps.model.dao.userDao as userDao

class userRepository : userDao {


    override suspend fun insert(userId: Int, name: String, age: Int): users? {
        var statement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            statement = userTable.insert { user ->
                user[userTable.userID] = userId
                user[userTable.name] = name
                user[userTable.age] = age
            }
        }
        return rowToResult(statement?.resultedValues?.get(0))
    }

    override suspend fun findUserById(userId: Int): users? =
        DatabaseFactory.dbQuery {
            userTable.select { userTable.userID.eq(userId) }
                .map { rowToResult(it) }
                .singleOrNull()
        }

    override suspend fun deleteUser(userId: Int): Int?=
        DatabaseFactory.dbQuery {
            userTable.deleteWhere { userTable.userID.eq(userId) }
        }

    override suspend fun getAllUser(): List<users> =
        DatabaseFactory.dbQuery {
            userTable.selectAll()
                .mapNotNull { rowToResult(it) }
        }


    override suspend fun updateAllData(userId: Int, name: String, age: Int): Int? =
        DatabaseFactory.dbQuery {
            userTable.update({ userTable.userID.eq(userId) }) { user ->
                user[userTable.name] = name
                user[userTable.age] = age
            }
        }

    override suspend fun updateAnyData(id: Int, name: String, age: Int): Int? =
        DatabaseFactory.dbQuery {
            userTable.update({ userTable.userID.eq(id) }) { user ->
            user[userTable.name] = name
            user[userTable.age] = age

        }
    }

    private fun rowToResult(row: ResultRow?): users? {
        if (row == null)
            return null
        return users(
            userId = row[userTable.userID],
            name = row[userTable.name],
            age = row[userTable.age],

        )
    }



}