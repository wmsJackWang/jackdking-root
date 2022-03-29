package domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class Teacher {

    String name;
    String subject;

    @Autowired
    Student student;

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", subject='" + subject + '\'' +
                ", student=" + student.toString() +
                '}';
    }
}
