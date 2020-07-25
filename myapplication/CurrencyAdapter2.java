package com.example.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import android.widget.Toast;

import org.w3c.dom.Text;
import java.util.ArrayList;

public class CurrencyAdapter2 extends ArrayAdapter<CurrencyInfor2> {
    public CurrencyAdapter2(Context context, ArrayList<CurrencyInfor2> currencyInforArrayList2) {
        super(context,0,currencyInforArrayList2);
    }

    @NonNull
    @Nullable
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = createRow(position, (ListView)parent);
        }
        return convertView;
    }

    private View createRow(int position, ListView parent) {
        View itemView = createARow(position);
        CurrencyInfor2 currencyInfor2 = getItem(position);
        DisplayInfo2(itemView, currencyInfor2);
        return itemView;
    }

    private void DisplayInfo2(View itemView, CurrencyInfor2 currencyInfor2) {
        ImageView imageView2 = (ImageView) itemView.findViewById(R.id.flagsitem);
        imageView2.setImageResource(currencyInfor2.Flags2);

        TextView textView2 =(TextView)itemView.findViewById(R.id.countryNameitem);
        textView2.setText(currencyInfor2.Name2);

        TextView currencyView2=(TextView)itemView.findViewById(R.id.unititem);
        currencyView2.setText(currencyInfor2.Currency2);
    }

    private View createARow(int position) {
        return LayoutInflater.from(this.getContext()).inflate(R.layout.items_layout2, null);
    }
}
