package com.codepath.bigheartapp.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.bigheartapp.ComposeActivity;
import com.codepath.bigheartapp.EndlessRecyclerViewScrollListener;
import com.codepath.bigheartapp.PostAdapter;
import com.codepath.bigheartapp.PostDetailsActivity;
import com.codepath.bigheartapp.R;
import com.codepath.bigheartapp.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    // Store variables to use in the home fragment
    private EndlessRecyclerViewScrollListener scrollListener;
    public static ArrayList<Post> posts;
    public static RecyclerView rvPost;
    public static PostAdapter adapter;
    private SwipeRefreshLayout swipeContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Register for the particular broadcast based on ACTION string
        IntentFilter filter = new IntentFilter(PostDetailsActivity.ACTION);
        getActivity().registerReceiver(detailsChangedReceiver, filter);




        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Set created variables to new elements or corresponding layouts
        posts = new ArrayList<>();
        adapter = new PostAdapter(posts, 0);
        rvPost = (RecyclerView) rootView.findViewById(R.id.rvPost);
        rvPost.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPost.setAdapter(adapter);

        // Configure the RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPost.setLayoutManager(linearLayoutManager);

        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                view.post(new Runnable() {
                    @Override
                    public void run() {}
                });
            }
        };

        // Adds the scroll listener and item decoration to RecyclerView
        rvPost.addOnScrollListener(scrollListener);
        rvPost.addItemDecoration(new VerticalSpaceItemDecoration(12));
        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // To keep animation for 4 seconds
                posts.clear();
                adapter.clear();
                loadTopPosts();
            }
        });

        // Scheme colors for animation
        swipeContainer.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );
        loadTopPosts();
        return rootView;
    }

    // load the latest posts
    public void loadTopPosts(){
        final Post.Query postQuery = new Post.Query();
        postQuery.getTop().withUser();
        postQuery.addDescendingOrder(Post.KEY_DATE);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e == null) {
                    adapter.clear();
                    for(int i = 0; i < objects.size(); i++) {
                        posts.add(objects.get(i));
                        adapter.notifyItemInserted(posts.size() - 1);
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to query posts", Toast.LENGTH_LONG).show();
                }
                swipeContainer.setRefreshing(false);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    //put space between cardviews
    public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        // Specify a final variable for space between cardviews
        private final int verticalSpaceHeight;

        // function to set the space height
        public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.top = verticalSpaceHeight;
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        // Unregister the listener when the application is paused
        getActivity().unregisterReceiver(detailsChangedReceiver);
    }

    // Define the callback for what to do when data is received
    private BroadcastReceiver detailsChangedReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

            int resultCode = intent.getIntExtra(getString(R.string.result_code), RESULT_CANCELED);

            if (resultCode == RESULT_OK) {
                Post postChanged = (Post) intent.getSerializableExtra(Post.class.getSimpleName());
                int indexOfChange = -1;
                for (int i = 0; i < posts.size(); i++) {
                    if (posts.get(i).hasSameId(postChanged)) {
                        indexOfChange = i;
                        break;
                    }
                }
                if (indexOfChange != -1) {
                    posts.set(indexOfChange, postChanged);
                    adapter.notifyItemChanged(indexOfChange);
                } else {
                    Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_LONG).show();
                }

            }
        }
    };
}
