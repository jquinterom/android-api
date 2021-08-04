package co.com.ceiba.mobile.pruebadeingreso.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.util.Log
import co.com.ceiba.mobile.pruebadeingreso.models.Post
import co.com.ceiba.mobile.pruebadeingreso.models.User
import java.lang.Exception

class dbManager{
    constructor()
    constructor(context: Context){
        val helper = dbHelper(context)
        db = helper.writableDatabase
        db = helper.readableDatabase
    }

    private var db: SQLiteDatabase? = null
    private var values: ContentValues? = null
    private val response: String? = null
    private val ALL_COLUMNS = arrayOf("*")


    object TABLES {
        // Tabla de usuarios
        object USERS : BaseColumns {
            const val TABLE_NAME = "users"
            const val COLUMN_NAME_ID = "id"
            const val COLUMN_NAME_NAME = "name"
            const val COLUMN_NAME_EMAIL = "email"
            const val COLUMN_NAME_PHONE = "phone"
            const val COLUMN_NAME_WEBSITE = "website"
        }

        // Tabla de posts
        object POSTS : BaseColumns {
            const val TABLE_NAME = "posts"
            const val COLUMN_NAME_ID = "id"
            const val COLUMN_NAME_USER_ID = "userId"
            const val COLUMN_NAME_TITLE = "title"
            const val COLUMN_NAME_BODY = "body"
        }
    }



    // region definicion de tablas
    val CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS ${TABLES.USERS.TABLE_NAME}" +
            "(${TABLES.USERS.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
            "${TABLES.USERS.COLUMN_NAME_NAME} TEXT NOT NULL," +
            "${TABLES.USERS.COLUMN_NAME_EMAIL} TEXT NOT NULL," +
            "${TABLES.USERS.COLUMN_NAME_PHONE} TEXT NOT NULL," +
            "${TABLES.USERS.COLUMN_NAME_WEBSITE} TEXT NOT NULL)"

    val CREATE_TABLE_POSTS = "CREATE TABLE IF NOT EXISTS ${TABLES.POSTS.TABLE_NAME}" +
            "(${TABLES.POSTS.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
            "${TABLES.POSTS.COLUMN_NAME_USER_ID} INTEGER NOT NULL," +
            "${TABLES.POSTS.COLUMN_NAME_TITLE} TEXT NOT NULL," +
            "${TABLES.POSTS.COLUMN_NAME_BODY} TEXT NOT NULL)"
    // endregion

    // region consultas

    // Proceso para registrar usuario en la base de datos
    open fun registerUser(user: User): Boolean {
        var response = true
        try {
            // crear a nuevo mapa de valores con llaves y valores
            val values = ContentValues().apply {
                put(TABLES.USERS.COLUMN_NAME_ID, user.id)
                put(TABLES.USERS.COLUMN_NAME_NAME, user.name)
                put(TABLES.USERS.COLUMN_NAME_EMAIL, user.email)
                put(TABLES.USERS.COLUMN_NAME_PHONE, user.phone)
                put(TABLES.USERS.COLUMN_NAME_WEBSITE, user.website)
            }
            db!!.insert(TABLES.USERS.TABLE_NAME, null, values)
        } catch (e: Exception) {
            e.printStackTrace()
            response = false
        }
        return response
    }


    // Proceso para registrar post en la base de datos
    open fun registerPost(post: Post): Boolean {
        var response = true
        try {
            // crear a nuevo mapa de valores con llaves y valores
            val values = ContentValues().apply {
                put(TABLES.POSTS.COLUMN_NAME_ID, post.id)
                put(TABLES.POSTS.COLUMN_NAME_USER_ID, post.userId)
                put(TABLES.POSTS.COLUMN_NAME_TITLE, post.title)
                put(TABLES.POSTS.COLUMN_NAME_BODY, post.body)
            }
            db!!.insert(TABLES.POSTS.TABLE_NAME, null, values)
        } catch (e: Exception) {
            e.printStackTrace()
            response = false
        }
        return response
    }

    // Recuperar los usuarios
    fun getUsers(): Array<User> {
        // colmuns
        val projection = arrayOf(TABLES.USERS.COLUMN_NAME_ID, TABLES.USERS.COLUMN_NAME_NAME,
            TABLES.USERS.COLUMN_NAME_EMAIL, TABLES.USERS.COLUMN_NAME_PHONE, TABLES.USERS.COLUMN_NAME_WEBSITE)
        var response : MutableList<User>? = null
        try {
            val cursor = db?.query(
                TABLES.USERS.TABLE_NAME,   // Tabla a consultar
                projection,             // Columnas
                null,
                null,
                null,
                null,
                null
            )
            val itemIds = mutableListOf<Long>()
            with(cursor) {
                while (this!!.moveToNext()) {
                    Log.d("user", cursor.toString())
                    val itemId = getLong(getColumnIndexOrThrow(TABLES.USERS.COLUMN_NAME_ID))
                    itemIds.add(itemId)
                }
            }
            // cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response?.toTypedArray() ?: emptyArray()
    }



    // Recuperar los posts or usuario
    fun getPosts(userId: Int): Array<Post> {
        // colmuns
        val projection = arrayOf(TABLES.POSTS.COLUMN_NAME_ID, TABLES.POSTS.COLUMN_NAME_USER_ID,
            TABLES.POSTS.COLUMN_NAME_TITLE, TABLES.POSTS.COLUMN_NAME_BODY)
        val selection = "${TABLES.POSTS.COLUMN_NAME_USER_ID} = ?"
        var response : MutableList<Post>? = null

        try {
            val cursor = db?.query(
                TABLES.POSTS.TABLE_NAME,   // Tabla a consultar
                projection,             // Columnas
                selection,
                arrayOf(userId.toString()),
                null,
                null,
                null
            )
            val itemIds = mutableListOf<Long>()
            with(cursor) {
                while (this!!.moveToNext()) {
                    Log.d("post", cursor.toString())
                    val itemId = getLong(getColumnIndexOrThrow(TABLES.USERS.COLUMN_NAME_ID))
                    itemIds.add(itemId)
                }
            }
            // cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response!!.toTypedArray()
    }




    //endregion

}