import java.lang.IllegalArgumentException

fun strLen(s: String) = s.length
fun strLen1(s: String?) = s?.length
fun strLenSafe(s: String?): Int =
//        if (s != null) s.length else 0
        s?.length ?: 0

fun printAllCaps(s: String?) {
    val allCaps: String? = s?.toUpperCase()
    p(allCaps)
}

fun foo(s: String?) {
    val t: String = s ?: ""
}

class Employee(val name: String, val manager: Employee?)

fun managerName(employee: Employee): String? = employee.manager?.name

class Address(val streetAddress: String, val zipCode: Int,
              val city: String, val country: String)

class Company(val name: String, val address: Address?)

class Person(val name: String, val company: Company?)

fun Person.countryName(): String {
    val country = this.company?.address?.country
    return if (country != null) country else "Unknown"
}

fun Person.countryName2(): String =
        company?.address?.country ?: "Unknown"

fun printShippingLabel(person: Person) {
    val address = person.company?.address
            ?: throw IllegalArgumentException("No address")
    with(address) {
        p(streetAddress)
        p("$zipCode $city, $country")
    }
}

class Person7(val firstName: String, val lastName: String) {
    override fun equals(o: Any?): Boolean {
        val otherPerson = o as? Person7 ?: return false
        return otherPerson.firstName == firstName &&
                otherPerson.lastName == lastName
    }

    override fun hashCode(): Int =
            firstName.hashCode() * 37 + lastName.hashCode() * 37
}

fun main(args: Array<String>) {
    val p1 = Person7("Dmitry", "Jemerov")
    val p2 = Person7("Dmitry", "Jemerov")

    p(p1 == p2)
    p(p1.equals(42))

//    val address = Address("Elsestr. 47",90687,"Munich","Germany")
//    val address:Address? = null
//    val jetbrains = Company("JetBrains", address)
//    val person = Person("Dmitry", jetbrains)
//    printShippingLabel(person)

//    val person = Person("Dmitry", null)
//    p(person.countryName())
//    p(person.countryName2())

//    val ceo = Employee("Da Boss", null)
//    val developer = Employee("Bob Smith", ceo)
//    p(managerName(developer))
//    p(managerName(ceo))

//    printAllCaps("abc")
//    printAllCaps(null)

//    val x: String? = null
//    p(strLenSafe(x))
//    p(strLenSafe("abc"))
//    var y:String = x
//    p(strLen1(x))
//    strLen(x)
//    strLen1(null)
//    strLen(null)
}