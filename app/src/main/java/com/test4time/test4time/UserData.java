package com.test4time.test4time;

/**
 * Stores information about each user from the USERS database table
 * Contains: user's name, grade level, and current time
 */
public class UserData {

    private String name, gradeLevel;
    private int currentTime;
    private int isParent;
    public UserData(String name, String gradeLevel, int currentTime, int isParent) {
        this.name = name;
        this.gradeLevel = gradeLevel;
        this.currentTime = currentTime;
        this.isParent = isParent;
    }

    public String getName() {
        return name;
    }

    public void setName() {this.name = name;}

    public String getGradeLevel() {
        return gradeLevel;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setName(String newName) {
        name = newName;
    }

    public void setGradeLevel(String newGradeLevel) {
        gradeLevel = newGradeLevel;
    }

    public void setCurrentTime(int newTime) {
        currentTime = newTime;
    }

    // isParent = 1 means user is a Parent,
    //          = 0 means user is a Child/Student
    public int getIsParent() {
        return isParent;
    }
}
