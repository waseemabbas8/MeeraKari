package com.example.tahreem_fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.service.autofill.FieldClassification;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.Login;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;

//import com.facebook.accountkit.LoginResult;
import com.facebook.login.LoginManager;


import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


import java.util.Arrays;
import java.util.regex.Pattern;

public class login extends AppCompatActivity {

  //Yahan par initialization ho ree hai un chzon ki jo humen chaiey hain ...
  EditText et_Email,et_Pass;
  Button btn_Login,btn_Signup,btn_fb,btn_gmail;
  TextView forgetPass,signuptxt;
  ImageView visiblity;
  DatabaseReference databaseReference;
  RadioButton radiobuttonuser,radiobuttonvendor;


  LoginButton loginButton;
  String radiobuttontext = "";
  boolean checked = false;
  // password visibilty basically vo button hai jis se password show ho rahaa hai
  boolean passwordvisiblity = false;
//    ProgressDialog progressDialog;

  // Shared preferences basically tab use hota hai jab humen kisi chz ka data gather karna ho ..
  SharedPreferences.Editor sharedPreferences;
  // Session manager ki ek clss hai jo is liey use hoi hai k agar ek phone se ya id se
  // ek baar connect ho jae to dusri br na karni parre regstration ya login
  SessionManager manager;

  //Ye firebase k database ka reference laa k de raha hai yaa us ka instance bana raha hai
  DatabaseReference dbref;

  // firebase authentication ka kaam ho raha hai yahan jo k firebase me authentication mojud hai

  private FirebaseAuth firebaseAuth;


  // ye hum rejex ka pattern lagaa re hain jo k ye keh raha hai k a se le k z tak ki  alphabets hain
  // 0 se le k 9 tak k numbers hain
  // ASCII codes bhi included hain taake hum email ko check kar saken
  // Email ka pattern check ho jae is se

  public final Pattern Email_pattern = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +

          "\\@" +

          "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +

          "(" +

          "\\." +

          "[a-zA-Z][a-zA-Z\\-]{0,25}" +

          ")+");
  // password k patterns rakhe jaa re hain jece k pass ko hum alphabets bhi le len
  // Aur 0 se le k 9 tak ki numbers le len

  public final Pattern Password_Pattern = Pattern.compile("[a-zA-Z0-9+]{6,20}");
  private ProgressDialog LoadingBar;
  private ProgressDialog LoadingBarsecond;

  private CallbackManager callbackManager;
  private static final String EMAIL = "email";

/*
    // for google signup
GoogleSignInClient mGoogleSignInClient;
SignInButton sign_in;
int RC_SIGN_IN = 0;


 */

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);


/*
        // for google sign up button
        sign_in = findViewById(R.id.sign_in_button);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.sign_in_button:
                    signIn();
                    break;
                }
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

*/

    // facebook
    FacebookSdk.sdkInitialize(getApplicationContext());
    AppEventsLogger.activateApp(this);


    callbackManager = CallbackManager.Factory.create();

    //  loginButton = (LoginButton) findViewById(R.id.loginbutton);



    loginButton = (LoginButton) findViewById(R.id.login_button);
    loginButton.setReadPermissions("email");
    // If you are using in a fragment, call loginButton.setFragment(this);

    // Callback registration
    loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
      @Override
      public void onSuccess(LoginResult loginResult) {
//                SessionManager sessionManager = new SessionManager(getApplicationContext());
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        // boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        accessToken.getUserId();
        Profile.getCurrentProfile().getFirstName();
        String Email = Profile.getCurrentProfile().getId();

        handleFacebookAccessToken(loginResult.getAccessToken(), Profile.getCurrentProfile());
        Profile.getCurrentProfile().getLastName();


//                sessionManager.createSession("","");
        // App code
      }

      @Override
      public void onCancel() {
        // App code
      }

      @Override
      public void onError(FacebookException exception) {
        // App code
      }
    });
/*
        LoadingBar = new ProgressDialog(this);
        LoadingBar.setTitle("WELCOME");
        LoadingBar.setMessage("PLEASE WAIT , WE ARE REGISTERING YOUR DATA");
        //ye is liey kiyaa h use taake kahin touch krne pr loadng baar bnd na hoo

        LoadingBar.setCanceledOnTouchOutside(false);
        LoadingBar.show();

 */

    // login ki chzen yahan dekh re hain hum aur yahan par view by id kara k vo chzen rakhvaa re hain

    et_Email = (EditText) findViewById(R.id.et_emailID);
    et_Pass = (EditText) findViewById(R.id.etPass);
    btn_Login = (Button) findViewById(R.id.loginbutton);
    //btn_fb = (Button)findViewById(R.id.fb_button);
    //  btn_gmail = (Button)findViewById(R.id.gmail_button);




