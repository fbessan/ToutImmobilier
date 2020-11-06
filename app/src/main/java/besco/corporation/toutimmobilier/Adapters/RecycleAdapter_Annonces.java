package besco.corporation.toutimmobilier.Adapters;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import besco.corporation.toutimmobilier.Constants.Constant;
import besco.corporation.toutimmobilier.Models.Annonces;
import besco.corporation.toutimmobilier.R;


public class RecycleAdapter_Annonces extends RecyclerView.Adapter<RecycleAdapter_Annonces.MyViewHolder> {
Context context;

    //boolean showingFirst = true;

    private List<Annonces> annoncesList;




    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView typebien,address,montant;
        ImageView photo;
        FrameLayout frame;
        public MyViewHolder(View view) {
            super(view);

            typebien = (TextView) view.findViewById(R.id.typebien);
            address = (TextView) view.findViewById(R.id.address);
            montant = (TextView) view.findViewById(R.id.montant);
            photo = (ImageView) view.findViewById(R.id.photo);

            frame = (FrameLayout) view.findViewById(R.id.frame);

        }

    }


    public RecycleAdapter_Annonces(Context mainActivityContacts, List<Annonces> annoncesList) {
        this.annoncesList = annoncesList;
       this.context = mainActivityContacts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_annonces_list, parent, false);



        return new MyViewHolder(itemView);


    }




    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Annonces annonces = annoncesList.get(position);
        holder.typebien.setText(annonces.getTypeBien());
        holder.address.setText(annonces.getAddress());
        holder.montant.setText(annonces.getAmount());

        Picasso.get()
                .load(Constant.SERVEUR_URL+"assets/upload/toutimmo"+annonces.getPhotoHome()+".png")
                .placeholder(R.drawable.defaultlogo)
                .error(R.drawable.defaultlogo)
                .into(holder.photo);
        Log.d("123-9","img URL "+Constant.SERVEUR_URL+"assets/upload/toutimmo"+annonces.getPhotoHome()+".png");
        Log.d("123-99","img URL "+annonces.getPhotoHome());
        //http://127.0.0.1:8000/assets/upload/toutimmo12802772018100505162315387165830.png
        //holder.photo.setImageResource(annonces.getPhoto());

        //holder.linear.setTag(R.string.id, annonces.getId());
        //holder.linear.setTag(R.string.label,annonces.getName());




    }

    
    @Override
    public int getItemCount() {
        //return partnersList.size();
        return annoncesList == null ? 0 : annoncesList.size();
    }


    public void filterList(ArrayList<Annonces> filteredList) {
        annoncesList = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position){
        if (annoncesList != null) {
            Annonces item = annoncesList.get(position);
            if (item != null) {
                return item.getAnnonces_id();
            }
        }
        return 0;
    }

}


