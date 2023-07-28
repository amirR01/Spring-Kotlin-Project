package com.example.learningexampleproject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.ComponentScan
import java.util.*

@SpringBootApplication
class LearningExampleProjectApplication

fun main(args: Array<String>) {
    runApplication<LearningExampleProjectApplication>(*args)
//    var context : ConfigurableApplicationContext =
//    Arrays.stream(context.beanDefinitionNames).forEach(System.out::println)

}
