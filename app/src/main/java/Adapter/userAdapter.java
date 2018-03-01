package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.List;

import Api.userList.userListData;
import comk.example.admin.navgathi.R;


/**
 * Created by JoJo on 22-11-2016.
 */
public class userAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context con;
    AtmHolder pvh1;
    List<userListData> serviceslist;

    public userAdapter(List<userListData> serviceslist, Context con) {
        this.serviceslist = serviceslist;
        this.con = con;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        RecyclerView.ViewHolder viewHolder = null;
        if (position == 0) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userlist_style, viewGroup, false);
            viewHolder = new AtmHolder(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AtmHolder) {
            pvh1 = (AtmHolder) holder;
            pvh1.tv_type.setText(serviceslist.get(position).getType());

            // pvh1.im.setImageDrawable(Drawable.createFromPath(serviceslist.get(position).getImage()));
            pvh1.tv_name.setText(serviceslist.get(position).getName());
            pvh1.tv_email.setText(serviceslist.get(position).getEmail());
          //  pvh1.ratingBar.setRating(Float.parseFloat(String.valueOf(serviceslist.get(position).getRating())));


        }
    }


    public int getItemViewType(int position) {
        int a = 0;
        return a;
    }

    @Override
    public int getItemCount() {
        return serviceslist.size();
    }

    public static class AtmHolder extends RecyclerView.ViewHolder {

        TextView tv_type,tv_name,tv_email;


        AtmHolder(View itemView) {
            super(itemView);

            tv_type = (TextView) itemView.findViewById(R.id.textView);
            tv_name = (TextView) itemView.findViewById(R.id.textView1);
            tv_email = (TextView) itemView.findViewById(R.id.textView2);

        }
    }
}

