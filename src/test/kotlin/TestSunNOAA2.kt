import java.time.LocalDateTime

fun main() {
    val loc = Location(
        "NOAA Test",
        40.0,
        -105.0,
        1000.0,
        -7.0
    )

    val dt = LocalDateTime.of(2010, 1, 1, 12, 0)

    val sunNOAA2 = SunNOAA2(dt, loc)

    println("julianDay: ${sunNOAA2.julianDay}")
    println("julianCentury: ${sunNOAA2.julianCentury}")
    println("geomMeanLongSun: ${sunNOAA2.geomMeanLongSun}")
    println("geomMeanAnomSun: ${sunNOAA2.geomMeanAnomSun}")
    println("eccentEarthOrbit: ${sunNOAA2.eccentEarthOrbit}")
    println("eccentEarthOrbit: ${sunNOAA2.eccentEarthOrbit}")
    println("sunEqOfCtr: ${sunNOAA2.sunEqOfCtr}")
    println("sunTrueLong: ${sunNOAA2.sunTrueLong}")
    println("sunTrueAnom: ${sunNOAA2.sunTrueAnom}")
    println("sunRadVector: ${sunNOAA2.sunRadVector}")
    println("sunAppLong: ${sunNOAA2.sunAppLong}")
    println("meanObliqEcliptic: ${sunNOAA2.meanObliqEcliptic}")
    println("obliqCorr: ${sunNOAA2.obliqCorr}")
    println("sunRtAscen: ${sunNOAA2.sunRtAscen}")
    println("sunDeclin: ${sunNOAA2.sunDeclin}")
    println("varY: ${sunNOAA2.varY}")
    println("eqOfTime: ${sunNOAA2.eqOfTime}")
    println("haSunrise: ${sunNOAA2.haSunrise}")
    println("haSunriseAltitudeCorrected: ${sunNOAA2.haSunriseAltitudeCorrected}")
    println("solarNoon: ${sunNOAA2.solarNoon.toHMS()}")
    println("sunriseTime: ${sunNOAA2.sunriseTime.toHMS()}")
    println("sunriseTimeAltitudeCorrected: ${sunNOAA2.sunriseTimeAltitudeCorrected.toHMS()}")
    println("sunsetTime: ${sunNOAA2.sunsetTime.toHMS()}")
    println("sunsetTimeAltitudeCorrected: ${sunNOAA2.sunsetTimeAltitudeCorrected.toHMS()}")
    println("sunlightDuration: ${sunNOAA2.sunlightDuration}")
    println("sunlightDurationAltitudeCorrected: ${sunNOAA2.sunlightDurationAltitudeCorrected}")
    println("trueSolarTime: ${sunNOAA2.trueSolarTime}")
    println("hourAngle: ${sunNOAA2.hourAngle}")
    println("solarZenithAngle: ${sunNOAA2.solarZenithAngle}")
    println("solarElevationAngle: ${sunNOAA2.solarElevationAngle}")
    println("approxAtmosphericRefraction: ${sunNOAA2.approxAtmosphericRefraction}")
    println("solarElevationCorrectedForAtmRefraction: ${sunNOAA2.solarElevationCorrectedForAtmRefraction}")
    println("solarAzimuthAngle: ${sunNOAA2.solarAzimuthAngle}")
}