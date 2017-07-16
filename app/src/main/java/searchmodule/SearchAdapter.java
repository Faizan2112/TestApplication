package searchmodule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.testapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by faizan on 24/02/2017.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    LayoutInflater layoutInflater;
    Context c;
    List<SeachModel> seachModels;

   public SearchAdapter(Context c, List<SeachModel> seachModels) {
        this.c = c;
        this.seachModels = seachModels;
       layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

   }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.card_view_for_search_result, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.lName.setText(seachModels.get(position).searchMName);
        holder.lLocation.setText(seachModels.get(position).searchLocation);
        holder.lFavourite.setText(seachModels.get(position).searchfavourate);
    }

    @Override
    public int getItemCount() {
        return seachModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView lName, lLocation, lFavourite;

        public ViewHolder(View itemView) {
            super(itemView);
            lName = (TextView) itemView.findViewById(R.id.searchTextName);
            lLocation = (TextView) itemView.findViewById(R.id.searchTextName);
            lFavourite = (TextView) itemView.findViewById(R.id.searchTextName);
        }
    }
}
