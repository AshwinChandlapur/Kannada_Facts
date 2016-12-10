package com.vadeworks.kannadafacts;

import java.util.Random;

public class kannadafacts {
    String facts[]={"Kannada is the only Indian language for"};


    int i =0;

    public String nextfact()
    {
        Random r = new Random();
        int Low = 0;
        int High = facts.length;
        int Result = r.nextInt(High-Low) + Low;
        return facts[Result];
    }

    public String prevfact()
    {
        Random r = new Random();
        int Low = 0;
        int High = facts.length;
        int Result = r.nextInt(High-Low) + Low;
        return facts[Result];

    }
}