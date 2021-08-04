package co.com.ceiba.mobile.pruebadeingreso.models

class User {
    data class UserInfo(
        val id : Int,
        val name : String,
        val email : String,
        val phone : String,
        val website : String,
    )
}