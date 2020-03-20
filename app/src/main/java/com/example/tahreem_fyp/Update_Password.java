package com.example.tahreem_fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Update_Password extends AppCompatActivity {

    FirebaseUser user;
    EditText currentPass, newPass, comfrimPass;
    Button btnUpdate;
    FirebaseAuth mAuth;
    ImageView visiblity1,visiblity2,visiblity3;


    boolean passwordvisiblity1 = false;
    boolean passwordvisibilty2 = false;
    boolean passwordvisibility3 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__password);

        currentPass = (EditText) findViewById(R.id.Currentpass);
        newPass = (EditText) findViewById(R.id.Enternewwpass);
        comfrimPass = (EditText) findViewById(R.id.Enternewwpassagain);
        btnUpdate = (Button) findViewById(R.id.btn_updatepass);


        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();




// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.


        ////-------------------------------------------------------------------------------------------////

        visiblity1 = (ImageView)findViewById(R.id.svisiblitybtn1);
        visiblity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // agar pass visiblity false hai to vo use true kar de jis se pass humen show ho jae ga
                if (passwordvisiblity1 == false) {

                    passwordvisiblity1 = true;
                    // visibility image ki resource uthaani hai
                    visiblity1.setImageResource(R.drawable.visiblity_off);
                    // pass ko input me rakh diyaa
                    // ek baat yaad rahe k hum pass save nh karaa re hain
                    currentPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    // pass ki length rakh li jae aur aur us ka text get ho jae
                    currentPass.setSelection(currentPass.getText().length());
                    // yahan par bhi vahi visibility ka kaam ho raha hai k agar pass visible ho to true ho ga ye varna false ho jae ga

                } else if (passwordvisiblity1 == true) {

                    passwordvisiblity1 = false;

                    // drawable me visibility k naam se parra hai image
                    visiblity1.setImageResource(R.drawable.visible);
                    currentPass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    currentPass.setSelection(currentPass.getText().length());
                }
            }
        });



        ////-----------------------------------------------------------------------------------------------////




        visiblity2 = (ImageView)findViewById(R.id.svisiblitybtn2);
        visiblity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // agar pass visiblity false hai to vo use true kar de jis se pass humen show ho jae ga
                if (passwordvisibilty2 == false) {

                    passwordvisibilty2 = true;
                    // visibility image ki resource uthaani hai
                    visiblity2.setImageResource(R.drawable.visiblity_off);
                    // pass ko input me rakh diyaa
                    // ek baat yaad rahe k hum pass save nh karaa re hain
                    newPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    // pass ki length rakh li jae aur aur us ka text get ho jae
                    newPass.setSelection(newPass.getText().length());
                    // yahan par bhi vahi visibility ka kaam ho raha hai k agar pass visible ho to true ho ga ye varna false ho jae ga

                } else if (passwordvisibilty2 == true) {

                    passwordvisibilty2 = false;

                    // drawable me visibility k naam se parra hai image
                    visiblity2.setImageResource(R.drawable.visible);
                    newPass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    newPass.setSelection(newPass.getText().length());
                }
            }
        });




        /////-----------------------------------------------------------------------------------------------------///



        visiblity3 = (ImageView)findViewById(R.id.svisiblitybtn3);
        visiblity3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // agar pass visiblity false hai to vo use true kar de jis se pass humen show ho jae ga
                if (passwordvisibility3 == false) {

                    passwordvisibility3 = true;
                    // visibility image ki resource uthaani hai
                    visiblity3.setImageResource(R.drawable.visiblity_off);
                    // pass ko input me rakh diyaa
                    // ek baat yaad rahe k hum pass save nh karaa re hain
                    comfrimPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    // pass ki length rakh li jae aur aur us ka text get ho jae
                    comfrimPass.setSelection(comfrimPass.getText().length());
                    // yahan par bhi vahi visibility ka kaam ho raha hai k agar pass visible ho to true ho ga ye varna false ho jae ga

                } else if (passwordvisibility3 == true) {

                    passwordvisibility3 = false;

                    // drawable me visibility k naam se parra hai image
                    visiblity3.setImageResource(R.drawable.visible);
                    comfrimPass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    comfrimPass.setSelection(comfrimPass.getText().length());
                }
            }
        });




        ////----------------------------------------------------------------------------------------------------------///



        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cp = currentPass.getText().toString();
                AuthCredential credential = EmailAuthProvider
                        .getCredential(mAuth.getCurrentUser().getEmail(),cp );

// Prompt the user to re-provide their sign-in credentials
                mAuth.getCurrentUser().reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    String np = newPass.getText().toString();
                                    String cnp = comfrimPass.getText().toString();
//                                    if (np == cnp) {
                                    mAuth.getCurrentUser().updatePassword(np).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("Something", "Password updated");
                                                Toast.makeText(Update_Password.this, "Password Changed Successfully!", Toast.LENGTH_SHORT).show();
                                                finish();
                                            } else {
                                                Log.d("Something2", "Error !!! password not updated");
                                                Toast.makeText(Update_Password.this, "Error updating password ! Passwords doesn't match !", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
//                                    }
//                                    else {
//                                        Toast.makeText(Update_Password.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
//                                    }
                                }
                                else {
                                    Log.d("Else", "Error auth failed");
                                }
                            }
                        });

            }
        });

    }
}
