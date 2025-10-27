package com.mylearnings.java.collections;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ListSetMap {

    public static void main(String[] args) {

        // Adding null to set and map
        Set<Integer> sets = new HashSet<>();
        sets.add(null);
        sets.add(123);
        System.out.println(sets);

        Map<Integer, String> maps = new HashMap<>();
        maps.put(null, "123");
        maps.put(null, "1222");
        maps.put(null, "12322");
        System.out.println(maps);

        //Modifying the reference of the key
        Map<StringBuilder, String> objectString = new HashMap<>();
        StringBuilder builder = new StringBuilder("123344");
        objectString.put(builder, "String");
        builder.append("12333");
        System.out.println(objectString); // {12334412333=String}
        builder = new StringBuilder("123344");
        builder.append("abcd");
        System.out.println(objectString); // {12334412333=String}
    }

}
