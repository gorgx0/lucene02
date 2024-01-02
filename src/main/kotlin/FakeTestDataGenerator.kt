package homelab

import io.github.serpro69.kfaker.Faker

class FakeTestDataGenerator {

    val faker = Faker()

    fun main() {
        generateOffers(10).forEach { println(it) }
    }

    fun generateOffers(count: Int): List<Offer> {
        return (0..count).map { buildOffer(it) }
    }
    fun buildOffer(id: Int) = Offer(
        id,
        faker.name.name(),
        faker.address.streetAddress(),
        faker.address.city(),
        faker.random.nextInt(1, 5),
        faker.random.nextDouble()*10000+3000,
    )

    data class Offer(
        val id: Int,
        val owner: String,
        val address: String,
        val city: String,
        val numberOfRooms: Int,
        val price: Double,
    )
}