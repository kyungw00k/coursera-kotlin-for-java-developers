package rationals

import java.math.BigInteger

private fun gcd(a: BigInteger, b: BigInteger): BigInteger = if (b == 0.toBigInteger()) a else gcd(b, a % b)

class RationalRange(val start: Rational, val end: Rational) {
    operator fun contains(other: Rational): Boolean {
        return other >= start && other <= end
    }
}

class Rational(numerator: BigInteger, denominator: BigInteger) {
    private val factor = gcd(numerator.abs(), denominator.abs())

    val sign = if (numerator * denominator < 0.toBigInteger()) {
        -1
    } else {
        1
    }

    val n = if (factor.compareTo(BigInteger.ONE) > 0) {
        (numerator / factor).abs() * sign.toBigInteger()
    } else {
        numerator.abs() * sign.toBigInteger()
    }

    val d = if (factor.compareTo(BigInteger.ONE) > 0) {
        (denominator / factor).abs()
    } else {
        denominator.abs()
    }

    operator fun plus(other: Rational): Rational {
        return Rational((n * other.d) + (other.n * d), d * other.d)
    }

    operator fun minus(other: Rational): Rational {
        return Rational((n * other.d) - (other.n * d), d * other.d)
    }

    operator fun times(other: Rational): Rational {
        return Rational(n * other.n, d * other.d)
    }

    operator fun div(other: Rational): Rational {
        return Rational(n * other.d, d * other.n)
    }

    operator fun unaryMinus(): Rational {
        return Rational(-n, d)
    }

    operator fun compareTo(other: Rational): Int {
        return (n * other.d).compareTo((other.n * d))
    }

    override fun equals(other: Any?): Boolean {
        if (other is Rational) {
            return n.equals(other.n) && d.equals(other.d) && sign == other.sign
        }
        return false
    }

    override fun toString(): String {
        var output = "${n}"

        if (d.equals(BigInteger.ONE) == false) {
            output += "/${d.abs()}"
        }

        return output
    }

    operator fun rangeTo(other: Rational): RationalRange {
        return RationalRange(this, other)
    }
}

infix fun Int.divBy(other: Int): Rational {
    return Rational(this.toBigInteger(), other.toBigInteger())
}

infix fun Long.divBy(other: Long): Rational {
    return Rational(this.toBigInteger(), other.toBigInteger())
}

infix fun BigInteger.divBy(other: BigInteger): Rational {
    return Rational(this, other)
}

fun String.toRational(): Rational {
    val arr = if (this.indexOf('/') > -1) {
        this.split('/').map { it -> it.toBigInteger() }
    } else {
        listOf(this, "1").map { it -> it.toBigInteger() }
    }

    return Rational(arr[0], arr[1])
}

fun main(args: Array<String>) {
    val r1 = 1 divBy 2
    val r2 = 2000000000L divBy 4000000000L
    println(r1 == r2)

    println((2 divBy 1).toString() == "2")

    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    println("1/2".toRational() - "1/3".toRational() == "1/6".toRational())
    println("1/2".toRational() + "1/3".toRational() == "5/6".toRational())

    println(-(1 divBy 2) == (-1 divBy 2))

    println((1 divBy 2) * (1 divBy 3) == "1/6".toRational())
    println((1 divBy 2) / (1 divBy 4) == "2".toRational())

    println((1 divBy 2) < (2 divBy 3))
    println((1 divBy 2) in (1 divBy 3)..(2 divBy 3))

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}