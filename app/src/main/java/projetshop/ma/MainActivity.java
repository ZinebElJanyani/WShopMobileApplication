package projetshop.ma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void onStart(){
        super.onStart();
        //check if the user is logged in so we will redirect directly to the home page
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        int userID = sessionManagement.getSession();
        if(userID !=-1){
            Intent intent = new Intent(MainActivity.this,home_activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    public void onRegister(View view){

        Intent intent = new Intent(MainActivity.this,register_activity.class);
        startActivity(intent);
       // finish();
    }
    public void onLogin(View view){
        Intent intent = new Intent(MainActivity.this,login_activity.class);
        startActivity(intent);
        //finish();
    }
}