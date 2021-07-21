package com.examples.patientmonitoring;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class mainpage extends AppCompatActivity {
    EditText name,age,gender,camip;
    Button save;
    ImageButton settings;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,geturl;

    Upload upload;
    String namee,np,n1,a1,g1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        name =findViewById(R.id.n);
        age = findViewById(R.id.ag);
        gender =findViewById(R.id.g);
        camip= findViewById(R.id.cam);
        settings= findViewById(R.id.set);
        save =findViewById(R.id.button);
        recyclerView =findViewById(R.id.rec);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        geturl=firebaseDatabase.getInstance().getReference().child("Url");
        databaseReference=firebaseDatabase.getInstance().getReference().child("Data");
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfirebase();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /// a=FirebaseDatabase.getInstance().getReference().child("Age");

                upload =new Upload();
                name =findViewById(R.id.n);
                age = findViewById(R.id.ag);
                gender =findViewById(R.id.g);
                camip= findViewById(R.id.cam);
                String aa=age.getText().toString();
                String g=gender.getText().toString();
                String na=name.getText().toString();
                String caip=camip.getText().toString();
                upload.setAge(aa);
                upload.setGender(g);
                upload.setName(na);
                upload.setCami(caip);
                //a.setValue(upload);
                String id =databaseReference.push().getKey();
                databaseReference.child(id).setValue(upload);
                Toast.makeText(mainpage.this, "Data saved", Toast.LENGTH_SHORT).show();

                Intent i =new Intent(mainpage.this,MainActivity.class);

                //i.putExtra("Value",na);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Upload> options =
                new FirebaseRecyclerOptions.Builder<Upload>()
                        .setQuery(databaseReference,Upload.class)
                        .build();
        FirebaseRecyclerAdapter<Upload,ViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<Upload, ViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Upload model) {
                        holder.setData(getApplicationContext(),model.getName(),model.getAge(),model.getGender(),model.getCami());

                        holder.setOnClickListener(new ViewHolder.Clicklistener() {
                            @Override
                            public void onItemLongClick(View view, int posistion) {
                                // namee =getItem(posistion).getName();

                                //upload=new Upload();
                                name =findViewById(R.id.n);
                                age = findViewById(R.id.ag);
                                gender =findViewById(R.id.g);
                                String aa=model.getAge();
                                String g=model.getGender();
                                String na=model.getName();
                                String caa=model.getCami();
                                geturl.setValue(caa);
                                Intent i =new Intent(mainpage.this,MainActivity.class);
                                i.putExtra("1",na);
                                i.putExtra("2",aa);
                                i.putExtra("3",g);
                                i.putExtra("4",caa);
                                //openPatientreg();
                                startActivity(i);

                                finish();
                                //showDeleteDataDialog(namee);
                            }

                        });

                    }

                    @NonNull

                    @Override
                    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.row,parent,false);

                        return new ViewHolder(view);
                    }
                };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    private void showDeleteDataDialog(String name){
        AlertDialog.Builder builder=new AlertDialog.Builder(mainpage.this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Query query = databaseReference.orderByChild("name").equalTo(name);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(mainpage.this, "Data deleted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    public void openPatientreg() {
        //inter.show();
        Intent intent = new Intent(mainpage.this, MainActivity.class);
        startActivity(intent);
    }
    /*public void setData(Context context, String name, String age, String gender,String cami){
        // TextView textView = itemView.findViewById(R.id.textrow);
        // textView.setText("Name" + name + "\n" +"Age" + age + "\n"+"Gender" + gender);
        Intent j = new Intent(mainpage.this,
                MainActivity.class);
        j.putExtra("1",name);
        j.putExtra("2",age);
        j.putExtra("3",gender);
        j.putExtra("4",cami);
        context.startActivity(j);*/
  //  }
    public void openfirebase() {
        //inter.show();
        Intent intent = new Intent(mainpage.this, firebase.class);
        startActivity(intent);
    }
}