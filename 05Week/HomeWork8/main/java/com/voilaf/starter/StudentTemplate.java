package com.voilaf.starter;

import lombok.Data;

@Data
public class StudentTemplate {

    private String name;

    private SchoolTemplate schoolTemplate;

    private KlassTemplate klassTemplate;
}
