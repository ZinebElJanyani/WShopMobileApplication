package projetshop.ma;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class ProductsFragment extends Fragment {


    private static final String URL_display = "http://10.0.2.2:8080/getProducts.php";

    Context applicationContext = home_activity.getContextOfApplication();
    private String id_category;
    private TextView msg;
    ProductAdapter productAdapter;
    FragmentManager fragmentManager;
    RecyclerView recyclerView;
    List<Product> myList = new ArrayList<>();


    public ProductsFragment(String idCategory) {
    this.id_category = idCategory;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_products, container, false);

        recyclerView = v.findViewById(R.id.main_container);

        displayProducts(v);

        return v;
    }
    private void displayProducts(View view){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_display,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONArray jsonA = new JSONArray(response);
                    myList = new ArrayList<>();
                    String id,label,price,photo;




                    for(int i=0;i<jsonA.length();i++){
                        String sizes = "";
                        JSONObject jsonObject = jsonA.getJSONObject(i);
                        ArrayList<String>photos = new ArrayList<String>();
                        id = jsonObject.getString("id");
                        label = jsonObject.getString("label");
                        price = jsonObject.getString("price");
                        price+=" DH";
                        JSONArray jsonArray_size = jsonObject.getJSONArray("size");
                        for(int j=0;j<jsonArray_size.length();j++){
                            sizes+=jsonArray_size.get(j)+" /";
                        }
                        //sizes = jsonObject.getString("size");
                        photo= jsonObject.getString("resource");
                        photos.add(photo);

                        myList.add(new Product(id,id_category,photos,sizes,label,price));
                        Toast.makeText(applicationContext,"Articles",Toast.LENGTH_SHORT).show();

                    }

                    setProductsINcarousel();


                } catch (JSONException e) {

                    Toast.makeText(applicationContext,e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(applicationContext,error.toString(),Toast.LENGTH_SHORT).show();
                    }})
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("id_catg",String.valueOf( id_category));


                        return params;
                    }
                    };


        RequestQueue requestQueue = Volley.newRequestQueue(applicationContext);
        requestQueue.add(stringRequest);

    }

    public void setProductsINcarousel(){

        fragmentManager = getParentFragmentManager();
        productAdapter = new ProductAdapter(myList,fragmentManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(applicationContext,2));


    }
}