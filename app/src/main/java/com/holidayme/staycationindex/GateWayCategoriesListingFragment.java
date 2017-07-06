package com.holidayme.staycationindex;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.holidayme.activities.R;
import java.util.ArrayList;

/**
 * Created by arshad.shaikh on 3/1/2017.
 */

public class GateWayCategoriesListingFragment  extends Fragment implements View.OnClickListener{

    private  Context context;
    private  View view;
    private RecyclerView gatewayCategoryRecyclerView;
    private LinearLayoutManager linearLayoutManager;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context= getActivity();
        linearLayoutManager= new LinearLayoutManager(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.staycation_category, container, false);

        initUI();
        getData();
        return  view;
    }

    private void initUI() {

        gatewayCategoryRecyclerView= (RecyclerView) view.findViewById(R.id.gatewayCategoryRecyclerView);
        gatewayCategoryRecyclerView.setLayoutManager(linearLayoutManager);
        ImageView toolbarBackImageView = (ImageView) view.findViewById(R.id.toolbarBackImageView);

        ((TextView) view.findViewById(R.id.toolbarTitleTextView)).setText("Categories");
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        toolbarBackImageView.setOnClickListener(this);

    }

    private void getData() {

        ArrayList<SampleData> sampleDataArrayList = new ArrayList<>();

        sampleDataArrayList.add(new SampleData("France","20 Gateways","https://hme-media-2.azureedge.net/hotels/images/156706/park-plaza-westminster-bridge-london--34.jpg"));
        sampleDataArrayList.add(new SampleData("United Arab Emirates","20 Gateways","https://hme-media-1.azureedge.net/hotels/images/156706/park-plaza-westminster-bridge-london--33.jpg"));
        sampleDataArrayList.add(new SampleData("NewYork","20 Gateways","https://hme-media-3.azureedge.net/hotels/images/156706/park-plaza-westminster-bridge-london--32.jpg"));
        sampleDataArrayList.add(new SampleData("Russia","20 Gateways","https://hme-media-2.azureedge.net/hotels/images/156706/park-plaza-westminster-bridge-london--37.jpg"));
        sampleDataArrayList.add(new SampleData("Dubai","20 Gateways","https://hme-media-2.azureedge.net/hotels/images/156706/park-plaza-westminster-bridge-london--37.jpg"));
        sampleDataArrayList.add(new SampleData("Bangkok","20 Gateways","https://hme-media-1.azureedge.net/hotels/images/156706/park-plaza-westminster-bridge-london--36.jpg"));
        sampleDataArrayList.add(new SampleData("India","20 Gateways","https://hme-media-3.azureedge.net/hotels/images/156706/park-plaza-westminster-bridge-london--35.jpg"));
        sampleDataArrayList.add(new SampleData("Nepal","20 Gateways","https://hme-media-2.azureedge.net/hotels/images/156706/park-plaza-westminster-bridge-london--37.jpg"));
        sampleDataArrayList.add(new SampleData("Korea","20 Gateways","https://hme-media-2.azureedge.net/hotels/images/156706/park-plaza-westminster-bridge-london--37.jpg"));
        sampleDataArrayList.add(new SampleData("Paris","20 Gateways","https://hme-media-2.azureedge.net/hotels/images/156706/park-plaza-westminster-bridge-london--37.jpg"));
        sampleDataArrayList.add(new SampleData("Lindon","20 Gateways","https://hme-media-2.azureedge.net/hotels/images/156706/park-plaza-westminster-bridge-london--37.jpg"));
        sampleDataArrayList.add(new SampleData("Lindon","20 Gateways","https://hme-media-2.azureedge.net/hotels/images/156706/park-plaza-westminster-bridge-london--37.jpg"));

        GatewayCategoryAdapter gatewayCategoryAdapter = new GatewayCategoryAdapter(context, sampleDataArrayList);
        gatewayCategoryRecyclerView.setAdapter(gatewayCategoryAdapter);
        gatewayCategoryAdapter.notifyDataSetChanged();

        gatewayCategoryAdapter.setOnItemClickListener(new GatewayCategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.toolbarBackImageView:

                getActivity().onBackPressed();

                break;


        }
    }
}
