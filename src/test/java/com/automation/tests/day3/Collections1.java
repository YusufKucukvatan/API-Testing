package com.automation.tests.day3;

import java.util.*;

public class Collections1 {
    public static void main(String[] args) {
//        List<List<Integer>> list=new ArrayList<>();
//        list.add(Arrays.asList(11,23,3,43,59));
//        list.add(Arrays.asList(5,6,7,8));
//        list.add(Arrays.asList(9,10));
//        System.out.println(list);
//        System.out.println(list.size());
//        System.out.println(list.get(2).get(0));
//        Collections.sort(list.get(0));
//        Collections.reverse(list.get(0));
//        System.out.println(list.get(0));
//        ArrayList<String> list1=new ArrayList<>();
//        list1.add("a");
//        LinkedList<String> list2=new LinkedList<>();
//        list2.add("a");

        Map<Integer, Integer> map1=new LinkedHashMap<>();
        map1.put(1,2);
        map1.put(3,4);
        map1.put(5,6);

        Map<Integer, Integer> map2=new LinkedHashMap<>();
        map2.put(7,8);
        map2.put(9,10);
        map2.put(11,12);

        Map<Integer, Integer> map3=new LinkedHashMap<>();
        map3.put(13,14);
        map3.put(15,16);
        map3.put(17,18);

        List<Map<Integer, Integer>> listMap=new ArrayList<>();
        listMap.add(map1);
        listMap.add(map2);
        listMap.add(map3);

        //System.out.println(listMap);
        for (Map<Integer, Integer> map:listMap) {
            System.out.println(map);
        }

        //Set<Integer> set

//    Map<String, String> map = new HashMap<>();
//        map.put(null, null);
//        map.put("xxxx", null);
//        map.put("yyyy", null);
//        map.put("yyyy", "cccc");
//        map.put("yyyy", "dddd");
//        map.put(null, null);
//        System.out.println(map);
    }

}
