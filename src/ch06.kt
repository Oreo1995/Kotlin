import java.lang.IllegalArgumentException
import javax.print.attribute.standard.MediaSize

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

fun ignoreNulls(s: String?) {
    val sNotNull: String = s!!
    p(sNotNull.length)
}

fun sendEmailTo(email: String) {
    p("Sending email to $email")
}

fun getTheBestPersonInTheWorld(): Person7? = null

class MyService {
    fun performAction(): String = "foo"
}

//class MyTest {
//    private var myService: MyService? = null
//
//    @Before
//    fun setUp() {
//        myService = MyService()
//    }
//
//    @Test fun testsAction() {
//        Assert.assertEquals("foo",
//                myService!!.performAction())
//    }
//}

//class MyTest1 {
//    private lateinit var myService: MyService
//
//    @Before
//    fun setUp() {
//        myService = MyService()
//    }
//
//    @Test fun testsAction() {
//        Assert.assertEquals("foo",
//                myService.performAction())
//    }
//}

fun <T> printHashCode(t: T) {           //the parameter can be null
    p(t?.hashCode())
}

fun <T : Any> printHashCode1(t: T) {    //the parameter can not be null,because T is a subtype of Any
    p(t.hashCode())
}

fun verifyUserInput(input: String?) {
    if (input.isNullOrBlank()) {
        p("Please fill in the required fields")
    } else
        p(input?.length)
}


fun yellAt(person: Person8) {        //not handle the java's null
    p(person.name.toUpperCase() + "!!!")
}

fun yellAt1(person: Person8) {        //handle the java's null
    p((person.name ?: "Anyone").toUpperCase() + "!!!")
}

class StringPrinter : StringProcessor {
    override fun process(value: String) {
        p(value)
    }
}

class NullableStringPrinter : StringProcessor {
    override fun process(value: String?) {
        if (value != null) {
            p(value)
        }
    }
}

fun showProgress(progress: Int) {
    val percent = progress.coerceIn(0, 100)
    p("We're $percent% done!")
}

data class Person10(val name: String,
                    val age: Int? = null) {
    fun isOlderThan(other: Person10): Boolean? {

        /***
         * it not work,can not compare Int?
         *
         * if (age >= other.age) {
         *      return true
         *  }
         *
         *  return null
         */


        if (age == null || other.age == null)
            return null
        return age > other.age

    }
}

fun main(args: Array<String>) {
    p(Person10("Sam", 35).isOlderThan(Person10("Amy", 42)))     //false
    p(Person10("Sam", 35).isOlderThan(Person10("Jane")))            //null

//    showProgress(146)
//    yellAt1(Person8(null))      //Anyone
//    yellAt(Person8(null))       //java.lang.IllegalStateException: person.name must not be null

//    printHashCode("WANG")
//    printHashCode1(5)

//    verifyUserInput(" ")
//    verifyUserInput(null)
//    verifyUserInput("")
//    verifyUserInput("12345")

//    val person: Person7? = getTheBestPersonInTheWorld()
//    if (person != null) sendEmailTo(person.email)
//    getTheBestPersonInTheWorld()?.let { sendEmailTo(it.email) }       //lambda will not run,because fun is always null

//    var email: String? = "yole@example.com"
//    sendEmailTo(email)      //Type mismatch
//    email?.let { email -> sendEmailTo(email) }
//    email?.let { sendEmailTo(it) }
//    email = null
//    email?.let { sendEmailTo(it) }


//    ignoreNulls("WSF")
//    ignoreNulls(null)

//    val p1 = Person7("Dmitry", "Jemerov")
//    val p2 = Person7("Dmitry", "Jemerov")
//    p(p1 == p2)
//    p(p1.equals(42))

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