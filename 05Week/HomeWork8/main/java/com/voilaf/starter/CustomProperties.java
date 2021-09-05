package com.voilaf.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "custom")
@Data
public class CustomProperties {

    private String schoolName = "school.DefaultName";

    private String klassName = "klass.DefaultName";

    private String studentName = "student.DefaultName";
}
