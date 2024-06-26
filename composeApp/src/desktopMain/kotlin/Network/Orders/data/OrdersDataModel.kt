package Network.Orders.data

// Models.kt

import kotlinx.serialization.Serializable

@Serializable
data class OrdersDataModel(
    val orderId: String? = null,
    val userUUID: String,
    val serviceInfo: ServiceInfo,
    val priceTag: PriceDetails,
    val address: UserAddressDataModel,
    val dateTime: DateTime,
    var arrivalTime: Status,
    val professionalID: String = "",
    val professionalDetail: ProfessionalDetails? = null
)

@Serializable
data class ServiceInfo(
    val serviceTitle: String = "",
    val serviceId: String = ""
)

@Serializable
data class DateTime(
    val date: String = "",
    val time: String = ""
)

@Serializable
enum class Status {
    ACTIVE, CANCELED, REFUNDED, ASSIGNED, COMPLETED
}

@Serializable
data class ProfessionalDetails(
    val professionalName: String = "",
    val professionalImage: String = "",
    val rating: Int = 0,
    val count: Int = 0
)

@Serializable
data class UserAddressDataModel(
    val name: String = "",
    val mobile: String = "",
    val pinCode: String = "",
    val state: String = "",
    val city: String = "",
    val building: String = "",
    val area: String = "",
    val type: String = "",
    val addressId: String = ""
)

@Serializable
data class PriceDetails(
    val price: Int = 0,
    val tax: Int = 0,
    val quantity: Int = 0,
    val total: Int = 0,
    val coupon: Coupon? = null
)

@Serializable
data class Coupon(
    val offerTitle: String? = null,
    val couponCode: String? = null,
    val discount: Int? = null
)

