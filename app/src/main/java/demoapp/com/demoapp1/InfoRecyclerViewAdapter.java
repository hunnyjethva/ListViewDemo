package demoapp.com.demoapp1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InfoRecyclerViewAdapter extends RecyclerView.Adapter<InfoRecyclerViewAdapter.CatNamesViewHolder> {

    private Context mContext;
    ArrayList<InfoModel> userList;

    public InfoRecyclerViewAdapter(Context context, ArrayList<InfoModel> userList) {
        mContext = context;
        this.userList = userList;
    }


    public class CatNamesViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle,txtDEscription;
        ImageView imgFlag;

        public CatNamesViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.textView1);
            txtDEscription = (TextView) itemView.findViewById(R.id.textView2);
            imgFlag = (ImageView) itemView.findViewById(R.id.imageView1);
        }
    }


    @Override
    public CatNamesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflatedView = LayoutInflater.from(mContext).inflate(R.layout.infor_row, viewGroup, false);
        return new CatNamesViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(CatNamesViewHolder viewHolder, int i) {
        try {
            InfoModel user = userList.get(i);
            String imageUrl = user.getImageHref();
            if (imageUrl != null) {
                Picasso.with(mContext)
                        .load(imageUrl)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .fit().centerCrop()
                        .into(viewHolder.imgFlag);

                /*Glide.with(mContext).load(imageUrl).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.5f).into(viewHolder.imgFlag);*/

            }

            viewHolder.txtTitle.setText(user.getTitle());
            if(user.getDescription() != "null") {
                viewHolder.txtDEscription.setText(user.getDescription());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }


}
