package com.mylearnings.java.java_code.core_java;

/*
✔️ Lazy
✔️ Thread-safe
✔️ No volatile
✔️ No synchronization overhead
*/

public class CustomSingletonInitOnDemandIdiom {

    private static class SingletonHelper {
        private static final CustomSingletonInitOnDemandIdiom INSTANCE = new CustomSingletonInitOnDemandIdiom();
    }

    private CustomSingletonInitOnDemandIdiom() {
    }

    public static CustomSingletonInitOnDemandIdiom getInstance() {
        return SingletonHelper.INSTANCE;
    }

}
