package com.example.oldhabitsdiehard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FollowerAdapter extends ArrayAdapter<String> {
    private Context context;
    private User user;
    UserDatabase db = UserDatabase.getInstance();


    /**
     * Constructor
     * @param context
     * @param user
     */
    public FollowerAdapter(Context context, User user) {
        super(context, 0, user.getFollowers());
        this.user = user;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.followers_view_content, parent,false);
        }

        String follower = user.getFollowers().get(position);

        TextView followerUser = view.findViewById(R.id.follower_user);
        followerUser.setText(follower);

        Button deleteFollower = view.findViewById(R.id.delete_follower);

        deleteFollower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.getFollowers().remove(follower);
                User secondUser = db.getUser(follower);
                secondUser.removeFollowing(user.getUsername());
                db.updateUser(user);
                db.updateUser(secondUser);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
