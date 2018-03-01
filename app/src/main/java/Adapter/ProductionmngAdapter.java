package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import Api.projectApi.ProjectData;
import comk.example.admin.navgathi.R;

/**
 * Created by admin on 2/26/2018.
 */

public class ProductionmngAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context con;
    ProjectAdapter.AtmHolder pvh1;
    List<ProjectData> serviceslist;

    public ProductionmngAdapter(List<ProjectData> serviceslist, Context con) {
        this.serviceslist = serviceslist;
        this.con = con;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        RecyclerView.ViewHolder viewHolder = null;
        if (position == 0) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pjctlist_style, viewGroup, false);
            viewHolder = new ProjectAdapter.AtmHolder(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ProjectAdapter.AtmHolder) {
            pvh1 = (ProjectAdapter.AtmHolder) holder;
            pvh1.tv_title.setText(serviceslist.get(position).getTitle());

            // pvh1.im.setImageDrawable(Drawable.createFromPath(serviceslist.get(position).getImage()));
            pvh1.tv_site.setText(serviceslist.get(position).getSite());
            pvh1.tv_mgr.setText(serviceslist.get(position).getMgr());
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

        TextView tv_title,tv_site,tv_mgr;


        AtmHolder(View itemView) {
            super(itemView);

            tv_title = (TextView) itemView.findViewById(R.id.textView);
            tv_site = (TextView) itemView.findViewById(R.id.textView2);
            tv_mgr = (TextView) itemView.findViewById(R.id.textView3);

        }
    }
}

