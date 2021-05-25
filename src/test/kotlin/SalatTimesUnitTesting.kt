import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test



class SalatTimesUnitTesting {

    @Test
    fun testSalatTimes() {

        val salatNiceNames: List<String> = SalatNames.values().map { it.niceName }

        val locations: List<Location> = listOf(
            Location(), // Makkah
            Location("Islamabad", 33.6938118, 73.0651511, 532.0, 5.0),
            Location("Sydney", -33.8548157, 151.2164539, 7.0, 10.0),
            Location("New York", 40.741895, -73.989308, 13.0, -4.0),
            Location("Buenos Aires", -34.6075682, -58.4370894, 25.0, -3.0)
        )

        val allSalatDateTimes = locations.map { SalatTimes(location = it) }

        allSalatDateTimes.forEach {
            val sNames = it.salatDateTimes
                .toList()
                .sortedBy { s -> s.second }
                .map { s -> s.first }

            println(it.location.name)
            println(sNames)

            assertIterableEquals(salatNiceNames, sNames)
        }

    }

}

fun String.removeYesterdays() = this.removePrefix("Yesterday's ")