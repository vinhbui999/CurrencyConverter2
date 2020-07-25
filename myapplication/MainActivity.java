package com.example.myapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Currency;

import static java.lang.Integer.getInteger;
import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Float Result=(float)0;
    Float FNum=(float)0;
    Float SNum=(float)0;
    Float CRate=(float)0;

    Boolean Addition, Subtraction, Multiple, Divide, IsStringInText;
    Boolean Doing=false;//if doing any operation
    Boolean ZeroOn=false;//if clicked on 0 button
    Boolean isClickedOperator=false;
    Button but0, but1, but2, but3, but4, but5, but6, but7, but8, but9, butdot, butequal, butdel, butdiv, butmul, butsub,butadd;
    TextView expression, baseCurrency, targetName, targetCurrency, targetUnitCurrency;
    ImageView targetImg;
    String stringExpress, stringResult;
    // for HW02
    ImageButton addItems;
    Intent intentOut=null;
    Intent intentIn = null;
    TextView checking;
    ArrayList<CurrencyInfor> currencyList = new ArrayList<>();
    ArrayList<CurrencyInfor> targetList = new ArrayList<>();
    CurrencyInfor2 currencyInforIn = null;


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            stringExpress = expression.getText().toString();
            stringResult = baseCurrency.getText().toString();
            setContentView(R.layout.activity_main);
            setUpAllButtons();
            setOnClick();
            //getSupportActionBar().hide();
            intentIn = getIntent();
            Bundle args = intentIn.getBundleExtra("bundle");
            if(args != null){
                currencyList = (ArrayList<CurrencyInfor>) args.getSerializable("CurrencyList");
                setListView();
                expression.setText(stringExpress);
                baseCurrency.setText(stringResult);
            }

        }
        else if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            stringExpress = expression.getText().toString();
            stringResult = baseCurrency.getText().toString();
            setContentView(R.layout.landscape_main1);
            setUpAllButtons();
            setOnClick();
            //getSupportActionBar().hide();

            intentIn = getIntent();
            Bundle args = intentIn.getBundleExtra("bundle");
            if(args != null){
                currencyList = (ArrayList<CurrencyInfor>) args.getSerializable("CurrencyList");
                setListView();
                expression.setText(stringExpress);
                baseCurrency.setText(stringResult);
            }
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        IsStringInText=false;
        Addition=false;
        Subtraction=false;
        Multiple=false;
        Divide=false;

        //getSupportActionBar().hide();
        super.onCreate(savedInstanceState);

        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_main);

        }
        else if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.landscape_main1);
        }
        setUpAllButtons();
        setOnClick();

        currencyList = createCurrencyList();

        intentIn = getIntent();
        Bundle args = intentIn.getBundleExtra("bundle");
        if(args != null){
            currencyList = (ArrayList<CurrencyInfor>) args.getSerializable("CurrencyList");
            updateTargetList();
            setListView();
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putString( "expression",stringexpress); //maintaince expression
        outState.putFloat("result", Result);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    private void setListView() {
        final ListView listView = (ListView)findViewById(R.id.listCurrency);
        final CurrencyAdapter currencyAdapter = new CurrencyAdapter(this, targetList);
        listView.setAdapter(currencyAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {//i: position
                TextView itemsName = (TextView) view.findViewById(R.id.itemCountryName);
                TextView itemsCurrency=(TextView) view.findViewById(R.id.itemUnitCurrency);
                ImageView itemsImg = (ImageView)view.findViewById(R.id.itemflags);

                deleteItem(itemsCurrency.getText());
                listView.setAdapter(currencyAdapter);
            }
        });

    }

    private void deleteItem( CharSequence itemsCurrency) {
        CurrencyInfor currencyInfor=null;
        for(int i=0;i<targetList.size();++i){
            if(targetList.get(i).getCurrency() == itemsCurrency.toString()){
                currencyInfor = targetList.get(i);
                currencyInfor.inShow=false;
                currencyInfor.result =(float)0;
                break;
            }
        }
        targetList.remove(currencyInfor);

    }

    private void updateTargetList() {
        CurrencyInfor arg=null;
        for (int i = 0; i < currencyList.size(); ++i) {
            arg = currencyList.get(i);
            if (arg.inShow == true) { // need to show out
                targetList.add(arg);
            }
        }
    }

    private void setOnClick() {
        but0.setOnClickListener(helper);
        but1.setOnClickListener(helper);
        but2.setOnClickListener(helper);
        but3.setOnClickListener(helper);
        but4.setOnClickListener(helper);
        but5.setOnClickListener(helper);
        but6.setOnClickListener(helper);
        but7.setOnClickListener(helper);
        but8.setOnClickListener(helper);
        but9.setOnClickListener(helper);
        butdot.setOnClickListener(helper);
        butdel.setOnClickListener(helper);
        butdiv.setOnClickListener(helper);
        butmul.setOnClickListener(helper);
        butadd.setOnClickListener(helper);
        butsub.setOnClickListener(helper);
        butequal.setOnClickListener(helper);

        addItems.setOnClickListener(helper);

    }
    private void setUpAllButtons() {
        but0 = findViewById(R.id.num0);
        but1 = findViewById(R.id.num1);
        but2 = findViewById(R.id.num2);
        but3 = findViewById(R.id.num3);
        but4 = findViewById(R.id.num4);
        but5 = findViewById(R.id.num5);
        but6 = findViewById(R.id.num6);
        but7 = findViewById(R.id.num7);
        but8 = findViewById(R.id.num8);
        but9 = findViewById(R.id.num9);
        butdot = findViewById(R.id.Dot);
        butequal = findViewById(R.id.equal);
        butdel = findViewById(R.id.delete);
        butdiv = findViewById(R.id.divide);
        butmul = findViewById(R.id.ismul);
        butsub = findViewById(R.id.subtract);
        butadd = findViewById(R.id.plus);
        expression = (TextView) findViewById(R.id.editCurrency);
        baseCurrency=(TextView) findViewById(R.id.baseCurrency);
        //targetName=(TextView) findViewById(R.id.targetCountryName);
        //targetImg=(ImageView) findViewById(R.id.targetImage);
        //targetUnitCurrency= (TextView)findViewById(R.id.unitCurreny);
        //targetCurrency=(TextView) findViewById(R.id.targetCurrencies);


        addItems=(ImageButton)findViewById(R.id.addItem);

    }

    private  View.OnClickListener helper = new View.OnClickListener() {
        Float check;
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.num0:
                        expression.setText(expression.getText()+ "0");
                        break;
                    case R.id.num1:
                        expression.setText(expression.getText()+ "1");
                        break;
                    case R.id.num2:
                        expression.setText(expression.getText()+ "2");
                        break;
                    case R.id.num3:
                        expression.setText(expression.getText()+ "3");
                        break;
                    case R.id.num4:
                        expression.setText(expression.getText()+ "4");
                        break;
                    case R.id.num5:
                        expression.setText(expression.getText()+ "5");
                        break;
                    case R.id.num6:
                        expression.setText(expression.getText()+ "6");
                        break;
                    case R.id.num7:
                        expression.setText(expression.getText()+ "7");
                        break;
                    case R.id.num8:
                        expression.setText(expression.getText()+ "8");
                        break;
                    case R.id.num9:
                        expression.setText(expression.getText()+ "9");
                        break;
                    case R.id.Dot:
                        expression.setText(expression.getText()+ ".");
                        break;
                    case R.id.equal:
                        if(Float.parseFloat(expression.getText().toString()) != (float)0 && !Doing){
                            Result = Float.parseFloat(expression.getText().toString());
                            onlyConvert(Result);
                            break;
                        }
                        showResult(expression, view);
                        break;
                    case R.id.delete:
                        //expression.setText(backSpace(expression));
                        ExecuteClear(expression);
                        break;
                    case R.id.divide:
                       // expression.setText(expression.getText()+"/");
                        if(isClickedOperator == true)
                            changeOperation();
                        if(Result!=(float)0){
                            expression.setText(baseCurrency.getText().toString());
                        }
                        divide(expression);
                        break;
                    case R.id.ismul:
                        //expression.setText(expression.getText()+"x");
                        if(isClickedOperator == true)
                            changeOperation();
                        if(Result!=(float)0){
                            expression.setText(baseCurrency.getText().toString());
                        }
                        multi(expression);
                        break;
                    case R.id.subtract:
                        if(isClickedOperator == true)
                            changeOperation();
                        if(Result!=(float)0){
                            expression.setText(baseCurrency.getText().toString());
                        }
                       subtract(expression);
                        break;
                    case R.id.plus:
                        //expression.setText(expression.getText()+"+");
                        if(isClickedOperator == true)
                            changeOperation();
                        if(Result!=(float)0){
                            expression.setText(baseCurrency.getText().toString());
                        }
                        addition(expression);
                        break;
                    case R.id.addItem:
                        //Toast.makeText(MainActivity.this,"Press add items", Toast.LENGTH_SHORT).show();
                        // now move to next activity
                        intentOut = new Intent(MainActivity.this, MainActivity2.class);
                        Bundle args=new Bundle();
                        args.putSerializable("CurrencyList", (Serializable)currencyList);
                        intentOut.putExtra("bundle", args);

                        startActivity(intentOut);
                        break;


                }
            }
    };

    private void onlyConvert(Float result) {
        if(result==Math.round(result))
            baseCurrency.setText(String.valueOf(Math.round(result)));
        else baseCurrency.setText(String.valueOf(result));
        converter(result);
    }

    private String backSpace(TextView expression) {
        String a= expression.getText().toString();
        if(a.length()<=1)
            return null;
        a=a.substring(0, a.length()-1);
        return a;
    }

    private void multi(TextView expression) {
        FNum = Float.parseFloat(expression.getText().toString());
        ZeroOn=false;
        Multiple=true;
        Doing=true;
        isClickedOperator=true;
        expression.setText(null);
    }

    private void divide(TextView expression) {
            FNum = Float.parseFloat(expression.getText().toString());
            Divide=true;
            Doing=true;
            isClickedOperator=true;
            expression.setText(null);
    }

    private void subtract(TextView expression) {
            FNum = Float.parseFloat(expression.getText().toString());
            Subtraction=true;
            Doing=true;
            ZeroOn=false;
            isClickedOperator=true;
            expression.setText(null);
    }

    private void addition(TextView expression) {
            FNum = Float.parseFloat(expression.getText().toString());
            Addition=true;
            Doing=true;
            ZeroOn=false;
            isClickedOperator=true;
            expression.setText(null);
    }

    private void changeOperation(){
        expression.setText(String.valueOf(FNum));
        isClickedOperator=false;
    }


    private ArrayList<CurrencyInfor> createCurrencyList(){
        ArrayList<CurrencyInfor> currencyListfake = new ArrayList<>();
        currencyListfake.add(new CurrencyInfor("United State Dollar$","USD", R.drawable.usd, 23200));//1$=23 182vnd
        currencyListfake.add(new CurrencyInfor("Japanese YEN","JPY",R.drawable.jpy, 21600));// 1JPY = 216.95 vnd
        currencyListfake.add(new CurrencyInfor("Euro","EUR",R.drawable.eur, 26400));//1 KRW= 19.28vnd
        currencyListfake.add(new CurrencyInfor("Pound Sterling","GBP",R.drawable.gpb, 29000));//1EUR= 26257.12 vnd
        return currencyListfake;
    }


