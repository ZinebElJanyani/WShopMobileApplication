package projetshop.ma;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesFragment extends Fragment {


    private static final String URL_display = "http://10.0.2.2:8080/getcategories.php";

    Context applicationContext = home_activity.getContextOfApplication();


    private CarouselRecyclerview carouselRecyclerview;
    CategoryAdapter imgAdapter;
    FragmentManager fragmentManager;

    List<Category> myList = new ArrayList<>();
    public CategoriesFragment() {
        // Required empty public constructor
    }



    public static CategoriesFragment newInstance(String param1, String param2) {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();
    
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_categories, container, false);



       /* myList = new ArrayList<>();
        myList.add(new Category("1","https://i.pinimg.com/474x/dd/59/e0/dd59e046e773ac43b4d9877c6454bc4f.jpg"));
        myList.add(new Category("2","https://i.pinimg.com/474x/a1/8b/90/a18b90dd7ebd58cf2b898c8ad7a0db79.jpg"));
        myList.add(new Category("3","https://images.tesetturdunyasi.com.tr/fermuar-detayli-kot-ceket-tsd220709-mavi-125903-20-O.jpg"));
        myList.add(new Category("4","https://images.tesetturdunyasi.com.tr/fermuarli-trenckot-tsd211216-bej-124784-20-B.jpg"));
        myList.add(new Category("5","https://images.tesetturdunyasi.com.tr/oversize-kot-ceket-tsd220508-mavi-123927-20-B.jpg"));
        myList.add(new Category("6","https://images.tesetturdunyasi.com.tr/fermuarli-kisa-kap-tsd211210-taba-116334-19-B.jpg"));
        myList.add(new Category("7","https://images.tesetturdunyasi.com.tr/ekose-ceket-gomlek-tsd211229-vizon-115428-19-B.jpg"));
        myList.add(new Category("8","https://images.tesetturdunyasi.com.tr/fermuarli-deri-kap-tsd5501-haki-113494-18-B.jpg"));
        myList.add(new Category("9","https://images.tesetturdunyasi.com.tr/etegi-lastikli-kot-ceket-tsd5517-haki-113398-18-B.jpg"));

        carouselRecyclerview = v.findViewById(R.id.carouselRecycle);
        imgAdapter = new CategoryAdapter(myList);
        carouselRecyclerview.setAdapter(imgAdapter );
        carouselRecyclerview.set3DItem(true);
        carouselRecyclerview.setAlpha(true);
        carouselRecyclerview.setInfinite(true);*/



        displayCategories(v);

        return v;
    }

    private void displayCategories(View view){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_display,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONArray jsonA = new JSONArray(response);
                    myList = new ArrayList<>();
                    String path,title;
                    int id;

                    for(int i=0;i<jsonA.length();i++){
                        JSONObject jsonObject = jsonA.getJSONObject(i);
                        path = jsonObject.getString("path");
                        title = jsonObject.getString("title");
                        id = jsonObject.getInt("id");
                        myList.add(new Category(title,path,id));

                    }
                    setCategoriesINcarousel(view);


                } catch (JSONException e) {

                    Toast.makeText(applicationContext,e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(applicationContext,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                });


        RequestQueue requestQueue = Volley.newRequestQueue(applicationContext);
        requestQueue.add(stringRequest);

    }

    public void setCategoriesINcarousel(View view){
        carouselRecyclerview = view.findViewById(R.id.carouselRecycle);
        fragmentManager = getParentFragmentManager();
        imgAdapter = new CategoryAdapter(myList,fragmentManager);
        carouselRecyclerview.setAdapter(imgAdapter );
        carouselRecyclerview.set3DItem(true);
        carouselRecyclerview.setAlpha(true);
        carouselRecyclerview.setInfinite(true);
    }
}