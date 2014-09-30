package com.umkc.grocerymart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        final EditText uname = (EditText)findViewById(R.id.editText1);
        final EditText pwd = (EditText)findViewById(R.id.editText2);
        Button login = (Button)findViewById(R.id.button1);
        
        login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String username = uname.getText().toString();
				String passwd = pwd.getText().toString();
				
				System.out.println("password: "+passwd);
				
				if(true)//username.equalsIgnoreCase("aniln") && passwd.equalsIgnoreCase("anil"))
				{
					Intent i = new Intent(getApplicationContext(), FindNearByStore.class);
					startActivity(i);
					finish();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Please enter valid credentials", Toast.LENGTH_SHORT).show();
				}
			}
		});
    }


}
