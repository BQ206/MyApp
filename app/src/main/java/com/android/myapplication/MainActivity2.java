package com.android.myapplication;


import static android.graphics.Color.WHITE;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlendMode;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.BlendModeColorFilterCompat;
import androidx.core.graphics.BlendModeCompat;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthMultiFactorException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.MultiFactorResolver;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity2 extends AppCompatActivity, {
    TextView textView;

    TextView descr;
    TextView namez;
    TextView price;
    Python py;

    PyObject pyobj;

    Button Btn1wk;
    Button Btn3mo;
    Button Btn6mo;

    Button Btn1yr;
    Button Btn5yr;

    Button sl_stk;

    Button back;

    Button cr_usr;

    FirebaseAuth mAuth;

    TextView stck;

    Button Login;

    String m_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();



        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        String symb = intent.getStringExtra(MainActivity.Extra_symbol);
        String name = intent.getStringExtra(MainActivity.Extra_name);
        String prcez = intent.getStringExtra(MainActivity.Extra_prce);
        py = Python.getInstance();

        pyobj = py.getModule("myscript");

        PyObject obj2 = pyobj.callAttr("stock_descr", symb);

        int dscID = getResources().getIdentifier("appdescr", "id", getPackageName());
        descr = (TextView)findViewById(dscID);
        descr.setText(obj2.toString());


        int rfID = getResources().getIdentifier("apppri", "id", getPackageName());
        price = (TextView)findViewById(rfID);
        price.setText(prcez);

        int rzID = getResources().getIdentifier("text1", "id", getPackageName());
        namez = (TextView)findViewById(rzID);
        namez.setText(name);

        int bnUres = getResources().getIdentifier("wk1", "id", getPackageName());
        Btn1wk = (Button)findViewById(bnUres);

        Btn1wk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl_1wk(symb);
            }
        });

        int bnUre2 = getResources().getIdentifier("mo3", "id", getPackageName());
        Btn3mo = (Button)findViewById(bnUre2);

        Btn3mo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl_3mo(symb);
            }
        });

        int bnUre3 = getResources().getIdentifier("mo6", "id", getPackageName());
        Btn6mo = (Button)findViewById(bnUre3);

        Btn6mo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl_6mo(symb);
            }
        });

        int bnUre4 = getResources().getIdentifier("yr1", "id", getPackageName());
        Btn1yr = (Button)findViewById(bnUre4);

        Btn1yr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl_1yr(symb);
            }
        });

        int bnUre5 = getResources().getIdentifier("yr5", "id", getPackageName());
        Btn5yr = (Button)findViewById(bnUre5);

        Btn5yr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl_5yr(symb);
            }
        });



        int bnUre = getResources().getIdentifier("backbn", "id", getPackageName());
        back = (Button)findViewById(bnUre);

        int sbnUre = getResources().getIdentifier("cre_user", "id", getPackageName());
        cr_usr = (Button)findViewById(sbnUre);

        int sbnzUre = getResources().getIdentifier("sel_stck", "id", getPackageName());
        sl_stk = (Button)findViewById(sbnzUre);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_back();
            }
        });

        sl_stk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slct_stck_rm(name);
            }
        });

        cr_usr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create_acc("brian_quilty@hotmail.com","here1243");
            }
        });

        int loginid = getResources().getIdentifier("login", "id", getPackageName());
        Login = (Button)findViewById(loginid);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign_in_acc("brian_quilty@hotmail.com","here1243");
            }
        });


    }



    void create_acc(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            System.out.println("intv");
                            FirebaseUser user = mAuth.getCurrentUser();
                            m_uid = user.getUid();
                        } else {
                            System.out.println("ikn");
                            // If sign in fails, display a message to the user.
                        }

                    }
                });
    }

    void sign_in_acc(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            System.out.println("izn");
                            FirebaseUser user = mAuth.getCurrentUser();
                            m_uid = user.getUid();
                        } else {
                            // If sign in fails, display a message to the user.

                        }

                        // ...
                    }
                });
    }

    void slct_stck(String stocktad){
        Map<String, Object> user = new HashMap<String, Object>();
        String rt;
        rt = "583";
        user.put("stocks", rt);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference stocks_m = db.collection("user_stocks").document(m_uid);
        stocks_m.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        stocks_m.update("stocks", FieldValue.arrayUnion(stocktad));
                    } else {
                        db.collection("user_stocks").document(m_uid).set(user);
                        stocks_m.update("stocks", FieldValue.arrayUnion(stocktad));
                    }
                } else {
                    System.out.println("fdc");
                }
            }
        });

        System.out.println(stocks_m);
        System.out.println(m_uid);



    }

    void slct_stck_rm(String stocktad){
        Map<String, Object> user = new HashMap<String, Object>();
        String rt;
        rt = "583";
        user.put("stocks", rt);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference stocks_m = db.collection("user_stocks").document(m_uid);
        stocks_m.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        stocks_m.update("stocks", FieldValue.arrayRemove(stocktad));
                    } else {
                        db.collection("user_stocks").document(m_uid).set(user);
                        stocks_m.update("stocks", FieldValue.arrayRemove(stocktad));
                    }
                } else {
                    System.out.println("fdc");
                }
            }
        });

        System.out.println(stocks_m);
        System.out.println(m_uid);



    }

    void get_stck(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("sel_stck");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                int rzstID = getResources().getIdentifier("sel_stck_tx", "id", getPackageName());
                stck = (TextView)findViewById(rzstID);
                stck.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    void go_back(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    void cl_1wk(String symbz){
        PyObject frame = pyobj.callAttr("pr_stck_1wk",symbz);
        byte[] frameData = py.getBuiltins().callAttr("bytes", frame).toJava(byte[].class);
        Bitmap bitmap = BitmapFactory.decodeByteArray(frameData, 0, frameData.length);
        ((ImageView) findViewById(R.id.cameraImage)).setImageBitmap(bitmap);
    }
    void cl_3mo(String symbz){
        PyObject frame = pyobj.callAttr("pr_stck_3m",symbz);
        byte[] frameData = py.getBuiltins().callAttr("bytes", frame).toJava(byte[].class);
        Bitmap bitmap = BitmapFactory.decodeByteArray(frameData, 0, frameData.length);
        ((ImageView) findViewById(R.id.cameraImage)).setImageBitmap(bitmap);
    }
    void cl_6mo(String symbz){
        PyObject frame = pyobj.callAttr("pr_stck_6m",symbz);
        byte[] frameData = py.getBuiltins().callAttr("bytes", frame).toJava(byte[].class);
        Bitmap bitmap = BitmapFactory.decodeByteArray(frameData, 0, frameData.length);
        ((ImageView) findViewById(R.id.cameraImage)).setImageBitmap(bitmap);
    }
    void cl_1yr(String symbz){
        Btn1yr.setColorFilter(BlendModeColorFilterCompat.createBlendModeColorFilterCompat(WHITE, BlendModeCompat.SRC_ATOP)
        PyObject frame = pyobj.callAttr("pr_stck_1y",symbz);
        byte[] frameData = py.getBuiltins().callAttr("bytes", frame).toJava(byte[].class);
        Bitmap bitmap = BitmapFactory.decodeByteArray(frameData, 0, frameData.length);
        ((ImageView) findViewById(R.id.cameraImage)).setImageBitmap(bitmap);
    }
    void cl_5yr(String symbz){
        PyObject frame = pyobj.callAttr("pr_stck_5y",symbz);
        byte[] frameData = py.getBuiltins().callAttr("bytes", frame).toJava(byte[].class);
        Bitmap bitmap = BitmapFactory.decodeByteArray(frameData, 0, frameData.length);
        ((ImageView) findViewById(R.id.cameraImage)).setImageBitmap(bitmap);
    }
}