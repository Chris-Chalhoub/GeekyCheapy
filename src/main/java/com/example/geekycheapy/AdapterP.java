package com.example.geekycheapy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterP extends ArrayAdapter<Product> {

    public Context context;
    private List<String> names;
    private List<String> prices;
    private int [] images;

    public AdapterP(@NonNull Context context, ArrayList<Product> productsArrayList) {
        super(context, 0, productsArrayList);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.element, parent, false);
        }

        Product product = getItem(position);

        TextView txtName = listitemView.findViewById(R.id.txtName);
        TextView txtPrice = listitemView.findViewById(R.id.txtPrice);
        ImageView imgPhoto = listitemView.findViewById(R.id.imgPhoto);

        txtName.setText(product.getName());
        txtPrice.setText(product.getPrice());
        String mDrawableName = product.getImage();

        int resID = this.context.getResources().getIdentifier(mDrawableName , "drawable", this.context.getPackageName());
        imgPhoto.setImageResource(resID);
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context instanceof ProductsActivity){
                    ((ProductsActivity)context).onClickChangeActivity(product);
                }

            }

        });
        return listitemView;

    }
}
