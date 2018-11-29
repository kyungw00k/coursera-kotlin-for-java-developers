package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> {
    val uniqueTripDrivers = trips.map {
        it.driver
    }.distinct()

    return allDrivers.filter {
        !uniqueTripDrivers.contains(it)
    }.toSet()
}

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        allPassengers.filter { passenger ->
            trips.filter { trip ->
                trip.passengers.contains(passenger)
            }.size >= minTrips
        }.toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        trips
                .toList()
                .filter { it.driver == driver }
                .map { it.passengers }
                .flatten()
                .groupingBy { it }
                .eachCount()
                .filter { it.value > 1 }
                .map { it.key }
                .toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> {
    fun hasMajorityOfDiscount(passenger: Passenger): Boolean {
        val tripOfPassenger = trips.filter { passenger in it.passengers }
        val noDiscountTrip = tripOfPassenger.count { it.discount == null }

        return tripOfPassenger.isNotEmpty() && (noDiscountTrip / tripOfPassenger.size.toDouble()) < 0.5
    }

    return allPassengers.filter { hasMajorityOfDiscount(it) }.toSet()
}

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    val groupCount =
            trips
                    .map { it.duration }
                    .groupingBy { it / 10 }
                    .eachCount()
                    .toList()
                    .sortedByDescending { it.second }
                    .firstOrNull()

    if (groupCount != null) {
        return IntRange(groupCount.first * 10, groupCount.first * 10 + 9)
    }

    return null
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    val totalIncome = trips.map {
        it.cost
    }.sum()

    fun calculateIncomesByDriver(driver: Driver): Double =
            trips
                    .filter { it.driver == driver }
                    .map { it.cost }
                    .sumByDouble { it }

    val incomesOfDriver =
            trips
                    .map { it.driver }
                    .distinct()
                    .map { calculateIncomesByDriver(it) }
                    .sortedByDescending { it }

    return trips.isNotEmpty() && incomesOfDriver.subList(0, Math.min((allDrivers.size * 0.2).toInt(), incomesOfDriver.size)).sumByDouble { it } >= totalIncome * 0.8
}
