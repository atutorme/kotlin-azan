import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

val salatNiceNames: List<String> = SalatNames.values().map { it.niceName }

val locations: List<Location> = listOf(
    Location(), // Makkah
    Location("Islamabad", 33.6938118, 73.0651511, 532.0, 5.0),
    Location("Sydney", -33.8548157, 151.2164539, 7.0, 10.0),
    Location("New York", 40.741895, -73.989308, 13.0, -4.0),
    Location("Buenos Aires", -34.6075682, -58.4370894, 25.0, -3.0)
)

val allSalatTimes = locations.map { SalatTimes(location = it) }

fun getSalatSalatNames(locationName: String) : List<String> =
    allSalatTimes.first { it.location.name == locationName }
        .salatDateTimes
        .toList()
        .sortedBy { s -> s.second }
        .map { s -> s.first }

class SalatTimesUnitTesting {

    @Test
    fun testSalatTimesMakkah() {
        allSalatTimes.forEach { println("${it.location.name}\n ${it.salatTimestamps}\n") }
        assertIterableEquals(salatNiceNames, getSalatSalatNames("Makkah"))
    }

    @Test
    fun testSalatTimesIslamabad() {
        assertIterableEquals(salatNiceNames, getSalatSalatNames("Islamabad"))
    }

    @Test
    fun testSalatTimesSydney() {
        assertIterableEquals(salatNiceNames, getSalatSalatNames("Sydney"))
    }

    @Test
    fun testSalatTimesNewYork() {
        assertIterableEquals(salatNiceNames, getSalatSalatNames("New York"))
    }

    @Test
    fun testSalatTimesBuenosAires() {
        assertIterableEquals(salatNiceNames, getSalatSalatNames("Buenos Aires"))
    }
}