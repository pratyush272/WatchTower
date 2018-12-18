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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText reg_email_field;
    private EditText reg_pass_field;
    private EditText reg_pass2_field;
    private Button reg_LoginButton;
    private Button reg_RegisterButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth  = FirebaseAuth.getInstance();

        reg_email_field=  (EditText)findViewById(R.id.reg_Email_feild);
        reg_pass_field=  (EditText)findViewById(R.id.reg_pwd_field);
        reg_pass2_field=  (EditText)findViewById(R.id.reg_pwd_field2);
        reg_LoginButton = (Button) findViewById(R.id.reg_Login_button);
        reg_RegisterButton = (Button) findViewById(R.id.reg_Login_Reg_button);

        reg_RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = reg_email_field.getText().toString();
                String passw = reg_pass_field.getText().toString();
                String passw2 = reg_pass2_field.getText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(passw) && !TextUtils.isEmpty(passw2) && passw.equals(passw2))
                {
                    mAuth.createUserWithEmailAndPassword(email,passw).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendToMain();
                            } else {
                                String errmsg = task.getException().getMessage();
                                Toast.makeText(RegisterActivity.this,"Error: "+errmsg,Toast.LENGTH_LONG).show();
                            }

                            // ...
                        }
                    });



                }
                else
                    {
                        Toast.makeText(RegisterActivity.this,"Check the fields",Toast.LENGTH_LONG).show();
                    }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //sendToMain();
    }

    private void sendToMain() {
        Intent mainintent = new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(mainintent);
        finish();
    }
}
