package com.kelvinlee.appchallenge_java.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kelvinlee.appchallenge_java.R;
import com.kelvinlee.appchallenge_java.helper.APIHelper;
import com.kelvinlee.appchallenge_java.model.BannerModel;
import com.kelvinlee.appchallenge_java.model.CatalogModel;
import com.kelvinlee.appchallenge_java.model.EmployeeModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ContentViewFragment extends Fragment {

    private SwipeRefreshLayout swipeContainer;
    ArrayList<CatalogModel> data = new ArrayList<>();
    MyAdapter mAdapter;
    String team = "";
    Boolean isLoading = false;
    int loadingPage = 0;

    public static Fragment newInstance(String team) {
        ContentViewFragment f = new ContentViewFragment();
        f.team = team;
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));

        this.mAdapter = new MyAdapter(data);
        recyclerView.setAdapter(this.mAdapter);

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoading) {
                    loadingPage = 0;
                    data.clear();
                    loadData();
                }
                swipeContainer.setRefreshing(false);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == data.size() - 1) {
                        isLoading = true;
                        loadData();
                    }
                }
            }
        });

        this.loadData();

        return view;
    }

    public void loadData() {
        APIHelper.loadCatalog(team, this.loadingPage, new APIHelper.CompletionListener() {
            @Override
            public void onCompletion(ArrayList<CatalogModel> list) {
                if (list.size() > 0) {
                    loadingPage += 1;
                    data.addAll(list);
                    mAdapter.notifyDataSetChanged();
                }
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isLoading = false;
                    }
                }, 1000);
            }
        });
    }

    public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private ArrayList<CatalogModel> mData;
        public MyAdapter(ArrayList<CatalogModel> data) {
            this.mData = data;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 0) {
                View view0 = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_cell, parent, false);
                ViewHolder0 holder = new ViewHolder0(view0);
                holder.iv = (ImageView) view0.findViewById(R.id.imageView);

                return holder;
            }else{
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_cell, parent, false);
                ViewHolder1 holder = new ViewHolder1(view1);
                holder.iv = (ImageView) view1.findViewById(R.id.imageView);
                holder.name = (TextView) view1.findViewById(R.id.name);
                holder.position = (TextView) view1.findViewById(R.id.position);
                holder.expertise = (TextView) view1.findViewById(R.id.expertise);

                return holder;
            }
        }
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            switch (holder.getItemViewType()) {
                case 0:
                    ViewHolder0 viewHolder0 = (ViewHolder0)holder;
                    Picasso.get().load(((BannerModel)this.mData.get(position)).url).into(viewHolder0.iv);
                    break;

                case 1:
                    ViewHolder1 viewHolder1 = (ViewHolder1)holder;

                    Picasso.get().load(((EmployeeModel)this.mData.get(position)).avatar).transform(new CropCircleTransformation()).into(viewHolder1.iv);
                    viewHolder1.name.setText(((EmployeeModel)this.mData.get(position)).name);
                    viewHolder1.position.setText(((EmployeeModel)this.mData.get(position)).position);
                    viewHolder1.expertise.setText(((EmployeeModel)this.mData.get(position)).expertise);

                    break;
            }
        }
        @Override
        public int getItemCount() {
            return this.mData.size();
        }
        class ViewHolder0 extends RecyclerView.ViewHolder {
            public ImageView iv;
            public ViewHolder0(View itemView) {
                super(itemView);
            }
        }
        class ViewHolder1 extends RecyclerView.ViewHolder {
            public ImageView iv;
            public TextView name;
            public TextView position;
            public TextView expertise;
            public ViewHolder1(View itemView) {
                super(itemView);
            }
        }
        @Override
        public int getItemViewType(int position) {
            if(this.mData.get(position).type.equals("banner")) {
                return 0;
            }
            return 1;
        }
    }

}
