package com.example.znotagain.login;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.znotagain.DatabaseHelper;
import com.example.znotagain.R;

/**
 * Created by zNotAgain on 3/3/2018.
 */

public class Activity_CreateUserAccount extends Activity {

    DatabaseHelper myDb;
    EditText username,password,confirmPassword;
    Button registerButton;
    TextView alreadyMember;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createuseraccount);
        myDb = new DatabaseHelper(this);

        username = findViewById(R.id.register_usernameText);
        password = findViewById(R.id.register_passwordText);
        confirmPassword = findViewById(R.id.register_confirmPasswordText);
        alreadyMember = findViewById(R.id.already_member);
        registerButton = findViewById(R.id.register_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make sure username & password fields are not empty
                if(isUserRegisterAcceptable(username.getText().toString(),password.getText().toString(),confirmPassword.getText().toString())){
                    if(password.getText().toString().matches(confirmPassword.getText().toString())){
                        Cursor user_res = myDb.checkUserLoginData(username.getText().toString(), password.getText().toString());
                        Cursor owner_res = myDb.checkOwnerLoginData(username.getText().toString(), password.getText().toString());

                        // make sure account doesn't exist in database
                        if(user_res.getCount() == 1 || owner_res.getCount() == 1){
                            Toast.makeText(Activity_CreateUserAccount.this,"Account Already Exists",Toast.LENGTH_LONG).show();
                        }else{
                            boolean isInserted = myDb.addUserAccount(username.getText().toString(),password.getText().toString());

                            // on successful insert to database
                            if(isInserted){
                                Toast.makeText(Activity_CreateUserAccount.this,"Account Created",Toast.LENGTH_LONG).show();
                                finish();
                            }else{
                                Toast.makeText(Activity_CreateUserAccount.this,"Account not Created",Toast.LENGTH_LONG).show();
                            }
                        }
                    }else{
                        confirmPassword.setError("Does not match your password");
                    }
                }else{
                    // username & password fields are empty
                    Toast.makeText(Activity_CreateUserAccount.this,"Required Fields are Empty",Toast.LENGTH_LONG).show();
                }
            }
        });

        alreadyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public boolean isUserRegisterAcceptable(String username, String password, String confirm_password){
        return !(username.isEmpty() || password.isEmpty() || confirm_password.isEmpty());
    }

}
