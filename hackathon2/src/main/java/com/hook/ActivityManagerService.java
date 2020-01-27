package com.hook;

public class ActivityManagerService implements IActivityManager {

    @Override
    public String startActivity(String name) {
        System.out.println("ActivityManagerService startActivity: " + name);
        return "ActivityManagerService_" + name;
    }
}
