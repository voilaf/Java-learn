package com.voilaf;

import com.voilaf.combination.Klass;
import com.voilaf.combination.School;
import com.voilaf.combination.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        School school = applicationContext.getBean(School.class);
        System.out.println(school);

        Klass klass = applicationContext.getBean(Klass.class);
        System.out.println(klass);

        Student student = applicationContext.getBean(Student.class);
        System.out.println(student);
    }
}
