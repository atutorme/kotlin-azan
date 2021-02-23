import java.time.LocalDateTime

fun main() {
    val loc = Location(
        "NOAA Test",
        40.0,
        -105.0,
        0.0,
        -7.0
    )

    val dt = LocalDateTime.of(2010, 1, 1, 0, 0)

    val sunNOAA2 = SunNOAA2(dt, loc)

    println("julianDay: ${sunNOAA2.julianDay}")
    println("julianCentury: ${sunNOAA2.julianCentury}")
    println("geomMeanLongSun: ${sunNOAA2.geomMeanLongSun}")
    println("geomMeanAnomSun: ${sunNOAA2.geomMeanAnomSun}")
    println("eccentEarthOrbit: ${sunNOAA2.eccentEarthOrbit}")
}