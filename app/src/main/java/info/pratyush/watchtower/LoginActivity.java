package info.pratyush.watchtower;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmailText;
    private EditText loginPassText;
    private Button  loginButton;
    private Button loginRegButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth= FirebaseAuth.getInstance();

        loginEmailText = (EditText) findViewById(R.id.Email_feild);
        loginPassText = (EditText) findViewById(R.id.pwd_field);
        loginButton = (Button) findViewById(R.id.Login_button);
        loginRegButton=  (Button) findViewById(R.id.Login_Reg_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginEmail = loginEmailText.getText().toString();
                String loginPass = loginPassText.getText().toString();
                if(!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPass) )
                {
                    mAuth.signInWithEmailAndPassword(loginEmail,loginPass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendToMain();
                            } else {
                                String errmsg = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this,"Error: "+errmsg,Toast.LENGTH_LONG).show();
                            }

                            // ...
                        }
                    });


                }
            }
        });

        loginRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regintent =  new Intent(LoginActivity.this,RegisterActivity.class);
                Toast.makeText(LoginActivity.this,"registeredddd",Toast.LENGTH_LONG).show();
                startActivity(regintent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentuser= mAuth.getCurrentUser();
        if(currentuser != null)
        {
            sendToMain();
        }

    }

    private void sendToMain() {
        Intent mainintent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(mainintent);
        finish();
    }
}
