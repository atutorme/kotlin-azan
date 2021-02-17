import java.time.LocalDate
import java.time.temporal.JulianFields
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

object Sun {
    val localDate = LocalDate.now()
    val julianDate = JulianFields.JULIAN_DAY.getFrom(localDate)
    val daysSinceJ2000 = julianDate - 2451545.0

    val meanAnomaly = 357.529 + 0.98560028 * daysSinceJ2000
    val meanLongitude = 280.459 + 0.98564736 * daysSinceJ2000
    val eclipticLongitude = meanLongitude + 1.915 * sin(meanAnomaly) + 0.020 * sin(2 * meanAnomaly)

    val sunToEarthDistanceAstronomicalUnits = 1.00014 - 0.01671 * cos(meanAnomaly) - 0.00014 * cos(2 * meanAnomaly)
    val obliquityOfEcliptic = 23.439 - 0.00000036 * daysSinceJ2000
    val rightAscension = atan2(cos(obliquityOfEcliptic) * sin(eclipticLongitude), cos(eclipticLongitude)) / 15

    val declination = asin(sin(obliquityOfEcliptic) * sin(eclipticLongitude))
    val equationOfTime = meanLongitude / 15 - rightAscension
}