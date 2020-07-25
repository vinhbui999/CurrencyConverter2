package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    Intent intentIn=null;
    Intent intentOut = null;
    TextView expression, baseCurrency, targetName, targetCurrency, targetUnitCurrency;
    TextView textView;
    ArrayList<CurrencyInfor>  currencylist = new ArrayList<>();
    ArrayList<CurrencyInfor> targetlist = new ArrayList<>();

    CurrencyInfor currencyInfor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //getSupportActionBar().hide();
        intentIn = getIntent(); //get data
        Bundle args = intentIn.getBundleExtra("bundle");
         currencylist = (ArrayList<CurrencyInfor>) args.getSerializable("CurrencyList");

         updateCurrencyList();
        setListView2();

    }

    private void setListView2() {
        ListView listView2 = (ListView)findViewById(R.id.CurrenciesList2);

        final CurrencyAdapter currencyAdapter = new CurrencyAdapter(MainActivity2.this, targetlist);
        listView2.setAdapter(currencyAdapter);

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                targetlist.get(i).inShow=true;
                intentOut= new Intent(MainActivity2.this, MainActivity.class);// send to Main1
                Bundle args=new Bundle();
                args.putSerializable("CurrencyList", (Serializable)currencylist);
                intentOut.putExtra("bundle", args);
                startActivity(intentOut);

            }
        });

    }

    private void modifyInShow(int i) {
        CurrencyInfor currencyInfor =null;
        currencyInfor = targetlist.get(i);
    }

    private void updateCurrencyList() {
        CurrencyInfor arg=null;
        for(int i=0; i < currencylist.size();++i){
            arg=currencylist.get(i);
            if(arg.inShow==false) {
                targetlist.add(arg);
                //currencylist.get(i).inShow = true;
            }
        }

    }


    @Override
    public void onClick(View view) {

    }
}