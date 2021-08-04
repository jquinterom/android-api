package co.com.ceiba.mobile.pruebadeingreso.models

class Post {

    data class PostInfo(
        val userId: Int,
        val id : Int,
        val title : String,
        val body : String
    )
}
