package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import API.i.UserApi.UserData;
import API.i.projectApi.ProjectData;
import comk.example.admin.navgathi.R;

/**
 * Created by admin on 1/24/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
            Context con;
            UserAdapter.AtmHolder1 pvh1;
            List<UserData> serviceslist;
        public UserAdapter(List<UserData> serviceslist, Context con) {
            this.serviceslist = serviceslist;
            this.con = con;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
            RecyclerView.ViewHolder viewHolder = null;
            if (position == 0) {
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_style, viewGroup, false);
                viewHolder = new ProjectAdapter.AtmHolder(v);
            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (holder instanceof ProjectAdapter.AtmHolder) {
                pvh1 = (UserAdapter.AtmHolder1) holder;
                pvh1.tv_title.setText(serviceslist.get(position).getName());

                // pvh1.im.setImageDrawable(Drawable.createFromPath(serviceslist.get(position).getImage()));
                pvh1.tv_type.setText(serviceslist.get(position).getType());
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

    public static class AtmHolder1 extends RecyclerView.ViewHolder {

        TextView tv_title,tv_type;


        AtmHolder1(View itemView) {
            super(itemView);

            tv_title = (TextView) itemView.findViewById(R.id.textView);

            tv_type = (TextView) itemView.findViewById(R.id.textView3);

        }
    }
}
