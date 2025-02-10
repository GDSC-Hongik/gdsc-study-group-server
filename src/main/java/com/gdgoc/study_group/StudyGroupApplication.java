package com.gdgoc.study_group;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class StudyGroupApplication {

  public static void main(String[] args) {
    SpringApplication.run(StudyGroupApplication.class, args);
  }
}
