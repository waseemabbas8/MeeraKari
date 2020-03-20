package com.example.tahreem_fyp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Edit_Profile extends AppCompatActivity {



  // yahan par inputs le liey hain hum ne jis me buttons edittext and TextView ki kahani di v hai
  //User ki id generation bhi ho ree hai yahan
  TextView nameProf, lastnameProf, emailProf, userNameDisplay, userEmailDisplay, edt_email;
  Button btnUpdate;
  EditText edt_name, edt_lastname;
  String user_id ;

  // dtbase  ka refereence hai yahan par jahan firebase ka aur us k storage aur prefernces ka usage aarha hai
  DatabaseReference databaseReference;
  FirebaseAuth firebaseAuth;
  private StorageReference mStorageRef;
  // URl ka kaam ho rahaa h ..
  // uri ek tareeke se datatyp hoi
  // or img uri variable ka nam  h
  private Uri imgUri;
  // yahan par ek ye url bhi rakh raha hai
  public String url = "";


  // ye vahan k liey hai string ahan par title par name aarha hai .. email address aur picture aarhi hai
  String U_Fname = "", U_LName = "", U_Email = "", U_pic = "";
  // ImageButton vahan camera ka nishaan bana va hai jhn pr img add ho ree h
  ImageButton addImage,removePicture;
  // Imgview hn par hmri profile picture shosw ho ree hai
  ImageView userPicture;

  // storage ka pata karna hai k ye image k folder me rakhe ga sb czon ko
  public static final String FB_STORAGE_PATH = "image/";
  public static final String FB_DATABASE_PATH = "image";
  // ye images uthaane k liey use hotaa h .. ye confirmation k liey use hot h
  public static final int REQUEST_CODE = 1234;


  private ProgressDialog LoadingBar;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit__profile);


    // ON create k forun bad hi yahan par hum ne chzon ko bind krna shru kar diya ha
    nameProf = (TextView) findViewById(R.id.tv_username);
    lastnameProf = (TextView) findViewById(R.id.tv_lastname);
    emailProf = (TextView) findViewById(R.id.tv_email);

    edt_email = (TextView) findViewById(R.id.edt_email);
    edt_lastname = (EditText) findViewById(R.id.edt_lastname);
    edt_name = (EditText) findViewById(R.id.edt_userName);

    userNameDisplay = (TextView) findViewById(R.id.userNameProf);
    userEmailDisplay = (TextView) findViewById(R.id.EmailProf);
    btnUpdate = (Button) findViewById(R.id.btn_updateProfile);

    addImage = (ImageButton) findViewById(R.id.addPicture);
    userPicture = (ImageView) findViewById(R.id.user_Image);
    removePicture = (ImageButton) findViewById(R.id.removePicture);
