package com.example.znotagain.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.znotagain.DatabaseHelper;
import com.example.znotagain.R;
import com.example.znotagain.login.Activity_Main;
import com.example.znotagain.owner.Activity_Owner;

/**
 * Created by zNotAgain on 9/3/2018.
 */

public class Activity_User_Settings extends Activity{
    DatabaseHelper myDb;
    Button backButton;
    TextView userName,passWord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        myDb = new DatabaseHelper(this);

        backButton = findViewById(R.id.back);
        userName = findViewById(R.id.settings_username);
        passWord = findViewById(R.id.settings_password);

        Intent intent = getIntent();
        final String username_message = intent.getStringExtra(Activity_Main.USER_NAME);
        final String password_message = intent.getStringExtra(Activity_Main.PASSWORD);

        if(username_message != null && password_message != null){
            userName.setText(username_message);
            passWord.setText(password_message);
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void editUsername(final View v){
        Intent intent = getIntent();
        final String username_message = intent.getStringExtra(Activity_Main.USER_NAME);
        final String password_message = intent.getStringExtra(Activity_Main.PASSWORD);

        if(username_message != null && password_message != null){
            userName.setText(username_message);
            passWord.setText(password_message);
        }

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(v.getContext());
        mBuilder.setCancelable(true);
        mBuilder.setTitle("Set new Username:");
        // Set up the input
        final EditText input = new EditText(v.getContext());
        // Specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        mBuilder.setView(input);

        // Set up the buttons
        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
                if(myDb.updateUserAccountUsername(userName.getText().toString(),m_Text)){
                    Toast.makeText(v.getContext(),"Edit Successful",Toast.LENGTH_SHORT).show();
                    userName.setText(m_Text);
                }
                else
                    Toast.makeText(v.getContext(),"Edit Failed",Toast.LENGTH_SHORT).show();
            }
        });
        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        mBuilder.show();
    }

    public void editPassword(final View v){
        Intent intent = getIntent();
        final String username_message = intent.getStringExtra(Activity_Main.USER_NAME);
        final String password_message = intent.getStringExtra(Activity_Main.PASSWORD);

        if(username_message != null && password_message != null){
            userName.setText(username_message);
            passWord.setText(password_message);
        }

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(v.getContext());
        mBuilder.setCancelable(true);
        mBuilder.setTitle("Set new Password:");
        // Set up the input
        final EditText input = new EditText(v.getContext());
        // Specify the type of input expected
        input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mBuilder.setView(input);

        // Set up the buttons
        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
                if(myDb.updateUserAccountPassword(passWord.getText().toString(),m_Text)){
                    Toast.makeText(v.getContext(),"Edit Successful",Toast.LENGTH_SHORT).show();
                    passWord.setText(m_Text);
                }
                else
                    Toast.makeText(v.getContext(),"Edit Failed",Toast.LENGTH_SHORT).show();
            }
        });
        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        mBuilder.show();
    }

    public void deleteAccount(final View v){
        Intent intent = getIntent();
        final String username_message = intent.getStringExtra(Activity_Main.USER_NAME);
        final String password_message = intent.getStringExtra(Activity_Main.PASSWORD);

        if(username_message != null && password_message != null){
            userName.setText(username_message);
            passWord.setText(password_message);
        }

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(v.getContext());
        mBuilder.setCancelable(true);
        mBuilder.setTitle("Confirm?");
        mBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(myDb.deleteUserAccount(userName.getText().toString(),passWord.getText().toString()) > 0){
                    Toast.makeText(v.getContext(),"Account Successfully Deleted",Toast.LENGTH_LONG).show();
                    setResult(Activity.RESULT_OK);
                    finish();
                }else
                    Toast.makeText(v.getContext(),"Account Delete Failed",Toast.LENGTH_LONG).show();

            }
        });
        mBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        mBuilder.show();
    }
}
