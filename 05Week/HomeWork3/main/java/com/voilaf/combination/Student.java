package com.voilaf.combination;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Student {

    private String name;
    private School school;
    private Klass klass;
}
