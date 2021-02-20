import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * This implementation of Sun calculations is taken from the document titled:
 * "General Solar Position Calculations" produced by NOAA Global Monitoring Division
 * https://www.esrl.noaa.gov/gmd/grad/solcalc/solareqns.PDF
 * Retrieved on 2021-02-19
 */
class SunNOAA(val dateTime: LocalDateTime) {
    companion object {
        val gregorianCalendar = GregorianCalendar()
    }

    fun Int.isLeapYear() = gregorianCalendar.isLeapYear(this)

    val numDaysInYear = if (dateTime.year.isLeapYear()) 366 else 365

    val dayOfYear = dateTime.dayOfYear

    val hour = dateTime.hour

    val fractionalYear = 2.0 * PI / numDaysInYear * (dayOfYear - 1.0 + (hour - 12.0) / 24.0)

    val eqTime = 229.18 * (0.000075 + 0.001868 * cos(fractionalYear) - 0.032077 * sin(fractionalYear) -
            0.014615 * cos(2 * fractionalYear) - 0.040849 * sin(2 * fractionalYear))

    val decl = 0.006918 - 0.399912 * cos(fractionalYear) + 0.070257 * sin(fractionalYear) -
            0.006758 * cos(2 * fractionalYear) + 0.000907 * sin(2 * fractionalYear) -
            0.002697 * cos(3 * fractionalYear) + 0.00148 * sin (3 * fractionalYear)

}