//    radiobuttonvendor.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        //Toast.makeText(login.this, "hello Vendor", Toast.LENGTH_SHORT).show();
//      }
//    });




    //  btn_Signup = (Button) findViewById(R.id.btnSignup);

    //class ko hum yahan par dikhaa re hain session ki claaass ko k yahan se session ki class shru ho jae
    manager = new SessionManager(this);

    // firebase ki authentication ka kaam ka instance aarha hai ye
    firebaseAuth = FirebaseAuth.getInstance();
//        dbref = FirebaseDatabase.getInstance().getReference("Users");

    // visibility yahann par usee show picture ki baat ho ree hai
    visiblity = findViewById(R.id.svisiblitybtn);

    // jab yahan par click ho jae tab ye chalna shru ho jae
    visiblity.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        // agar pass visiblity false hai to vo use true kar de jis se pass humen show ho jae ga
        if (passwordvisiblity == false) {
          passwordvisiblity = true;
          // visibility image ki resource uthaani hai
          visiblity.setImageResource(R.drawable.visiblity_off);
          // pass ko input me rakh diyaa
          // ek baat yaad rahe k hum pass save nh karaa re hain
          et_Pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

          // pass ki length rakh li jae aur aur us ka text get ho jae
          et_Pass.setSelection(et_Pass.getText().length());
          // yahan par bhi vahi visibility ka kaam ho raha hai k agar pass visible ho to true ho ga ye varna false ho jae ga

        } else if (passwordvisiblity == true) {
          passwordvisiblity = false;

          // drawable me visibility k naam se parra hai image
          visiblity.setImageResource(R.drawable.visible);
          et_Pass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
          et_Pass.setSelection(et_Pass.getText().length());
        }
      }
    });

    //
    signuptxt = (TextView) findViewById(R.id.suptxt);
    signuptxt.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // yahan par ye signup ki activity open karaa araha hai

        Intent intent = new Intent(login.this, SignUpActivity.class);
        startActivity(intent);
      }
    });

    btn_Login.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {


          //Toast.makeText(login.this, "Hello User", Toast.LENGTH_SHORT).show();
          sign_in();


      }
    });





    // forget pass par se ho rahaa hai ..
    forgetPass = (TextView) findViewById(R.id.forgotpassword);
    forgetPass.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // login  k neeche ek chotaa sa forget pass likha va hai ..
        // forget pass click k baad ye forget pass ki activity khol de gaa

        Intent intentfp = new Intent(login.this, ForgotPassword.class);
        startActivity(intentfp);
      }
    });

  }


  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    callbackManager.onActivityResult(requestCode, resultCode, data);
    super.onActivityResult(requestCode, resultCode, data);


  }

  private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
    try {
      GoogleSignInAccount account = completedTask.getResult(ApiException.class);

      // Signed in successfully, show authenticated UI.
//            Intent intent = new Intent(login.this,MainActivity.class);
//            startActivity(intent);
    } catch (ApiException e) {
      // The ApiException status code indicates the detailed failure reason.
      // Please refer to the GoogleSignInStatusCodes class reference for more information.
      Log.w("Error", "signInResult:failed code=" + e.getStatusCode());

    }
  }






  //facebook login

  private void handleFacebookAccessToken(AccessToken token , final Profile profile) {
//        Log.d(TAG, "handleFacebookAccessToken:" + token);

    AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
    databaseReference = FirebaseDatabase.getInstance().getReference();
    firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                final String emaiAdd = task.getResult().getUser().getEmail();
                if (task.isSuccessful()) {
                  String uid = firebaseAuth.getCurrentUser().getUid();
                  User us = new User(task.getResult().getUser().getEmail(),profile.getFirstName(), profile.getLastName());
                  databaseReference.child("Users").child(uid).setValue(us)
                          .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull final Task<Void> task) {


                              dbref = FirebaseDatabase.getInstance().getReference("Users");
                              // value event listener se kaam ho traha hai aur is me listener ho raha hai
                              // jo k events recieve karta hai data changes par
                              dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                  // firebase k instances hain basically snapshots matlab k databases ki duplicates rakhi jaati hain
                                  for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    if (dataSnapshot1.child("email_id").getValue(String.class).equals(emaiAdd)) {
                                      final String u_name = dataSnapshot1.child("firstn").getValue().toString();

                                      // is k email id and fierstname lastname vagrea ka string me connections ho rahe hain

                                      // shared preferences me ye hai k mmory kse saman ikhatta kar k laa re hain
                                      sharedPreferences = getSharedPreferences("usertype", MODE_PRIVATE).edit();
                                      sharedPreferences.putBoolean("utype", false);
                                      sharedPreferences.apply();
                                      // yahan par session create ho gaya jis me humare ps email aur pass hota hai
                                      manager.createSession(u_name, emaiAdd);
//                                            progressDialog.hide();

                                      // yahan par msg boxes ho re hain show k login successful karaa do
                                      Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                                      Intent MainIntent = new Intent(login.this,MainActivity.class);
                                      startActivity(MainIntent);
                                      finish();





                                      break;
                                    }
                                  }
                                }








                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                              });
                            }
                          });
                  Intent intent = new Intent(login.this, MainActivity.class);
                  finish();
                  startActivity(intent);
                } else {
                  Toast.makeText(login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                }
              }
            });
  }

  private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
