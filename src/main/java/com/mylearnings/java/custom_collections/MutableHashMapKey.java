package com.mylearnings.java.custom_collections;

import java.util.HashMap;
import java.util.Map;

public class MutableHashMapKey {

    void main() {

        // HashMap stores object references, not copies. That's why mutating the key object affects the map.
        // However, reassigning the key variable does not affect the map.
        Map<StringBuilder, String> objectString = new HashMap<>();
        StringBuilder builder = new StringBuilder("123344");
        objectString.put(builder, "String");
        builder.append("12333");
        System.out.println(objectString); // {12334412333=String}
        builder = new StringBuilder("123344");
        builder.append("abcd");
        System.out.println(objectString); // {12334412333=String}

        // Using a mutable object as a key in a HashMap is dangerous because:
            // -> If its internal state changes,
            // -> Its hashCode() changes,
            // -> But HashMap still thinks it belongs to the old bucket.
        // 🛑 Golden Rule: Never use a mutable object (like StringBuilder) as a HashMap key.

    }

}
