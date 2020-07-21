package com.company;

import java.util.Iterator;
import java.util.List;

public class Main {

    public void run() {
//        Pet p = new Pet("dog");
//        Cat c = Cat.makeConstructor("tom");
//        c.setColor(PrimaryColor.BLUE);
//        p.feed();
//        p.feed("burger");
//        c.feed();
//        System.out.println(c.noise);
//
//        Runnable r = new ExampleRunnable();
//        r.run();

//        List<String> ls = List.of("cat","dog","rabbit");
//        Iterator<String> it = ls.iterator();
//        while (it.hasNext()){
//            String s = it.next();
//            System.out.println(s);
//        }
//
//        for (String s: ls){
//            System.out.println(s);
//        }
//

        char s = 'a';
        char f = (char) (s + 1);
        String a = "kallol";
        for (char c : a) System.out.println(c);
        System.out.println(f);

    }

    public static void main(String[] args) {
        Main m = new Main();
        m.run();
    }
}
