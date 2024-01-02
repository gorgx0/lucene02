package homelab

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File

fun main() {
    val offers = offers2("test_data.json")
    offers.forEach { println(it) }
}

fun offers2(fileName: String): List<RealEstateOffer> {
    return File(fileName).readText().let {
        offers(it)
    }
}
private fun offers(it: String) = Json.decodeFromString<Offers>(it).offers

@Serializable
data class Offers(
    @SerialName("real_estate_offers")
    val offers: List<RealEstateOffer>
)

@Serializable
data class RealEstateOffer(
    @SerialName("property_id")
    val propertyId: String,
    val type: String,
    val location: String,
    val price: String,
    val bedrooms: Int,
    val bathrooms: Double,
    @SerialName("area_sqft")
    val areaSqft: Int,
    val description: String,
    @SerialName("image_url")
    val imageUrl: String
)

