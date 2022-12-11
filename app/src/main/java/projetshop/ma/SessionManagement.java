package projetshop.ma;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    final String SESSION_KEY = "userKEY";
    final String NAME = "name";
    final String EMAIL= "email";
    final String IMG= "STRimage";


    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(int id,String name,String email,String stringIMG) {
        editor.putInt(SESSION_KEY, id).commit();
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.putString(IMG, stringIMG);
        editor.apply();
    }

    public HashMap<String,String> userINFO(){
    //to get user information from other context:
        HashMap<String,String> user = new HashMap<>();
        user.put(SESSION_KEY,Integer.toString( sharedPreferences.getInt(SESSION_KEY,-1)));
        user.put(NAME,sharedPreferences.getString(NAME,null));
        user.put(EMAIL,sharedPreferences.getString(EMAIL,null));
        user.put(IMG,sharedPreferences.getString(IMG,null));

        return user;
    }

    public int getSession(){
        return sharedPreferences.getInt(SESSION_KEY,-1);
    }

    public void closeSession(){
        editor.putInt(SESSION_KEY,-1).commit();
    }
}