//        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

    AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
    firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                final String emaiAdd = task.getResult().getUser().getEmail();
                if (task.isSuccessful()) {
                  String uid = firebaseAuth.getCurrentUser().getUid();
                  User us = new User(acct.getEmail(),acct.getDisplayName(), acct.getFamilyName());
                  databaseReference.child("Users").child(uid).setValue(us)
                          .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull final Task<Void> task) {


                              dbref = FirebaseDatabase.getInstance().getReference("Users");
                              // value event listener se kaam ho traha hai aur is me listener ho raha hai
                              // jo k events recieve karta hai data changes par
                              dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                  // firebase k instances hain basically snapshots matlab k databases ki duplicates rakhi jaati hain
                                  for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    if (dataSnapshot1.child("email_id").getValue(String.class).equals(emaiAdd)) {
                                      final String u_name = dataSnapshot1.child("firstn").getValue().toString();

                                      // is k email id and fierstname lastname vagrea ka string me connections ho rahe hain

                                      // shared preferences me ye hai k mmory kse saman ikhatta kar k laa re hain
                                      sharedPreferences = getSharedPreferences("usertype", MODE_PRIVATE).edit();
                                      sharedPreferences.putBoolean("utype", false);
                                      sharedPreferences.apply();
                                      // yahan par session create ho gaya jis me humare ps email aur pass hota hai
                                      manager.createSession(u_name, emaiAdd);
//                                            progressDialog.hide();

                                      // yahan par msg boxes ho re hain show k login successful karaa do
                                      Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                                      Intent MainIntent = new Intent(login.this,MainActivity.class);
                                      startActivity(MainIntent);
                                      finish();





                                      break;
                                    }
                                  }
                                }








                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                              });
                            }
                          });
                  Intent intent = new Intent(login.this, MainActivity.class);
                  finish();
                  startActivity(intent);
                } else {
                  Toast.makeText(login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                }

                // ...
              }
            });
  }

  // login with  login button

  public void sign_in()
  {

    // email ID ,password vageraa le re hain yahan par aur if else k checks par se kaam kar re hain jin me kuch conditions hain
    final String emaiAdd = et_Email.getText().toString();

    // string me  save kareaa re hain is me email aur pass dnn ko
    String pword = et_Pass.getText().toString();

    // email aur pass check karen
    if (checkEmail(emaiAdd) && checkPassword(pword)) {
      firebaseAuth.signInWithEmailAndPassword(emaiAdd, pword)
              // ye dehan rahe k dnn chzen filled hon like email address hogaa, aur password dekhen ge
              .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                  if (task.isSuccessful()) {
                    // agar task successful ho jaata hai to User nam ka ek table firebase me hai mojud
                    // vo reference ajae gaa aur us ka instance aarhaa0 hai yahan par
                    // Uzer me basically sb ki information bparri v hai
                    dbref = FirebaseDatabase.getInstance().getReference("Users");
                    // value event listener se kaam ho traha hai aur is me listener ho raha hai
                    // jo k events recieve karta hai data changes par
                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // firebase k instances hain basically snapshots matlab k databases ki duplicates rakhi jaati hain
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                          if (dataSnapshot1.child("email_id").getValue(String.class).equals(emaiAdd)) {
                            final String u_name = dataSnapshot1.child("firstn").getValue().toString();

                            // is k email id and fierstname lastname vagrea ka string me connections ho rahe hain

                            // shared preferences me ye hai k mmory kse saman ikhatta kar k laa re hain
                            sharedPreferences = getSharedPreferences("usertype", MODE_PRIVATE).edit();
                            sharedPreferences.putBoolean("utype", false);
                            sharedPreferences.apply();
                            // yahan par session create ho gaya jis me humare ps email aur pass hota hai
                            manager.createSession(u_name, emaiAdd);
//                                            progressDialog.hide();

                            // yahan par msg boxes ho re hain show k login successful karaa do
                            Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                            Intent MainIntent = new Intent(login.this,MainActivity.class);
                            startActivity(MainIntent);
                            finish();





                            break;
                          }
                        }
                      }








                      @Override
                      public void onCancelled(@NonNull DatabaseError databaseError) {

                      }
                    });
                  } else {


                    // varna aap  ye msg show karaa do k available nh hai aap ka email
                    Toast.makeText(login.this, "Enter a valid Email And Password !", Toast.LENGTH_SHORT).show();
                  }
                }
              });
    }
    else
    {
      Toast.makeText(login.this, "Check Your Internet Connection!", Toast.LENGTH_SHORT).show();

    }


  }



  private boolean checkPassword(String password) {

    // password pattern ko check krana hai jo hum ne rejex sekaam kiyaa hai
    return Password_Pattern.matcher(password).matches();
  }

  // email check kare gaa  aur us k patern se match bhi kare gaa
  private boolean checkEmail(String email) {
    return Email_pattern.matcher(email).matches();
  }

}