// firebase ki authentication aur un k instances aane shru ho gae
    firebaseAuth = FirebaseAuth.getInstance();
    // yahan par firebase k storage k reference aana shru ho gya h
    mStorageRef = FirebaseStorage.getInstance().getReference();
    // email ,firstname and last name ka set text ho raha hai yahan
    nameProf.setText("First Name");
    lastnameProf.setText("Last Name");
    emailProf.setText("Email ");


    getPersonalInfo();


    addImage.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // jece hi image k button pr hoga click to us me typ jae gi image ki
        // saari imgs k liey hotaa h mtlb k sb images uthaa skte hain...
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // image k selection k liey requesting code jo hai upar vo 1234 hai ye dena hota hai
        startActivityForResult(Intent.createChooser(intent, "Select Image"), REQUEST_CODE);
      }
    });

    btnUpdate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {


        uploadPicture();
        Toast.makeText(Edit_Profile.this, edt_email.getText().toString() +edt_name.getText().toString()+edt_lastname.getText().toString(), Toast.LENGTH_SHORT).show();
        LoadingBar = new ProgressDialog(v.getContext());
        LoadingBar.setTitle("UPDATING");
        LoadingBar.setMessage("PLEASE WAIT");
        //ye is liey kiyaa h use taake kahin touch krne pr loadng baar bnd na hoo

        LoadingBar.setCanceledOnTouchOutside(true);
        LoadingBar.show();

      }
    });

    removePicture.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        removeProfilePicture();
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    // agar request code 1234 hi ho aur data null naa na hoo to ye imgURi ka data get kar le
    if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
      imgUri = data.getData();

      try {
        // bitmap hum 0s and 1s k liey use krte hain for images
        //bitmap me hmri images save hoti hain
        Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
        // image set krane k liye
        userPicture.setImageBitmap(bm);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  void getPersonalInfo() {

    // database ka refernce de diya hai yahan par
    databaseReference = FirebaseDatabase.getInstance().getReference();
    // listener add karaa diyaa yahan par
    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override

      // data ko change kr k vps db me bhi to store krana hoga naa
      // to vo data snapshot me ae gaa jis me User ka table hai
      // aur us k childs hain 3  jis me name last name aur email address hain
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        user_id  = firebaseAuth.getCurrentUser().getUid();
        U_Email = firebaseAuth.getCurrentUser().getEmail();

        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
          if(dataSnapshot1.child(user_id).child("firstn").getValue(String.class) != null) {
            U_Fname = dataSnapshot1.child(user_id).child("firstn").getValue(String.class);
          }
          if(dataSnapshot1.child(user_id).child("lastn").getValue(String.class) != null) {
            U_LName = dataSnapshot1.child(user_id).child("lastn").getValue(String.class);
          }
//                    U_Email = dataSnapshot1.child(user_id).child("email_id").getValue(String.class);

          if (dataSnapshot1.child(user_id).child("url").exists())
          {
            // child hai jis me URL ki value get karaa re hain
            U_pic = dataSnapshot1.child(user_id).child("url").getValue(String.class);

          }

        }



        // data set karayaa
        // jo heading par likhna hai .. us k liey bho ae ga ye
        edt_name.setText(U_Fname);
        edt_lastname.setText(U_LName);
        edt_email.setText(U_Email);
        userNameDisplay.setText(U_Fname +" "+ U_LName);
        userEmailDisplay.setText(U_Email);
        if (!(U_pic.isEmpty())) {

          // glide ki lib use hoi hai yahan
          Glide.with(Edit_Profile.this).load(U_pic).into(userPicture);
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }

  void uploadPicture(){

    try {

      // firebase se uth k aarhi h
      //ek hi name ki img ko override na kaarane ki vja se lgaya vi h ..
      final StorageReference ref = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(imgUri));
      // email, name and last name ka get text and trim hogaa
      Log.i("aa",edt_email.getText().toString().trim() +edt_name.getText().toString().trim()+edt_lastname.getText().toString().trim());

      // successor listener ka kaam jis me snapshot bhi le re hain
      ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
          //download Url and sucesslistener
          ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {



            @Override
            public void onSuccess(Uri uri) {
              //String uid = firebaseAuth.getCurrentUser().getUid();
              Toast.makeText(Edit_Profile.this, user_id, Toast.LENGTH_SHORT).show();
              //                            Toast.makeText(Edit_Profile.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
              ImageUpload imageUpload = new ImageUpload(uri.toString());
              url = imageUpload.getUrl();

              // User us = new User(U_Email,U_Fname,U_LName,url);
              User us = new User(edt_email.getText().toString(),edt_name.getText().toString(),edt_lastname.getText().toString(),url);

              Log.i("data", edt_email.getText().toString().trim() + edt_name.getText().toString().trim() + edt_lastname.getText().toString().trim() );
              // database k references ka user table k child ki value set hogaeee on SuccessListener par
              databaseReference.child("Users").child(user_id).setValue(us).addOnSuccessListener(new OnSuccessListener<Void>() {

                @Override
                public void onSuccess(Void aVoid) {
                  Toast.makeText(Edit_Profile.this, "Profile Updated!", Toast.LENGTH_SHORT).show();
                }


              });
            }
          });
        }
      });

    } catch (Exception e) {
      //catch me jo bhi error h vo log me jae gaa
      e.printStackTrace();
      Log.e("Error",e.getMessage());
    }
  }

  public String getImageExt(Uri uri) {
    // content resolver client ki requests accept kre ga  aur use direct kre ga content provider ki trf
    ContentResolver contentResolver = getContentResolver();
// file extension from url
    MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
    // uri laa kr de rhaah hai
    return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
  }

  void removeProfilePicture()
  {
    databaseReference = FirebaseDatabase.getInstance().getReference();
    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        User us = new User(U_Email,U_Fname,U_LName,null);
        databaseReference.child("Users").child(user_id).setValue(us).addOnSuccessListener(new OnSuccessListener<Void>() {
          @Override
          public void onSuccess(Void aVoid) {
            Toast.makeText(Edit_Profile.this, "Updated", Toast.LENGTH_SHORT).show();
            U_pic = "";
            Glide.with(Edit_Profile.this).load(U_pic).into(userPicture);

          }
        });
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }
}
