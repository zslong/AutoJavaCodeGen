//package com.sumscope.idb.dcs.DealCodeGen;
//
//import java.lang.reflect.Field;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by shilong.zhang on 2018/2/12.
// */
//public class Test {
//    public static void main(String[] args) throws IllegalAccessException {
//        DcsDeal dcsDeal = new BondDcsDeal();
//        Map<String, String> fields = new HashMap<>();
//        for(Field field : dcsDeal.getClass().getDeclaredFields()) {
//            field.setAccessible(true);
//            fields.put(field.getName(), (String) field.get(dcsDeal));
//        }
//
//        System.out.println(fields.size());
//        fields.forEach((k,v)-> System.out.println(k.concat("->").concat(v)));
//    }
//}
