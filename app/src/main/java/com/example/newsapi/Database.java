package com.example.newsapi;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database {

    private DatabaseReference databaseReference;

    public Database(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Model.class.getSimpleName());
    }

    public Task<Void> add(Model mo){
        return databaseReference.push().setValue(mo);
    }

}
