package com.example.tahreem_fyp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity{

  private ProgressDialog LoadingBar;
  // yahan par hum ne instances banana shru kare
  // means k sb k objects ya variables lena tart kar dieye
  private EditText first_name, last_name, email, password, confirm_password;
  private RadioButton radioButtonMale, radioButtonFemale,radioButtonVendor,radioButtonUser;
  private Button signupbutton;
  private ProgressBar progressBar;
  ImageView visiblityone;
  ImageView visibilitytwo;
  // firebase ki authentication
  private FirebaseAuth auth;

  boolean passwordvisiblity = false;
  boolean checked= false;
  //EditText first_name, last_name, email, password, confirm_password;
  //  ImageView visiblity, match;
  // TextView signin, condition;
  //Button signupbutton;
  // boolean password_visiblity = false;
  // Drawable border;

  // firebase ki authentication ho ree hai
  private FirebaseAuth firebaseAuth;
  // DB ka refernce diya hai
  DatabaseReference databaseReference;

  // ye progress bar jece kaam karta hai
  // but yahan ye as such use nh ho rahaa hai
  ProgressDialog progressDialog;

  String radioButtonText="";


  // rejex k vahi patterns lag re hain jis me a se z tak k alphatbets hain
  // 0 se le k 09 tak k numbers hain aur ASCII ki tenms hin
  // ye bataa raha hai k ye ye pattern ho to accept hoga email varna nh karna hai
  public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
          "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +

                  "\\@" +

                  "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +

                  "(" +

                  "\\." +

                  "[a-zA-Z][a-zA-Z\\-]{0,25}" +

                  ")+");


  public final Pattern Password_Pattern = Pattern.compile("[a-zA-Z0-9+]{6,20}");


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

      /*  LoadingBar = new ProgressDialog(this);
        LoadingBar.setTitle("WELCOME");
        LoadingBar.setMessage("PLEASE WAIT, WE ARE REGISTERING YOUR DATA");
        //ye is liey kiyaa h use taake kahin touch krne pr loadng baar bnd na hoo



        LoadingBar.setCanceledOnTouchOutside(true);
        LoadingBar.show();

         */

