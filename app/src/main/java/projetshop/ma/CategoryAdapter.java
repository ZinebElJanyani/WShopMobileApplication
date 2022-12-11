package projetshop.ma;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview;
import com.jackandphantom.carouselrecyclerview.view.ReflectionImageView;

import java.util.ArrayList;
import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ImagesViewHolder> {

    private List<Category> list ;
    private FragmentManager fragmentManager;

    public CategoryAdapter(List<Category> imgs, FragmentManager frag){

        this.list = imgs;
        this.fragmentManager = frag;
    }


    @NonNull
    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_items,parent,false);
        return new ImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesViewHolder holder, int position) {
       Category category = list.get(position);

        Glide.with( holder.categoryImage).load(category.getPath())
                .override(800, 800)
                .into(holder.categoryImage);
        holder.categoryTitle.setText(category.getTitle());


        holder.categoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductsFragment productsFragment = new ProductsFragment(String.valueOf(category.getId()));
                fragmentManager.beginTransaction().replace(R.id.bottomFragmentContainer,productsFragment).commit();
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ImagesViewHolder extends RecyclerView.ViewHolder{
        ReflectionImageView categoryImage;
        TextView categoryTitle;
        public ImagesViewHolder(@NonNull View itemView) {

            super(itemView);
            categoryImage = itemView.findViewById(R.id.categorieImage);
            categoryTitle = itemView.findViewById(R.id.categorieName);

        }
    }
}
