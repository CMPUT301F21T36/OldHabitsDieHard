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

public class FollowingAdapter extends ArrayAdapter<String> {
    private Context context;
    private User user;
    UserDatabase db = UserDatabase.getInstance();

    /**
     * Constructor
     * @param context
     * @param user
     */
    public FollowingAdapter(Context context, User user) {
        super(context, 0, user.getFollowing());
        this.user = user;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.following_view_content, parent,false);
        }

        String follow = user.getFollowing().get(position);

        TextView followingUser = view.findViewById(R.id.following_user);
        followingUser.setText(follow);

        Button unFollowingButton = view.findViewById(R.id.unfollow_button);

        unFollowingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.getFollowing().remove(follow);
                User secondUser = db.getUser(follow);
                secondUser.removeFollower(user.getUsername());
                db.updateUser(user);
                db.updateUser(secondUser);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}