// signup ki activity par content set ho raha hai
    setContentView(R.layout.activity_sign_up);
    //  getSupportActionBar().setTitle("Signup");
    firebaseAuth = FirebaseAuth.getInstance();
    //Get Firebase auth instance
    // auth = FirebaseAuth.getInstance();



    signupbutton = (Button) findViewById(R.id.signup_btn);
    email = (EditText) findViewById(R.id.emailtxt);
    password = (EditText) findViewById(R.id.passtxt);
    confirm_password = (EditText)findViewById(R.id.passcond) ;
    first_name = (EditText) findViewById(R.id.fnametxt);
    last_name = (EditText) findViewById(R.id.lnametxt);
    radioButtonMale = (RadioButton) findViewById(R.id.RDB_male);
    radioButtonFemale = (RadioButton) findViewById(R.id.RDB_female);
    radioButtonUser = (RadioButton) findViewById(R.id.user_req);








    signupbutton.setOnClickListener(new View.OnClickListener() {


      @Override
      public void onClick(View v) {
          signup();
        LoadingBar = new ProgressDialog(v.getContext());
        LoadingBar.setTitle("SIGNING UP ");
        LoadingBar.setMessage("PLEASE WAIT ");
        LoadingBar.setCanceledOnTouchOutside(true);
        LoadingBar.show();









      }
    });





    visiblityone = findViewById(R.id.visiblitybtnonesignup);


    // jab yahan par click ho jae tab ye chalna shru ho jae
    visiblityone.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        // agar pass visiblity false hai to vo use true kar de jis se pass humen show ho jae ga
        if (passwordvisiblity == false) {
          passwordvisiblity = true;
          // visibility image ki resource uthaani hai
          visiblityone.setImageResource(R.drawable.visiblity_off);
          // pass ko input me rakh diyaa
          // ek baat yaad rahe k hum pass save nh karaa re hain
          password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

          // pass ki length rakh li jae aur aur us ka text get ho jae
          password.setSelection(password.getText().length());
          // yahan par bhi vahi visibility ka kaam ho raha hai k agar pass visible ho to true ho ga ye varna false ho jae ga

        } else if (passwordvisiblity == true) {
          passwordvisiblity = false;

          // drawable me visibility k naam se parra hai image
          visiblityone.setImageResource(R.drawable.visible);
          password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
          password.setSelection(password.getText().length());
        }
      }
    });


    visibilitytwo= findViewById(R.id.visiblitybtnsecondsignup);


    // jab yahan par click ho jae tab ye chalna shru ho jae
    visibilitytwo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        // agar pass visiblity false hai to vo use true kar de jis se pass humen show ho jae ga
        if (passwordvisiblity == false) {
          passwordvisiblity = true;
          // visibility image ki resource uthaani hai
          visibilitytwo.setImageResource(R.drawable.visiblity_off);
          // pass ko input me rakh diyaa
          // ek baat yaad rahe k hum pass save nh karaa re hain
          confirm_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

          // pass ki length rakh li jae aur aur us ka text get ho jae
          confirm_password.setSelection(confirm_password.getText().length());
          // yahan par bhi vahi visibility ka kaam ho raha hai k agar pass visible ho to true ho ga ye varna false ho jae ga

        } else if (passwordvisiblity == true) {
          passwordvisiblity = false;

          // drawable me visibility k naam se parra hai image
          visibilitytwo.setImageResource(R.drawable.visible);
          confirm_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
          confirm_password.setSelection(confirm_password.getText().length());
        }
      }
    });


  }

  private boolean checkEmail(String email) {
    return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
  }

  private boolean checkpass(String pass) {
    return Password_Pattern.matcher(pass).matches();
  }

  void signup() {
    final String Email = email.getText().toString().trim();
    final String Pass = password.getText().toString().trim();
    final String fName = first_name.getText().toString().trim();
    final String lName = last_name.getText().toString().trim();


    if (fName.isEmpty()) {
      first_name.setError("Enter first name");
      first_name.requestFocus();
      Toast.makeText(getApplicationContext(), "Enter first name!", Toast.LENGTH_SHORT).show();
      return;
    }
    if (lName.isEmpty()) {
      last_name.setError("Enter last name");
      last_name.requestFocus();
      Toast.makeText(getApplicationContext(), "Enter last name!", Toast.LENGTH_SHORT).show();
      return;
    }
    if (Email.isEmpty()) {
      email.setError("Enter Email Address");
      email.requestFocus();
      Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
      return;
    }

    if (Pass.isEmpty()) {
      password.setError("Enter Password");
      email.requestFocus();
      Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
      return;
    }
    if (fName.isEmpty() && lName.isEmpty() && Email.isEmpty() && Pass.isEmpty()) {
      Toast.makeText(getApplicationContext(), "Enter Fields", Toast.LENGTH_SHORT).show();
    } else if (!(fName.isEmpty() && lName.isEmpty() && Email.isEmpty() && Pass.isEmpty())) {


      databaseReference = FirebaseDatabase.getInstance().getReference();
      firebaseAuth.createUserWithEmailAndPassword(Email, Pass)
              .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                  if (task.isSuccessful()) {
                    String uid = firebaseAuth.getCurrentUser().getUid();
                    User us = new User(Email, fName, lName);
                    databaseReference.child("Users").child(uid).setValue(us)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                              @Override
                              public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                                startActivity(intent);
                              }
                            });
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    finish();
                    startActivity(intent);
                  } else {
                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                  }
                }
              });
    } else {
      Toast.makeText(SignUpActivity.this, "Fill The Fields Properly", Toast.LENGTH_LONG).show();

    }


  }
}
