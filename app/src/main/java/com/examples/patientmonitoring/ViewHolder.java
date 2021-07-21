package com.examples.patientmonitoring;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    public ViewHolder(@NonNull  View itemView) {

        super(itemView);
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                mClicklistener.onItemLongClick(view,getAdapterPosition());
                return false;
            }
        });

    }
    public void setData(Context context,String name,String age,String gender,String cami){
        TextView textView = itemView.findViewById(R.id.textrow);
        textView.setText("Name " + name + "\n" +"Age " + age + "\n"+"Gender " + gender);
       /* Intent j = new Intent(ViewHolder.this,
                MainActivity.class);
        j.putExtra("1",name);
        j.putExtra("2",age);
        j.putExtra("3",gender);
        context.startActivity(j);*/
    }
    private ViewHolder.Clicklistener mClicklistener;
    public interface Clicklistener {
        void onItemLongClick(View view, int posistion);

    }
    public void setOnClickListener(ViewHolder.Clicklistener clickListener){
        mClicklistener =clickListener;
    }
}
