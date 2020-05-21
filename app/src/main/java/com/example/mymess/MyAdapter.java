package com.example.mymess;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Context c;
    ArrayList<DishForDisplay> dishArrayList;

    public MyAdapter(Context c, ArrayList<DishForDisplay> dishArrayList) {
        this.c = c;
        this.dishArrayList = dishArrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        String dish_name = dishArrayList.get(position).getDish_name();
        String cuisine_name = "Cuisine: " + dishArrayList.get(position).getCuisine();
        String mess_name = "Mess: " + dishArrayList.get(position).getMess();
        String tags = "Tags: ";
        List<String> dish_tags = dishArrayList.get(position).getTags();
        String[] userSelectedTags = dish_tags.toArray(new String[0]);
        int tags_match_count = dishArrayList.get(position).getTags_match_count();
        int tags_mess_count = dishArrayList.get(position).getTags_mess_count();

        for(int i = 0; i < userSelectedTags.length; i++){
            tags = tags + userSelectedTags[i];
            if(i != userSelectedTags.length - 1){
                tags = tags + ",";
            }
        }

        holder.m_textViewTitle.setText(dish_name);
        holder.m_textViewCuisine.setText(cuisine_name);
        holder.m_textViewMess.setText(mess_name);
        holder.m_textViewTags.setText(tags);//Here Tags must come !@#$
        holder.m_textViewNumberOfTagsMatching.setText("Tags Matching with Dish: " + tags_match_count);
        holder.m_textViewNumberOfTagsMatchingwithMess.setText("Tags Matching with Mess: " + tags_mess_count);
        System.out.println(dish_name+ " tags_mess_count: " + tags_mess_count);
        //Fetch Image from Link or Local !@#$ -- Fetched
        Picasso.get().load(dishArrayList.get(position).getImage_path()).into(holder.m_imageView);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                String gDishName = dishArrayList.get(position).getDish_name();
                String gCuisine = "Cuisine: " + dishArrayList.get(position).getCuisine();
                String gMessName = "Mess: " + dishArrayList.get(position).getMess();

                String tags = "Tags: ";
                List<String> gTags = dishArrayList.get(position).getTags();
                String[] userSelectedTags = gTags.toArray(new String[0]);
                for(int i = 0; i < userSelectedTags.length; i++){
                    tags = tags + userSelectedTags[i];
                    if(i != userSelectedTags.length - 1){
                        tags = tags + ",";
                    }
                }

                int gNumMatchingTags = dishArrayList.get(position).getTags_match_count();
                int gNumMessMatchingTags = dishArrayList.get(position).getTags_mess_count();
                String gImage_path = dishArrayList.get(position).getImage_path();

                //get our data with intent

                Intent intent = new Intent(c, SuccessfulMessRegistration.class);
                intent.putExtra("DISH_NAME", gDishName);
                intent.putExtra("CUISINE", gCuisine);
                intent.putExtra("MESS", gMessName);
                intent.putExtra("TAGS", tags);
                intent.putExtra("IMAGE_PATH", gImage_path);

//                intent.putExtra("NumMatchingTags", gNumMatchingTags);
//                intent.putExtra("NumMessMatchingTags", gNumMessMatchingTags);
                c.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return dishArrayList.size();
    }
}
