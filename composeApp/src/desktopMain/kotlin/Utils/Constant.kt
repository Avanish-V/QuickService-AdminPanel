package utils

object Constant {
    const val BASE_URL = "http://127.0.0.1:8080"
}

data class MostBookedDTO(
    val productTitle:String,
    val category:String,
    val productId:String,
    val price:Int,
    val booked:Int
)

val mostBookedList = listOf<MostBookedDTO>(
    MostBookedDTO(
        productTitle = "AC repair",
        category = "AC",
        productId = "AC01",
        price = 299,
        booked = 1000
    ),MostBookedDTO(
        productTitle = "AC fill cleaning service",
        category = "AC",
        productId = "AC02",
        price = 230,
        booked = 650
    ),MostBookedDTO(
        productTitle = "Refrigerator repair",
        category = "Refrigerator",
        productId = "Refrigerator01",
        price = 799,
        booked = 200
    )
)