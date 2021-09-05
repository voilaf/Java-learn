package com.voilaf.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@EnableConfigurationProperties(CustomProperties.class)
public class CustomAuthConfiguration {

    @Autowired
    private CustomProperties customProperties;

    @Bean("schoolTemplate")
    public SchoolTemplate createSchool() {
        System.out.println("school init");
        SchoolTemplate schoolTemplate = new SchoolTemplate();
        schoolTemplate.setName(customProperties.getSchoolName());
        return schoolTemplate;
    }

    @Bean("klassTemplate")
    @DependsOn("schoolTemplate")
    public KlassTemplate createKlass(SchoolTemplate schoolTemplate) {
        System.out.println("klass init");
        KlassTemplate klassTemplate = new KlassTemplate();
        klassTemplate.setName(customProperties.getKlassName());
        klassTemplate.setSchoolTemplate(schoolTemplate);
        return klassTemplate;
    }

   @Bean("studentTemplate")
   @DependsOn("klassTemplate")
   public StudentTemplate createStudent(SchoolTemplate schoolTemplate, KlassTemplate klassTemplate) {
       System.out.println("klass init");
       StudentTemplate studentTemplate = new StudentTemplate();
       studentTemplate.setName(customProperties.getStudentName());
       studentTemplate.setSchoolTemplate(schoolTemplate);
       studentTemplate.setKlassTemplate(klassTemplate);
       return studentTemplate;
   }


}
