import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.time.temporal.JulianFields
import kotlin.math.*

class Sun(val date: LocalDate) {
    companion object {
        val epoch = LocalDate.of(2000, 1, 1)
    }

    val daysSinceJ2000 = ChronoUnit.DAYS.between(epoch, date)

    val meanAnomaly = (357.529 + 0.98560028 * daysSinceJ2000) % 360.0
    val meanLongitude = (280.459 + 0.98564736 * daysSinceJ2000) % 360.0
    val eclipticLongitude = (meanLongitude + 1.915 * sin(meanAnomaly.radians()) + 0.020 * sin(2 * meanAnomaly.radians())) % 360.0

    val sunToEarthDistanceAstronomicalUnits = 1.00014 - 0.01671 * cos(meanAnomaly.radians()) - 0.00014 * cos(2 * meanAnomaly.radians())
    val obliquityOfEcliptic = 23.439 - 0.00000036 * daysSinceJ2000
    val rightAscension = (atan2(cos(obliquityOfEcliptic.radians()) * sin(eclipticLongitude.radians()), cos(eclipticLongitude.radians())).degrees() + 360.0) / 15.0

    val declination = asin(sin(obliquityOfEcliptic.radians()) * sin(eclipticLongitude.radians())).degrees()
    val equationOfTime = (meanLongitude / 15.0 - rightAscension) * 60.0 // minutes
}