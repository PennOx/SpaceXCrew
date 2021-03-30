package tk.pankajb.spacexcrew;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import tk.pankajb.spacexcrew.Models.CrewMember;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context context;
    private CrewMember[] crewMembers;

    RecyclerAdapter(Context context, CrewMember[] crewMembers){
        this.context = context;
        this.crewMembers = crewMembers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CrewMember currentMember = crewMembers[position];

        holder.agency.setText(currentMember.getAgency());
        holder.name.setText(currentMember.getName());
        holder.status.setText(currentMember.getStatus());
        holder.wiki.setText(currentMember.getWikipedia());
        holder.wiki.setTextColor(Color.BLUE);
        holder.wiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(currentMember.getWikipedia()));
                context.startActivity(intent);
            }
        });
        Glide.with(context).load(currentMember.getImage()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return crewMembers.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        View view;
        ImageView image;
        TextView name;
        TextView status;
        TextView agency;
        TextView wiki;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;
            image = itemView.findViewById(R.id.item_image);
            name = itemView.findViewById(R.id.item_name);
            status = itemView.findViewById(R.id.item_status);
            agency = itemView.findViewById(R.id.item_agency);
            wiki = itemView.findViewById(R.id.item_wiki);
        }
    }
}
