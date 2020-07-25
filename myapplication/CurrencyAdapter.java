package com.example.myapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CurrencyAdapter extends ArrayAdapter <CurrencyInfor> {
    public CurrencyAdapter(Context context, ArrayList<CurrencyInfor> currencyInfoArrayList) {
        super(context, 0, currencyInfoArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = createRow(position, (ListView)parent);
        }
        return convertView;
    }

    private View createRow(int position, ListView parent) {
        View itemView = createARow(position);
        CurrencyInfor currencyInfor = getItem(position);
        DisplayInfo(itemView, currencyInfor);
        return itemView;
    }

   private void DisplayInfo(View itemView, CurrencyInfor currencyInfor) {
        ImageView imageView = (ImageView) itemView.findViewById(R.id.itemflags);
        imageView.setImageResource(currencyInfor.Flags);

        TextView textView =(TextView)itemView.findViewById(R.id.itemCountryName);
        textView.setText(currencyInfor.Name);

        TextView currencyView=(TextView)itemView.findViewById(R.id.itemUnitCurrency);
        currencyView.setText(currencyInfor.Currency);

        TextView present=(TextView)itemView.findViewById(R.id.presentResult);
        present.setText(Float.toString(currencyInfor.result));

    }
    private View createARow(int position) {//set up item show as items_layout
        return LayoutInflater.from(this.getContext()).inflate(R.layout.items_layout, null);
    }


}
