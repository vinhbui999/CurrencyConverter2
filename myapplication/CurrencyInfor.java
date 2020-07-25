package com.example.myapplication;

import java.io.Serializable;

public class CurrencyInfor implements Serializable {
    public String Name;
    public String Currency;
    public int Flags;
    public boolean inShow=false;
    public float Rate;
    public float result=(float)0;

    public CurrencyInfor(String name, String currency, int flags, float rate){
        Name=name;
        Currency=currency;
        Flags=flags;
        Rate = rate;
    }

    public String getCurrency() {
        return Currency;
    }

    //public boolean isInShow() {}


}
