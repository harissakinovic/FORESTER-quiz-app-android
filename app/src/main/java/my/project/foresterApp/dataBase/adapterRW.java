package my.project.foresterApp.dataBase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import my.project.foresterApp.R;

public class adapterRW extends RecyclerView.Adapter<adapterRW.ViewHolder> {

    List<modelResults> newList;
    Context context;

    public adapterRW(List<modelResults> newList, Context context) {
        this.newList = newList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_results_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        modelResults modelResults=newList.get(position);

        holder.name.setText(modelResults.getName());
        holder.points.setText(modelResults.getPoints()+"");
    }

    @Override
    public int getItemCount() {
        return newList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, points;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.nameResultsLayout);
            points=itemView.findViewById(R.id.pointsResultsLayout);
        }
    }
}
