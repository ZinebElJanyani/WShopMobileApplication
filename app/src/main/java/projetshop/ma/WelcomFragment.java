package projetshop.ma;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WelcomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WelcomFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private ImageSlider imageSlider;
    private String mParam2;

    public WelcomFragment() {
        // Required empty public constructor
    }



    public static WelcomFragment newInstance(String param1, String param2) {
        WelcomFragment fragment = new WelcomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_welcome, container, false);
        this.imageSlider = v.findViewById(R.id.sliderWelcome);
//#8ACF5732
        ArrayList<SlideModel> slideModelList = new ArrayList<>();
        slideModelList.add(new SlideModel(R.drawable.promotion1,"Discover owr new collection",null));
        slideModelList.add(new SlideModel(R.drawable.promotion2,null));
        slideModelList.add(new SlideModel(R.drawable.promotion3, null));
        slideModelList.add(new SlideModel(R.drawable.promotion4, null));
        slideModelList.add(new SlideModel(R.drawable.promotion5,null));
        slideModelList.add(new SlideModel(R.drawable.promotion6,"And so more ",null));


        imageSlider.setImageList(slideModelList);
        return v;


    }
}