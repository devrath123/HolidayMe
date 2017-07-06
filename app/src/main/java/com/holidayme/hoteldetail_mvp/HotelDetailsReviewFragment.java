package com.holidayme.hoteldetail_mvp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.holidayme.activities.R;
import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.activities.util.NonScrollListView;
import com.holidayme.adapter.ReviewAdapter;
import com.holidayme.adapter.SummeryAdapter;
import com.holidayme.adapter.TopReviewAdapter;
import com.holidayme.fragments.BaseFragment;
import com.holidayme.response.HotelDetailInfoResponse;
import com.holidayme.response.TripAdviserDataResponse;

/**
 * Created by santosh.patar on 04-09-2015.
 */
public class HotelDetailsReviewFragment extends BaseFragment implements IHotelDetailView.IHotelDetailReviewView{

    private View rootView;
    private static HotelDetailInfoResponse hotelDetailInfoResponse;
    private static TripAdviserDataResponse tripAdviserDataResponse;
    private SeekBar excellentReviewSeekBar,veryGoodReviewSeekBar,averageReviewSeekBar, poorReviewSeekBar, terribleReviewSeekBar;


    static HotelDetailsReviewFragment newInstance(HotelDetailInfoResponse hotelDetailInfoResponse, TripAdviserDataResponse tripAdviserDataResponse) {
        HotelDetailsReviewFragment.hotelDetailInfoResponse = hotelDetailInfoResponse;
        HotelDetailsReviewFragment.tripAdviserDataResponse = tripAdviserDataResponse;
        return new HotelDetailsReviewFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.hotel_detail_review_fragment, container, false);
        setHasOptionsMenu(true);


        return rootView;
    }




    @SuppressLint("SetTextI18n")
    @Override
    public void initUIElements() {

        excellentReviewSeekBar = (SeekBar) rootView.findViewById(R.id.excellentReviewSeekBar);
        veryGoodReviewSeekBar = (SeekBar) rootView.findViewById(R.id.veryGoodReviewSeekBar);
        averageReviewSeekBar = (SeekBar) rootView.findViewById(R.id.averageReviewSeekBar);
        poorReviewSeekBar = (SeekBar) rootView.findViewById(R.id.poorReviewSeekBar);
        terribleReviewSeekBar = (SeekBar) rootView.findViewById(R.id.terribleReviewSeekBar);
        try {
            ((TextView) rootView.findViewById(R.id.excellentRatingTextView)).setText(tripAdviserDataResponse.getTripAdvisorDetail().getReviewRatingCount().getExcellent()+"");
            ((TextView) rootView.findViewById(R.id.veryGoodRatingTextView)).setText(tripAdviserDataResponse.getTripAdvisorDetail().getReviewRatingCount().getVeryGood()+"");
            ((TextView) rootView.findViewById(R.id.averageRatingTextView)).setText(tripAdviserDataResponse.getTripAdvisorDetail().getReviewRatingCount().getAverage()+"");
            ((TextView) rootView.findViewById(R.id.poorRatingTextView)).setText(tripAdviserDataResponse.getTripAdvisorDetail().getReviewRatingCount().getPoor()+"");
            ((TextView) rootView.findViewById(R.id.terribleRatingTextView)).setText(tripAdviserDataResponse.getTripAdvisorDetail().getReviewRatingCount().getTerrible()+"");
            ((TextView) rootView.findViewById(R.id.reviewCountTextView)).setText(tripAdviserDataResponse.getTripAdvisorDetail().getNumOfReviews() + " " + getActivity().getResources().getString(R.string.reviews_from_our_community));
            setAverageRatingToSeekBar();

        } catch (Exception e) {
            e.printStackTrace();
        }
        setTopReviewAdapter();

        setReviewAdapter();

        setSummeryAdapter();

        disableSeekBarClick();

        AppController.getInstance().getGTMAnalytics(getActivity()).setScreenName("HotelDetailReview Screen - " + hotelDetailInfoResponse.getTtl());
    }

    // disable seek bar click listener
    @Override
    public void disableSeekBarClick() {

        excellentReviewSeekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {v.performClick();return true;}});
        veryGoodReviewSeekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {v.performClick();return true;
            }
        });
        averageReviewSeekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {v.performClick();return true;
            }
        });
        poorReviewSeekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {v.performClick();return true;
            }
        });
        terribleReviewSeekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {v.performClick();return true;
            }
        });}

    @Override
    public void setSummeryAdapter() {
        NonScrollListView summeryListView = (NonScrollListView) rootView.findViewById(R.id.summeryListView);
        SummeryAdapter mSummeryAdapter = new SummeryAdapter(tripAdviserDataResponse.getTripAdvisorDetail().getSubratings(), getActivity());
        summeryListView.setAdapter(mSummeryAdapter);
    }

    @Override
    public void setReviewAdapter() {
        NonScrollListView reviewListView = (NonScrollListView) rootView.findViewById(R.id.reviewListView);
        ReviewAdapter mReviewAdapter = new ReviewAdapter(tripAdviserDataResponse.getTripAdvisorDetail().getTripTypes(), getActivity());
        reviewListView.setAdapter(mReviewAdapter);
    }

    @Override
    public void setTopReviewAdapter() {
        NonScrollListView topReviewListView = (NonScrollListView) rootView.findViewById(R.id.topReviewListView);
        TopReviewAdapter mTopReviewAdapter = new TopReviewAdapter(tripAdviserDataResponse.getTripAdvisorDetail().getReviews(), getActivity());
        topReviewListView.setAdapter(mTopReviewAdapter);
    }

    @Override
    public void setAverageRatingToSeekBar() {
        long ratingSum = tripAdviserDataResponse.getTripAdvisorDetail().getReviewRatingCount().getExcellent() + tripAdviserDataResponse.getTripAdvisorDetail().getReviewRatingCount().getVeryGood() + tripAdviserDataResponse.getTripAdvisorDetail().getReviewRatingCount().getAverage() + tripAdviserDataResponse.getTripAdvisorDetail().getReviewRatingCount().getPoor() + tripAdviserDataResponse.getTripAdvisorDetail().getReviewRatingCount().getTerrible();

        if (ratingSum != 0) {
            excellentReviewSeekBar.setProgress((int)((tripAdviserDataResponse.getTripAdvisorDetail().getReviewRatingCount().getExcellent() * 100) / ratingSum));
            veryGoodReviewSeekBar.setProgress((int) ((tripAdviserDataResponse.getTripAdvisorDetail().getReviewRatingCount().getVeryGood() * 100) / ratingSum));
            averageReviewSeekBar.setProgress((int) ((tripAdviserDataResponse.getTripAdvisorDetail().getReviewRatingCount().getAverage() * 100) / ratingSum));
            poorReviewSeekBar.setProgress((int)((tripAdviserDataResponse.getTripAdvisorDetail().getReviewRatingCount().getPoor() * 100) / ratingSum));
            terribleReviewSeekBar.setProgress((int)((tripAdviserDataResponse.getTripAdvisorDetail().getReviewRatingCount().getTerrible() * 100) / ratingSum));
        }


    }
}
