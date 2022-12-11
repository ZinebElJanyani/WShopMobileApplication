package projetshop.ma;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductsViewHolder> {
    private List<Product> list ;
    private FragmentManager fragmentManager;
    public ProductAdapter(List<Product> products ,FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
        this.list = products;

    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_product,parent,false);
        return new ProductAdapter.ProductsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {

        Product product = list.get(position);
        Glide.with( holder.productImage).load(product.getFirstPhoto())
                .override(800, 800)
                .into(holder.productImage);

        holder.label.setText(product.getLabel());
        holder.price.setText(String.valueOf(product.getUnit_price()));
        holder.size.setText(product.getSize());

        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductFragment productFragment = new ProductFragment(product.getId(),product.getSize(),product.getId_categoriy(),fragmentManager);
                fragmentManager.beginTransaction().replace(R.id.bottomFragmentContainer,productFragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder{
        ImageView productImage;
        TextView label,price,size;
        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_img);
            label = itemView.findViewById(R.id.label);
            price = itemView.findViewById(R.id.unit_price);
            size = itemView.findViewById(R.id.size);

        }
    }
}