/*
    private void create16Buttons() {
        GridLayout gridLayout = getGridLayoutMain();
        String[] Ids = initIdFor9Buttons();
        String[] labels = initLabelsFor9Buttons();
        for(int i=0; i < Ids.length;++i){
            Button button = createButton(Ids[i], labels[i]);
            addButtontoGridView(button, gridLayout);
        }
    }

    private void addButtontoGridView(Button button, GridLayout gridLayout) {
        gridLayout.addView(button);
    }

    private Button createButton(String id, String label) {
        Button button = new Button(this);
        button.setText(label);
        button.setId(Integer.parseInt(id));
        button.setTextColor(Color.parseColor("#FFFFFF"));
        button.setBackgroundColor(Color.parseColor("#202020"));

        button.setOnClickListener(helper);
        return button;

    }

    private String[] initLabelsFor9Buttons() {
        return new String[]
                {"7","8","9",
                 "4","5","6",
                 "1","2","3",
                 ".","0","="};
    }

    private String[] initIdFor9Buttons() {
        return new String[]
                {"7","8","9",
                 "4","5","6",
                 "1","2","3",
                 "10","0","11"};
    }

    private GridLayout getGridLayoutMain() {
        return (GridLayout)findViewById(R.id.Numberpad);
    }

*/
/*
    private View.OnClickListener helper = new View.OnClickListener() {
        @Override
        public void onClick(View view) {// for 13 buttons
            TextView editText=(TextView) findViewById(R.id.editCurrency);

            Float check = Float.parseFloat(editText.getText().toString());

            if((float)check == (float)0 && !ZeroOn || IsStringInText==true){
                editText.setText(null);
            }
            switch (view.getId()){
                case 0:
                    if(Result!=(float)0 && !Doing){ //has result but u press another number
                        ExecuteClear(editText, view.getId());
                        break;
                    }
                    editText.setText(editText.getText() + "0");
                    ZeroOn=true;
                    break;
                case 1:
                    if(Result!=(float)0 && !Doing){
                        ExecuteClear(editText, view.getId());
                        break;
                    }
                    editText.setText(editText.getText()+ "1");
                    break;
                case 2:
                    if(Result!=(float)0 && !Doing){
                        ExecuteClear(editText, view.getId());
                        break;
                    }
                    editText.setText(editText.getText()+ "2");
                    break;
                case 3:
                    if(Result!=(float)0 && !Doing){
                        ExecuteClear(editText, view.getId());
                        break;
                    }
                    editText.setText(editText.getText()+ "3");
                    break;
                case 4:
                    if(Result!=(float)0 && !Doing){
                        ExecuteClear(editText, view.getId());
                        break;
                    }
                    editText.setText(editText.getText()+ "4");
                    break;
                case 5:
                    if(Result!=(float)0 && !Doing){
                        ExecuteClear(editText, view.getId());
                        break;
                    }
                    editText.setText(editText.getText()+ "5");
                    break;
                case 6:
                    if(Result!=(float)0 && !Doing){
                        ExecuteClear(editText, view.getId());
                        break;
                    }
                    editText.setText(editText.getText()+ "6");
                    break;
                case 7:
                    if(Result!=(float)0 && !Doing){
                        ExecuteClear(editText, view.getId());
                        break;
                    }
                    editText.setText(editText.getText()+ "7");
                    break;
                case 8:
                    if(Result!=(float)0 && !Doing){
                        ExecuteClear(editText, view.getId());
                        break;
                    }
                    editText.setText(editText.getText()+ "8");
                    break;
                case 9:
                    if(Result!=(float)0 && !Doing){
                        ExecuteClear(editText, view.getId());
                        break;
                    }
                    editText.setText(editText.getText()+ "9");
                    break;
                case 10://float
                    if(Result!=(float)0 && (Result!=Math.round(Result)) && !Doing){
                        ExecuteError(editText, view.getId());
                        break;
                    }
                    editText.setText(editText.getText()+ ".");
                    break;
                case 11://show result
                    showResult(editText, view.getId());
                    break;



            }

        }
    };*/

    private void showResult(TextView editText, View view) {
        if(Addition==true){
            SNum = Float.parseFloat(editText.getText().toString());
            presentExpression(editText, "+");

            Result = FNum + SNum;

            if(Result == Math.round(Result)){
                baseCurrency.setText(String.valueOf(Math.round(Result)));
            }
            else{
                baseCurrency.setText(String.valueOf(Result));
            }
            Addition=false;
            Doing=false;
            converter(Result);
        }

        else if(Subtraction==true){
            SNum=Float.parseFloat(editText.getText().toString());
            presentExpression(editText, " - ");

            Result = FNum - SNum;
            if(Result == Math.round(Result)){
                baseCurrency.setText(String.valueOf(Math.round(Result)));
            }
            else
                baseCurrency.setText(String.valueOf(Result));
            Subtraction=false;
            Doing=false;
            converter(Result);
        }
        else if(Multiple){
            SNum=Float.parseFloat(editText.getText().toString());
            presentExpression(editText,"x");
            Result = FNum * SNum;

            if(Result == Math.round(Result)){
                baseCurrency.setText(String.valueOf(Math.round(Result)));
            }
            else
                baseCurrency.setText(String.valueOf(Result));

            Multiple=false;
            Doing=false;
            converter(Result);
        }
       else  if(Divide){
            SNum=Float.parseFloat(editText.getText().toString());
            presentExpression(editText,"/");

            if(Math.round(SNum) == 0){//divided by zero
               ExecuteError(editText);
               Result=(float)0;
            }
            else{
                Result = FNum / SNum;
                if(Result == Math.round(Result)){
                    baseCurrency.setText(String.valueOf(Math.round(Result)));}
                else baseCurrency.setText(String.valueOf(Result));
            }
            Doing=false;
            converter(Result);
        }
        ZeroOn=false;
        //ExecuteClear(editText,id);
    }


    private void presentExpression(TextView editText, String s) {
        if(SNum==Math.round(SNum) && FNum==Math.round(FNum)){
            editText.setText(Math.round(FNum) + s + Math.round(SNum));
        }
        else if(SNum!=Math.round(SNum) && FNum==Math.round(FNum)){
            editText.setText(Math.round(FNum) + s + SNum);
        }
        else if(SNum==Math.round(SNum) && FNum!=Math.round(FNum)){
            editText.setText(FNum + s + Math.round(SNum));
        }
    }

    private void ExecuteClear(TextView editText) {
        FNum = (float)0;
        SNum=(float)0;
        Result=(float)0;
        baseCurrency.setText(null);
        editText.setText(null);
        if(targetList != null){
            ListView listView=(ListView)findViewById(R.id.listCurrency);
            CurrencyAdapter currencyInfor = new CurrencyAdapter(this, targetList);
            CurrencyInfor currencyInfor1 = null;
            for(int i=0; i<targetList.size();++i){
                currencyInfor1=targetList.get(i);
                currencyInfor1.result = 0;
            }
            listView.setAdapter(currencyInfor);
        }

    }

    private void ExecuteError(TextView editText) { //showing "Bad Expression!"
        // condition: press operator then press equal/ press num -> press operator then press equal/
        editText.setText("Bad Expression!!!");
        editText.setTextColor(Color.parseColor("#990000"));

        IsStringInText=true;
        Toast.makeText(this,"Please choose the currency to convert", Toast.LENGTH_LONG).show();

    }

    private void converter(Float result){
        Float a = (float)0;
        ListView listView=(ListView)findViewById(R.id.listCurrency);
        CurrencyAdapter currencyInfor = new CurrencyAdapter(this, targetList);
        CurrencyInfor currencyInfor1 = null;
        TextView view=null;
        for(int i=0; i<targetList.size();++i){
            currencyInfor1=targetList.get(i);
            a =  result / targetList.get(i).Rate;
            currencyInfor1.result = a;

            /*
            if(result==(float)0){
                view.setText("0");
                break;}
            else{
                if(a==Math.round(a)){
                    view.setText(Float.toString(Math.round(a)));
                   }
                else {
                    view.setText(Float.toString(a));
                }
            }*/
        }
        listView.setAdapter(currencyInfor);

    }


    @Override
    public void onClick(View view) {

    }

}