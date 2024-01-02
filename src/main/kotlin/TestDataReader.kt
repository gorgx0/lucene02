package homelab

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File

class TestDataReader {
    fun offers(fileName: String): List<RealEstateOffer> {
        return File(fileName).readText().let {
            Json.decodeFromString<Offers>(it).offers
        }
    }
}

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

