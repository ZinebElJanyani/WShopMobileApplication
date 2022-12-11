package projetshop.ma;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment {

    String id;
    String sizes;
    String id_catg;
    ImageButton backbtn;
    List<Integer> imgList = new ArrayList<>();
    ViewPager viewPager;
    WormDotsIndicator dotsIndicator;
    FragmentManager fragmentManager;

    public ProductFragment(String id , String sizes, String idCategory, FragmentManager fragmentManager) {
        this.id = id;
        this.sizes = sizes;
        this.id_catg = idCategory;
        this.fragmentManager = fragmentManager;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product, container, false);
        backbtn = v.findViewById(R.id.back_btn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductsFragment productsFragment = new ProductsFragment(id_catg);
                fragmentManager.beginTransaction().replace(R.id.bottomFragmentContainer,productsFragment).commit();
            }
        });
        return v;
    }
}