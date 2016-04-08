package com.test4time.test4time;

/**
 * Created by Zach on 4/6/16.
 */
public class User {
    private String name, grade, time;


    public User(){

    }
    public User(String name, String grade, String time) {
        this.name = name;
        this.grade = grade;
        this.time = time;
    }
    

    public String getName() {
        return name;
    }

    public String getPackageName() {
        return grade;
    }

    public String getProcessName() {
        return time;
    }
}
