package com.example.firestoretest2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "FirestoreTest2";
    User currentUser = new User();

    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        final EditText editTextUserName = findViewById(R.id.editUserName);
        final EditText editTextUserEmail = findViewById(R.id.editUserEmail);

        Button btnInsertUser = findViewById(R.id.btnInsertUser);
        Button btnDeleteUser = findViewById(R.id.btnDeleteUser);
        Button btnUpdateUser = findViewById(R.id.btnUpdateUser);
        ListView lstUsers = findViewById(R.id.lstUsers);

        ReadUsers();

        btnInsertUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertUser(editTextUserName.getText().toString(), editTextUserEmail.getText().toString());
                ReadUsers();
                CleanFields();
            }
        });

        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser(currentUser);
                ReadUsers();
                CleanFields();
            }
        });

        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser(currentUser);
                ReadUsers();
                CleanFields();
            }
        });

        lstUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentUser = (User) parent.getItemAtPosition(position);

                editTextUserName.setText(currentUser.getUserName());
                editTextUserEmail.setText(currentUser.getUserEmail());

            }
        });

    }

    public void insertUser(String userName, String userEmail){

        db = FirebaseFirestore.getInstance();
        CollectionReference users = db.collection("Users");

        User newUser = new User(userName, userEmail, false);

        users.add(newUser);
        Toast.makeText(MainActivity.this, "User created !",
                Toast.LENGTH_SHORT).show();

    }

    public void deleteUser(User user){

        db.collection("Users").document(user.getUserId())
                .delete().addOnSuccessListener(new OnSuccessListener < Void > () {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this, "User deleted !",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void updateUser(User user){

        String txtUserName = ((EditText)(findViewById(R.id.editUserName))).getText().toString();
        String txtUserEmail = ((EditText)(findViewById(R.id.editUserEmail))).getText().toString();

        WriteBatch batch = db.batch();

        DocumentReference userData = db.collection("Users").document(user.getUserId());

        batch.update(userData, "userName", txtUserName);
        batch.update(userData, "userEmail", txtUserEmail);

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainActivity.this, "User Updated !",
                        Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void ReadUsers() {

        final ListView lstUsers = findViewById(R.id.lstUsers);
        final List<User> userList = new ArrayList<>();

        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                user.setUserId(document.getId());
                                userList.add(user);
                            }

                            UserAdapter myAdapter = new UserAdapter(userList);
                            lstUsers.setAdapter(myAdapter);

                        } else {
                            Log.d(TAG, "Error getting users: ", task.getException());
                        }
                    }
                });

    }

    private void CleanFields(){

        EditText editTextUserName = findViewById(R.id.editUserName);
        EditText editTextUserEmail = findViewById(R.id.editUserEmail);

        editTextUserName.setText("");
        editTextUserEmail.setText("");
    }


}
