import java.time.LocalDate
import java.time.temporal.JulianFields

object Sun {
    val localDate = LocalDate.now()
    val julianDate = JulianFields.JULIAN_DAY.getFrom(localDate)
}