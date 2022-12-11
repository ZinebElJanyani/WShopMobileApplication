package projetshop.ma;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.Base64;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import nl.joery.animatedbottombar.AnimatedBottomBar;

public class home_activity extends AppCompatActivity {

    public static Context contextOfApplication;
    private String email, name,id,stringIMG;
    MaterialToolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    View headerView;
    AnimatedBottomBar bottomBar;
    FragmentManager fragmentManager;
    SessionManagement sessionManagement;
    HashMap<String,String> user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        toolbar = findViewById(R.id.topAppBar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationSide);

        sessionManagement = new SessionManagement(home_activity.this);
        user = sessionManagement.userINFO();

        email = user.get(sessionManagement.EMAIL);
        name = user.get(sessionManagement.NAME);
        id = user.get(sessionManagement.SESSION_KEY);
        stringIMG = user.get(sessionManagement.IMG);

        setUserINFO();
        initBottomViews(savedInstanceState);
        initNavView();

        contextOfApplication = getApplicationContext();

        //this.email = findViewById(R.id.homeEMAIL);
        //this.name = findViewById(R.id.nav_userName);

        /*
        Intent intent = getIntent();
        String extraName = intent.getStringExtra("name");
        String extrEmail = intent.getStringExtra("email");*/

    }
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }


    @SuppressLint("NonConstantResourceId")
   private void initBottomViews(Bundle instanceState){
        bottomBar = findViewById(R.id.bottomBar);

        if(instanceState==null){
            bottomBar.selectTabById(R.id.bottomHome,true);
            fragmentManager = getSupportFragmentManager();
            WelcomFragment welcomeFragment = new WelcomFragment();
            fragmentManager.beginTransaction().replace(R.id.bottomFragmentContainer,welcomeFragment).commit();
        }
        bottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {
                Toast.makeText(home_activity.this,"your are already in "+tab.getTitle(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NonNull AnimatedBottomBar.Tab newTab) {

                Fragment fragment = null;
                switch (newTab.getId()) {
                    case R.id.bottomHome:
                        fragment = new WelcomFragment();
                        break;
                    case R.id.bottomFavorite:
                        fragment = new FavoriteFragment();
                        break;
                    case R.id.bottomCart:
                        fragment = new PanierFragment();
                        break;

                }
                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.bottomFragmentContainer, fragment).commit();
                }
            }

        });
    }
    public Bitmap ConvertStringToBitmap(String strIMG){
        byte[] decodedByte = Base64.getDecoder().decode(strIMG);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);

        return  decodedBitmap;
    }
    private void setUserINFO(){
        headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.nav_userName);
        navUsername.setText(name);

        CircleImageView navUserImage = headerView.findViewById(R.id.navProfile_image);
        navUserImage.setImageBitmap(ConvertStringToBitmap(stringIMG));
    }

    private void initNavView(){
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_Open,R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setToolbarNavigationClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));

        toggle.syncState();


        navigationView.bringToFront();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int indice = item.getItemId();

                drawerLayout.closeDrawer(GravityCompat.START);
                switch(indice){
                    case R.id.navhome:
                        Toast.makeText(home_activity.this,"Home",Toast.LENGTH_SHORT).show();
                        fragmentManager = getSupportFragmentManager();
                        WelcomFragment welcomeFragment = new WelcomFragment();
                        fragmentManager.beginTransaction().replace(R.id.bottomFragmentContainer,welcomeFragment).commit();
                        break;

                    case R.id.navcategories:
                        Toast.makeText(home_activity.this,"Categories",Toast.LENGTH_SHORT).show();

                        fragmentManager = getSupportFragmentManager();
                        CategoriesFragment categoriesFragment = new CategoriesFragment();
                        fragmentManager.beginTransaction().replace(R.id.bottomFragmentContainer,categoriesFragment).commit();
                        break;
                    case R.id.navaboutus:
                        Toast.makeText(home_activity.this,"About Us",Toast.LENGTH_SHORT).show();

                        fragmentManager = getSupportFragmentManager();
                        AboutFragment aboutFragment = new AboutFragment();
                        fragmentManager.beginTransaction().replace(R.id.bottomFragmentContainer,aboutFragment).commit();
                        break;
                    case R.id.navlogout:
                        Toast.makeText(home_activity.this,"Logout",Toast.LENGTH_SHORT).show();
                        onlogout();
                        break;

                    case R.id.navpsettings:
                        Toast.makeText(home_activity.this,"Profile",Toast.LENGTH_SHORT).show();
                        fragmentManager = getSupportFragmentManager();
                        ProfileFragment profileFragment  = new ProfileFragment(id);
                        fragmentManager.beginTransaction().replace(R.id.bottomFragmentContainer,profileFragment).commit();
                        break;

                    case R.id.navfeedbacks:
                        Toast.makeText(home_activity.this,"feedbacks",Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.navshare:
                        Toast.makeText(home_activity.this,"Share",Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.navpchangePASS:
                        Toast.makeText(home_activity.this,"Change Password",Toast.LENGTH_SHORT).show();
                        break;

                }
                return true;


            }

        });


    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.END);
        }else {
            super.onBackPressed();
        }
    }
    public void onlogout(){
        SessionManagement sessionManagement = new SessionManagement(home_activity.this);
        sessionManagement.closeSession();

        moveToLoginPage();


    }

    private void moveToLoginPage() {

        Intent intent = new Intent(home_activity.this,login_activity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
