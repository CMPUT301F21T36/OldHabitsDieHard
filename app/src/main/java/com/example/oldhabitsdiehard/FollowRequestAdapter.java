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

public class FollowRequestAdapter extends ArrayAdapter<FollowRequest> {
    private Context context;
    private User user;

    /**
     * Constructor
     * @param context
     * @param user
     */
    public FollowRequestAdapter(Context context, User user) {
        super(context, 0, user.getFollowRequests());
        this.user = user;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.followrequest_content, parent,false);
        }
        // Retrieve associated FollowRequest object
        FollowRequest followRequest = user.getFollowRequests().get(position);

        // Text
        TextView requestText = view.findViewById(R.id.requested_user);
        requestText.setText(followRequest.getFollower());

        // Button functionality
        Button confirmButton = view.findViewById(R.id.confirm_request);
        Button deleteButton = view.findViewById(R.id.delete_follower);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                followRequest.accept();
                user.getFollowRequests().remove(followRequest);
                notifyDataSetChanged();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                followRequest.deny();
                user.getFollowRequests().remove(followRequest);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
