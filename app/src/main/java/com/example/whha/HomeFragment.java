package com.example.whha;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.whha.model.PublicPost;
import com.example.whha.utils.GetPostList;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    final static String TAG = "HomeFragment";

    // 每次十个，每行
    private int Limit = 10;
    private int Offset = 1;
    private String mParam1;
    private String mParam2;

    public HomeFragment() {

    }

    public List<PublicPost> data;
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView recyclerView;

    private RecyclerViewAdapter adapter;

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    /*
    刷新方式，包括头部和尾部
     */
    private void setRefresh(){
        //head
        smartRefreshLayout.setRefreshHeader(new BezierRadarHeader(getActivity()));
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                smartRefreshLayout.finishRefresh(5000);
                Thread Refresh = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        data.clear();
                        // 重头刷新
                        Limit = 10;
                        Offset = 1;
                        if(setData() == true){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), "刷新失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                Refresh.start();

            }
        });
        //foot
        smartRefreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()));
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                smartRefreshLayout.finishLoadMore(2000);
                Thread Refresh = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Offset += Limit;
                        Limit = 5;
                        if(addData() == true){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), "没有更多了", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                Refresh.start();
            }
        });

    }
    /*
    初始化数据
    个人想法：上拉刷新的时候，也是调用这个，直接上来把数据清空得了.....
     */
    private boolean setData(){
        List<PublicPost> tempdata = GetPostList.getPublicPost(Limit, Offset);
        Log.i(TAG, "setData: " + tempdata);
        if(tempdata == null || tempdata.size() == 0)
            return false;
        for(PublicPost tmp : tempdata){
            data.add(data.size(), tmp);
        }
        return true;
    }
    /*
    添加数据
     */
    private boolean addData(){
        List<PublicPost> tempdata = GetPostList.getPublicPost(Limit, Offset);
        if(tempdata == null || tempdata.size() == 0)
            return false;
        for(PublicPost tmp : tempdata){
            data.add(data.size(), tmp);
        }
        return true;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        smartRefreshLayout = getActivity().findViewById(R.id.smart_refresh_layout);
        recyclerView = getActivity().findViewById(R.id.recycler_view);
        data = new ArrayList<>();
        Thread getdata = new Thread(new Runnable() {
            @Override
            public void run() {
                setData();
            }
        });
        getdata.start();
        setRefresh();
        setAdapter(true, false);
    }

    private void setAdapter(boolean isVertical, boolean isReverse) {
        adapter = new RecyclerViewAdapter(this.data);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(isVertical? RecyclerView.VERTICAL:RecyclerView.HORIZONTAL);
        manager.setReverseLayout(false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);


    }
}