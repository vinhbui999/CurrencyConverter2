package com.example.myapplication;

import java.io.Serializable;

public class CurrencyInfor2 implements Serializable {
    public String Name2;
    public String Currency2;
    public int Flags2;
    public boolean inShow=false;

    public CurrencyInfor2(String name2, String currency2, int flags2){
        Name2=name2;
        Currency2=currency2;
        Flags2=flags2;
    }

}
