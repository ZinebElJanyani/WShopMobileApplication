package projetshop.ma;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.tv.TvInputService;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class login_activity extends AppCompatActivity {

    private EditText Password ;
    private EditText Login ;
    private Button btn_login;
    private String Login_URL = "http://10.0.2.2:8080/login.php";
    private AlertDialog.Builder builder;
    private ProgressBar loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.Password = findViewById(R.id.loginPassword);
        this.Login = findViewById(R.id.loginEmail);
        this.btn_login = findViewById(R.id.btnLogin);
        this.builder = new AlertDialog.Builder(this);
        this.loading = findViewById(R.id.loadingLogin);

        this.btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }
    //fin de oncreate()
    public void login(){
        loading.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.GONE);

        String password = this.Password.getText().toString();
        String login = this.Login.getText().toString();

        if(login.isEmpty() || password.isEmpty()){
            message("Some fields are empty...!",null);
        }else{
            StringRequest stringRequest = new StringRequest(Request.Method.POST, this.Login_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int code =(int)jsonObject.getInt("code");
                                String msg = jsonObject.getString("message");
                                String name = jsonObject.getString("name");
                                String stringIMG = jsonObject.getString("StringImg");


                                int userID = jsonObject.getInt("id");
                                    builder.setTitle("login");
                                    builder.setMessage(msg);
                                    displayAlert(code,name,login,userID,stringIMG);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                message("login failed :(", e);


                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            message("login failed :(", error);

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("email", login);
                    params.put("password", password);
                    return params;
                }
            };

            //fin de stringRequest
            stringRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }
    public void message(String msg, Object error){
        if(error ==null) {
            Toast.makeText(login_activity.this, msg , Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(login_activity.this,msg +error.toString(),Toast.LENGTH_SHORT).show();
        }
        loading.setVisibility(View.GONE);
        btn_login.setVisibility(View.VISIBLE);
    }

    public void displayAlert(final int code,String n, String eml,int userID,String stringimg){

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(code == 0){
                    Password.setText("");
                    Login.setText("");
                    loading.setVisibility(View.GONE);
                    btn_login.setVisibility(View.VISIBLE);
                }
                if(code == 1){

                createUserSession(userID,n,eml,stringimg);


                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void createUserSession(int userID,String name, String email,String strIMG){
        //creation de la session
        SessionManagement sessionManagement = new SessionManagement(login_activity.this);
        sessionManagement.saveSession(userID,name,email, strIMG);

        //redirection vers home page
        Intent intent = new Intent(login_activity.this,home_activity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }



}


