package com.holidayme.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.data.UserDTO;
import com.holidayme.response.CurrentLocationDetail;

import java.util.List;


/**
 * Created by supriya.sakore on 22-05-2015.
 */
public class ContactUsFragment extends BaseFragment {
    private LinearLayout call_uae,email,layout_web,call_sa,call_ww;
    private ImageView img_instagram,img_google,img_twitter,img_fb;
    private TextView callNumberTextView;
    private CurrentLocationDetail currentLocationDetail = null;
    View rootView;
    UserDTO userDTO;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       rootView = inflater.inflate(R.layout.contact_us, container, false);


        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();



        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void initUIElements() {
        img_instagram=(ImageView)rootView.findViewById(R.id.img_instagram);
        img_google=(ImageView)rootView.findViewById(R.id.img_google);
        img_fb=(ImageView)rootView.findViewById(R.id.img_fb);
        img_twitter=(ImageView)rootView.findViewById(R.id.img_twitter);
        img_instagram=(ImageView)rootView.findViewById(R.id.img_instagram);
        img_google=(ImageView)rootView.findViewById(R.id.img_google);
        callNumberTextView=(TextView)rootView.findViewById(R.id.callNumberTextView);
        userDTO=UserDTO.getUserDTO();
        call_uae=(LinearLayout)rootView.findViewById(R.id.layout_call_uae);
        call_sa = (LinearLayout) rootView.findViewById(R.id.layout_call_sa);
        call_ww = (LinearLayout) rootView.findViewById(R.id.layout_call_ww);
        layout_web=(LinearLayout)rootView.findViewById(R.id.layout_web);


        callNumberTextView.setText(getContext().getString(R.string.worldwide_toll_free_text)+" "+getContext().getString(R.string.worldwide_toll_free_number));

        img_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userDTO!=null&&userDTO.getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/halaholidayme"));
                    startActivity(browserIntent);
                }else{
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/hiholidayme"));
                    startActivity(browserIntent);
                }

            }
        });
      img_google.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(userDTO!=null&&userDTO.getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)) {
                  Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/b/102458001055928029610/+HolidaymeSa"));
                  startActivity(browserIntent);
              }else{
                  Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/+Holidayme"));
                  startActivity(browserIntent);
              }

          }
      });
        img_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userDTO!=null&&userDTO.getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/halaholidayme"));
                    startActivity(browserIntent);
                }else{
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/hiholidayme"));
                    startActivity(browserIntent);
                }
            }
        });
       img_twitter.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(userDTO!=null&&userDTO.getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)) {
                   Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/halaholidayme"));
                   startActivity(browserIntent);
               }else{
                   Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/hiholidayme"));
                   startActivity(browserIntent);
               }
           }
       });

        layout_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.holidayme.com"));
                startActivity(browserIntent);

            }
        });

        call_uae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                String phone = (getActivity().getString(R.string.uae_toll_free_number));
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                getActivity().startActivity(intent);
            }
        });
       call_sa.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String phone = (getActivity().getString(R.string.saudi_toll_free_number));
               Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
               getActivity().startActivity(intent);

           }
       });
        call_ww.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = (getActivity().getString(R.string.worldwide_toll_free_number));
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                getActivity().startActivity(intent);

            }
        });
        email = (LinearLayout)rootView.findViewById(R.id.layout_email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"care@holidayme.com"});

                final PackageManager pm = getActivity().getPackageManager();
                final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                ResolveInfo best = null;
                for (final ResolveInfo info : matches)
                    if (info.activityInfo.packageName.endsWith(".gm") ||
                            info.activityInfo.name.toLowerCase().contains("gmail")) best = info;
                if (best != null)
                    intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                startActivity(intent);
            }


        });
        ((TextView)rootView.findViewById(R.id.toolbarTitleTextView)).setText(getActivity().getString(R.string.nav_item_contact_us));
        rootView.findViewById(R.id.toolbarBackImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    getActivity().onBackPressed();


            }
        });

        DrawerLayout mDrawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        LinearLayout language_layout = (LinearLayout)mDrawer.findViewById(R.id.languageLinearLayout);
        View view= mDrawer.findViewById(R.id.topBarView);
        if(language_layout!= null) {
            language_layout.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }
    }



}


