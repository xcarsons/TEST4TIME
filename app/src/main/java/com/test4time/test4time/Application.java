package com.test4time.test4time;

/**
 * @Author - Carson Schaefer
 * Class stores information about selected applications
 */
public class Application {
    private String name, packageName, processName;
    public Application(String name, String packageName, String processName) {
        this.name = name;
        this.packageName = packageName;
        this.processName = processName;
    }

    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getProcessName() {
        return processName;
    }
}
