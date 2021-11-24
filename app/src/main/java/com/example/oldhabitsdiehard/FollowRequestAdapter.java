package com.example.oldhabitsdiehard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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
        TextView requestText = view.findViewById(R.id.request_text);
        requestText.setText(followRequest.getFollower());

        // Button functionality
        ImageButton acceptButton = view.findViewById(R.id.accept_button);
        ImageButton denyButton = view.findViewById(R.id.deny_button);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                followRequest.accept();
                user.getFollowRequests().remove(followRequest);
                notifyDataSetChanged();
            }
        });

        denyButton.setOnClickListener(new View.OnClickListener() {
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
