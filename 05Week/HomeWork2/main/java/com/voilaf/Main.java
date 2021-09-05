package com.voilaf;

import com.voilaf.annotationbean.Cat;
import com.voilaf.beanregisterprocessor.Lion;
import com.voilaf.beanregisterprocessor.Tiger;
import com.voilaf.javaconfigurationbean.Horse;
import com.voilaf.xmlbean.Dog;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        Dog dog = applicationContext.getBean(Dog.class);
        dog.run();

        Cat cat = applicationContext.getBean(Cat.class);
        cat.run();

        Horse horse = applicationContext.getBean(Horse.class);
        horse.run();

        Lion lion = applicationContext.getBean(Lion.class);
        lion.run();

        Tiger tiger = applicationContext.getBean(Tiger.class);
        tiger.run();
    }
}
