package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private EditText edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button logout = findViewById(R.id.logout);
        Button add = findViewById(R.id.add);
        edit = findViewById(R.id.edit);
        ListView listView = findViewById(R.id.listview);


        logout.setOnClickListener((v)-> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(MainActivity.this, "You are logged out!!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, StartActivity.class));
        });

        add.setOnClickListener((v) -> {
            String txtName = edit.getText().toString();

            if (txtName.isEmpty()) {
                Toast.makeText(MainActivity.this, "No Name is Inserted", Toast.LENGTH_SHORT).show();
            } else {
                //FirebaseDatabase.getInstance().getReference().child("Languages").child("Name").setValue(txtName);

                DatabaseReference languagesReference = FirebaseDatabase.getInstance().getReference().child("Languages");
                DatabaseReference newLanguageReference = languagesReference.push();
                newLanguageReference.setValue(txtName);

                Toast.makeText(MainActivity.this, "Data Inserted Successfully !!!!", Toast.LENGTH_LONG).show();
            }
        });


        //child("Programming Knowledge").


       final ArrayList <String> list = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.name_item_view,list);
        listView.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Languages");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    list.add(snapshot1.getValue().toString());
                }
                        adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}