import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SalatTimesUnitTesting {
    @Test
    fun testNextSalat() {
        val allSalatDateTimes = SalatTimes().salatDateTimes

        allSalatDateTimes.forEach(::println)

        var i = 0
        val salatNiceNames = SalatNames.values().map { it.niceName }

        allSalatDateTimes.forEach {
            val localDateTimeProvider = LocalDateTimeProvider { it.value.minusMinutes(1) }
            val nextSalat = SalatTimes(localDateTimeProvider).nextSalatTime(localDateTimeProvider)
            Assertions.assertEquals(salatNiceNames[i], nextSalat.first.removeYesterdays())
            i++
            i %= salatNiceNames.size
        }

    }

}

fun String.removeYesterdays() = this.removePrefix("Yesterday's ")