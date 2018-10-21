interface Clickable {
    fun click()
    fun showOff() = println("I'm clickable")
}

interface Focusable {
    fun setFocus(b: Boolean) =
            println("I ${if (b) "got" else "lost"} focus")

    fun showOff() = println("I'm focusable")
}

class Button : Clickable, Focusable {
    override fun click() {
        println("I was clicked")
    }

    override fun showOff() {
        super<Clickable>.showOff()
        super<Focusable>.showOff()
    }
}

open class RichButton : Clickable {         //open can inherit
    fun disable() {}                         //final,can't be override
    open fun animate() {}                   //open,can be override
    //    override fun click() {}                 //open,can be override
    final override fun click() {}           //not delete final,because it is open that not have final
                                                //final,can't be override
}

abstract class Animated{            //this class is abstract,can not instance it
    abstract fun animate()          //abstract class' abstract fun is open default,and it must be override
    open fun stopAnimating(){}      //abstract class's abstractness fun is not default open
    fun animateTwice(){}
}

fun main(args: Array<String>) {

//    object:Clickable{
//        override fun click() {
//            println("Hello World")
//        }
//    }.click()

    val button = Button()
    button.click()
    button.setFocus(true)
    button.showOff()
}