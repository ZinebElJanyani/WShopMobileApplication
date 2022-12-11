package projetshop.ma;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String URL_EDITPHOTO = "http://10.0.2.2:8080/EditUserPhoto.php";
    private static final String URL_INFO = "http://10.0.2.2:8080/getUserInfo.php";
    private static final String URL_EDIT = "http://10.0.2.2:8080/EditUser.php";


    // TODO: Rename and change types of parameters

    SessionManagement sessionManagement;
    HashMap<String,String> user;
    String id;
    String name,email,phone,age;
    TextView textName;
    EditText editName,editPhone,editEmail,editAge;
    CircleImageView profilePhoto;
    Button btnEditPhoto;
    private Bitmap bitmap;
    Button btnSave;
    Button btnEdit;
    NavigationView navigationView;
    View headerView;
    Context applicationContext = home_activity.getContextOfApplication();


    public ProfileFragment(String id) {
    this.id = id;

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        textName =v.findViewById(R.id.textuserName);

        editEmail = v.findViewById(R.id.edit_userEmail);
        editName =v.findViewById(R.id.edit_userName);

         navigationView = (NavigationView) getActivity().findViewById(R.id.navigationSide);
        headerView = navigationView.getHeaderView(0);

        editPhone = v.findViewById(R.id.edit_userphone);

        editAge = v.findViewById(R.id.edit_userAge);

        profilePhoto= v.findViewById(R.id.profileImage);

        displayUserInfo();

        btnSave = v.findViewById(R.id.savebtn);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSave.setVisibility(View.GONE);
                btnEdit.setVisibility(View.VISIBLE);
                saveUserInfo();
                editName.setEnabled(false);
                editEmail.setEnabled(false);
                editAge.setEnabled(false);
                editPhone.setEnabled(false);
            }
        });
        btnEdit = v.findViewById(R.id.editbtn);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEdit.setVisibility(View.GONE);
                btnSave.setVisibility(View.VISIBLE);

                editName.setEnabled(true);
                editEmail.setEnabled(true);
                editAge.setEnabled(true);
                editPhone.setEnabled(true);
            }
        });
        btnEditPhoto = v.findViewById(R.id.btnEditPhoto);
        btnEditPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFile();
            }
        });

        return v;
    }
    private void displayUserInfo(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("code");
                    String stringIMG = jsonObject.getString("encodedIMG");
                     name = jsonObject.getString("name");
                     email = jsonObject.getString("email");
                     phone = jsonObject.getString("phone");
                     age = jsonObject.getString("age");

                    if(code==1){

                        Bitmap imgB = ConvertStringToBitmap(stringIMG);
                        textName.setText(name);
                        editEmail.setText(email);
                        editName.setText(name);
                        if(!phone.equals("rien")){
                            editPhone.setText(phone);
                        }
                        if(!age.equals("rien")){
                            editAge.setText(age);
                        }
                        if(!stringIMG.equals("rien")){
                            profilePhoto.setImageBitmap(imgB);
                        }
                    }

                } catch (JSONException e) {

                    Toast.makeText(applicationContext,e.toString(),Toast.LENGTH_SHORT).show();

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressDialog.dismiss();
                        Toast.makeText(applicationContext,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(applicationContext);
        requestQueue.add(stringRequest);
    }

    private void saveUserInfo(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("code");
                    String message = jsonObject.getString("message");
                    if(code==1){
                        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show();
                    textName.setText(editName.getText().toString());
                    }
                    if(code==0){
                        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {

                    Toast.makeText(applicationContext,"koko"+e.toString(),Toast.LENGTH_SHORT).show();

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(applicationContext,"lolo"+error.toString(),Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",id);
                params.put("name",editName.getText().toString());
                params.put("email",editEmail.getText().toString());
                params.put("age",editAge.getText().toString());
                params.put("phone",editPhone.getText().toString());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(applicationContext);
        requestQueue.add(stringRequest);
    }


    private void chooseFile(){
        Intent galleryPhoto =  new Intent();
        galleryPhoto .setType("image/*");
        galleryPhoto .setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(galleryPhoto,1);
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,@NonNull Intent Imgdata){
        super.onActivityResult(requestCode,resultCode,Imgdata);


        if(requestCode==1 && resultCode==RESULT_OK && Imgdata!=null && Imgdata.getData()!=null){
            Uri ImagPath = Imgdata.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(applicationContext.getContentResolver(),ImagPath);
                UploadPicture(id,ConvertBitmapToString(bitmap));

                profilePhoto.setImageBitmap(bitmap);
                CircleImageView navUserImage = headerView.findViewById(R.id.navProfile_image);
                navUserImage.setImageBitmap(bitmap);


            }catch(IOException e){
                e.printStackTrace();
            }


        }
    }



    private void UploadPicture(final String id,final String photo) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDITPHOTO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            //progressDialog.dismiss();
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("code");
                    String message = jsonObject.getString("message");
                    if(code==1){
                        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show();
                    }
                    if(code==0){
                        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    //progressDialog.dismiss();
                    Toast.makeText(applicationContext,"koko"+e.toString(),Toast.LENGTH_SHORT).show();

                }
            }
        },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //progressDialog.dismiss();
                    Toast.makeText(applicationContext,"lolo"+error.toString(),Toast.LENGTH_SHORT).show();
                }
            })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                params.put("id",id);
                params.put("photo",photo);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(applicationContext);
        requestQueue.add(stringRequest);

    }

    public String ConvertBitmapToString(Bitmap bitm){
        ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
        Log.i("original", bitm.getWidth()+"-"+bitm.getHeight());

        bitm.compress(Bitmap.CompressFormat.JPEG,20,byteArrayOutputStream);


        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String strIMAG = Base64.getEncoder().encodeToString(imageByteArray);
        return strIMAG;
    }

    public Bitmap ConvertStringToBitmap(String strIMG){
        byte[] decodedByte = Base64.getDecoder().decode(strIMG);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);

        return  decodedBitmap;
    }

}