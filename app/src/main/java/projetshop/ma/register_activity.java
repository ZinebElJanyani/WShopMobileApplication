package projetshop.ma;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.HashMap;
import java.util.Map;

public class register_activity extends AppCompatActivity {
    private EditText Name,Email,Password,ConfirmPassword;
    private Button btn_regist;
    private ProgressBar loading;
    private static String URL_REGIST = "http://10.0.2.2:8080/register.php";
    AlertDialog.Builder builder;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_signup);

        this.Name = findViewById(R.id.Reg_name);
        this.Email = findViewById(R.id.Reg_textEmail);
        this.Password = findViewById(R.id.Reg_textPassword);
        this.ConfirmPassword = findViewById(R.id.Reg_confirmPass);
        this.loading = findViewById(R.id.loading);
        this.btn_regist = findViewById(R.id.btnSingup);
        this.builder = new AlertDialog.Builder(this);

        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Regist();
            }
        });

   }
    private void Regist(){

        loading.setVisibility(View.VISIBLE);
        btn_regist.setVisibility(View.GONE);

        final String name = this.Name.getText().toString();
        final String passsword = this.Password.getText().toString();
        final String email = this.Email.getText().toString();
        final String confirmPass = this.ConfirmPassword.getText().toString();

        if(name.isEmpty() || email.isEmpty() || passsword.isEmpty()  || confirmPass.isEmpty()) {
            message("Some fields are empty...!", null);

            loading.setVisibility(View.GONE);
            btn_regist.setVisibility(View.VISIBLE);

        }

        else if(!(passsword.equals(confirmPass))){
            builder.setTitle("Something went wrong....");
            builder.setMessage("Your passwords are not matching...");
            displayAlert(0);


        }else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, this.URL_REGIST,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int code =(int)jsonObject.getInt("code");
                                String msg = jsonObject.getString("message");

                                if (code == 1) {
                                    message(msg, null);
                                }
                                else if(code == 2){
                                    builder.setTitle("registration is successful :)");
                                    builder.setMessage(msg);
                                    displayAlert(2);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                message("registration failed :(", e);


                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            message("registration failed :(", error);

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", name);
                    params.put("email", email);
                    params.put("password", passsword);
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

    public void message(String message,Object error){
        if(error ==null) {
            Toast.makeText(register_activity.this, message , Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(register_activity.this,message+error.toString(),Toast.LENGTH_SHORT).show();
        }
        loading.setVisibility(View.GONE);
        btn_regist.setVisibility(View.VISIBLE);
        if(!message.equals( "Some fields are empty...!")) {
            Name.setText("");
            Email.setText("");
            Password.setText("");
            ConfirmPassword.setText("");
        }
    }
     public void displayAlert(final int code){

       builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {
               if(code == 0){
                   Password.setText("");
                   ConfirmPassword.setText("");
                   loading.setVisibility(View.GONE);
                   btn_regist.setVisibility(View.VISIBLE);
               }
               if(code == 2){
                   login();

               }
           }
     });

       AlertDialog alertDialog = builder.create();
        alertDialog.show();
     }
     public void login(){
         Intent intent = new Intent(register_activity.this,login_activity.class);
         startActivity(intent);
         finish();
     }


}

