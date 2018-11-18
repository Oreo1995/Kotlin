import java.io.BufferedReader
import java.io.File
import java.io.LineNumberReader
import java.io.StringReader
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException
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

fun foo1(l: Long) = p(l)

interface Processor1<T> {
    fun process(): T
}

class NoResultProcessor : Processor1<Unit> {
    override fun process() {
        // do stuff
    }
}

class ResultProcess : Processor1<Int> {
    override fun process(): Int {
        val a = 5;
        val c = a * 3;
        return c;
    }
}

fun fail(message: String): Nothing {
    throw IllegalArgumentException(message)
}

fun readNumbers(reader: BufferedReader): List<Int?> {
    val result = ArrayList<Int?>()
    for (line in reader.lineSequence()) {
        try {
            val number = line.toInt()
            result.add(number)
        } catch (e: NumberFormatException) {
            result.add(null)
        }
    }
    return result
}

fun addValidNumbers(numbers: List<Int?>) {
    var sumOfValidNumbers = 0
    var invalidNumbers = 0
    for (number in numbers) {
        if (number != null) {
            sumOfValidNumbers += number
        } else {
            invalidNumbers++
        }
    }
    p("Sum of valid numbers: $sumOfValidNumbers")
    p("Invalid numbers: $invalidNumbers")
}

fun addValidNumbers1(numbers: List<Int?>) {
    val validNumbers = numbers.filterNotNull()
    p("Sum of valid numbers: ${validNumbers.sum()}")
    p("Invalid numbers: ${numbers.size - validNumbers.size}")
}

fun <T> copyElements(source: Collection<T>,
                     target: MutableCollection<T>) {
    for (item in source) {
        target.add(item)
    }
}

fun printInUppercase(list: List<String>) {      //define the parameter as only read
    p(CollectionUtils.uppercaseAll(list))
    p(list.first())
}

//write in kotlin of the interface FileContentProcessor.java
//the list is nullable,because some file is binary file,their content cannot display in text
//list's value cannot be null,because the line of file not null forever
//the list is read only,because the value of list is file's content that cannot be update
class FileIndexer : FileContentProcessor {
    override fun processContents(path: File, binaryContents: ByteArray?, textContents: List<String>?) {
        //.....
    }
}

class PersonParser : DataParser<Person10> {
    override fun parseData(input: String,
                           output: MutableList<Person10>,
                           errors: MutableList<String?>) {
        //...
    }
}

fun arrayDemo(args: Array<String>) {
    for (i in args.indices) {
        p("Argument $i is: ${args[i]}")
    }
}

fun arrayDemo1(args: Array<String>) {
    args.forEachIndexed{ index,element->
        p("Arguments $index is: $element")
    }
}

fun main(args: Array<String>) {
    val fiveZeros = IntArray(5)     //Factory function init,their value is 0
    val fiveZerosToo = intArrayOf(0, 0, 0, 0, 0)

    val squares = IntArray(5) { i -> (i + 1) * (i + 1) }        //lambda init
    p(squares.joinToString())

//    val strings = listOf("a", "b", "c")
//    p("%s/%s/%s".format(*strings.toTypedArray()))

//    val letters = Array<String>(26){i->('a'+i).toString()}
//    p(letters.joinToString ("" ))
//    arrayDemo(letters)

//    val list = listOf("a","b","c")
//    printInUppercase(list)

//    val source:Collection<Int> = arrayListOf(3,5,7)
//    val target:MutableCollection<Int> = arrayListOf(1)
//    copyElements(source,target)     //target can not be instead by source,even if its value can be update
//    p(target)

//    val reader = BufferedReader(StringReader("123456\ns424dfj\n13254"))
////    p(readNumbers(reader).toString())
//
//    val numbers = readNumbers(reader)
////    addValidNumbers(numbers)
//    addValidNumbers1(numbers)

//    fail("Error occurred")
//    val company = Company("Anly",
//            Address("403", 572000, "HongKong", "China"))
//    val company1 = Company("Anly", null)
//    val company2:Company? = null
//
//    val address = company2?.address ?: fail("No address")
//    p(address.city)
//    val result = ResultProcess()
//    p(result.process())

//    val b: Byte = 1
//    val l = b + 1L
//    foo1(42)
//    p(l)

//    val i = 1
////    val l:Long = i
//    val l:Long = i.toLong()     //must explicitly cast the type
//
//    val x = 1
//    val list = listOf(1L,2L,3L)
////    x in list
//    p(x.toLong() in list)   //must explicitly cast the type

//    p(Person10("Sam", 35).isOlderThan(Person10("Amy", 42)))     //false
//    p(Person10("Sam", 35).isOlderThan(Person10("Jane")))            //null

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