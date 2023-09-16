package cn.jailedbird.doc.design

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.Objects

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
    val a = A::class.java
    val dynamic =
        Proxy.newProxyInstance(
            A::class.java.classLoader,
            listOf(A::class.java).toTypedArray(),
            object : InvocationHandler { // TODO SAM
                override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
                    if (proxy == null || method == null) {
                        return Unit
                    }
                    // Exclude Object
                    if (method.declaringClass == Object::class.java) {
                        return method.invoke(proxy, args)
                    }
                    // TODO other exclude as your requirement;

                    println("before")
                    val result = method.invoke(proxy, args)
                    println("after")
                    return result
                }

            })

}

// 在接口不变的情况下，通过代理Proxy对原有功能进行拓展； 拓展比较首先，无法新增；但是完全可以实现类似hook的功能；包括插入代码
interface A {
    fun print()
}

class Subject : A {
    override fun print() {
        print("Subject")
    }
}

class Proxy1(private val subject: A) : A {
    override fun print() {
        print("Proxy before inject")
        subject.print()
        print("Proxy before inject")
    }

}



