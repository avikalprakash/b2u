package com.lueinfo.bshop.Fragment;



import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lueinfo.bshop.Adapter.PromotionDetails;
import com.lueinfo.bshop.Adapter.PromotionListAdaptor;
import com.lueinfo.bshop.Adapter.ServiceHandler;
import com.lueinfo.bshop.R;
import com.lueinfo.bshop.SpecialPromotionDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class PromotionFragment extends Fragment {
    ArrayList<PromotionDetails> promotionDetailses=new ArrayList<PromotionDetails>();
    ListView listPromotion;
    View view;
    public static final String LANGUAGE = "log";
    public static final String MyPref = "MyPref";
    SharedPreferences sharedpreferences;
    String language;
    PromotionListAdaptor promotionListAdaptor;

    public PromotionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_promotion, container, false);
        listPromotion=(ListView)view.findViewById(R.id.ListPromotion);
        sharedpreferences=this.getActivity().getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        language = sharedpreferences.getString(LANGUAGE,"");

       new PromotionList().execute();
        listPromotion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String title=promotionDetailses.get(i).getTitle();
                String careted_at=promotionDetailses.get(i).getCreated_at();
                String desc=promotionDetailses.get(i).getDescription();
                String image=promotionDetailses.get(i).getImage();
                Log.d("yuhusdhc",""+title+","+careted_at);
               // Toast.makeText(getContext(), title+" ,"+careted_at+", "+desc+", "+image, Toast.LENGTH_LONG).show();
                Intent iP = new Intent(getActivity(), SpecialPromotionDetails.class);
                iP.putExtra("title",title);
                iP.putExtra("careted_at", careted_at);
                iP.putExtra("desc", desc);
                iP.putExtra("image", image);
                iP.putExtra("productid",promotionDetailses.get(i).getProductid());
                 startActivity(iP);
            }
        });
        return  view;
    }


    private class PromotionList extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("loading...");
            pDialog.setIndeterminate(false);
            pDialog.show();
            promotionDetailses=new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall("http://bshop2u.com/apiAdmin/api/getNewsAndBuletin/"+language, ServiceHandler.GET);
            if(json!=null)
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    boolean error = jsonObject.getBoolean("error");

                    if (!error) {

                        JSONArray jsonArray = jsonObject.getJSONArray("message");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jobject = jsonArray.getJSONObject(i);
                            PromotionDetails promotionDetails = new PromotionDetails();
                            promotionDetails.setTitle(jobject.getString("title"));
                            promotionDetails.setDescription(jobject.getString("description"));
                            promotionDetails.setImage(jobject.getString("image"));
                            promotionDetails.setCreated_at(jobject.getString("created_at"));
                            promotionDetails.setProductid(jobject.getString("post_id"));
                            promotionDetailses.add(promotionDetails);
                            // promotionDetailses.add(new PromotionDetails((JSONObject)jsonArray.get(i)));

                        }


                    }
                }
                catch (JSONException e) {
                }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            promotionListAdaptor = new PromotionListAdaptor(getActivity(), promotionDetailses);
            listPromotion.setAdapter(promotionListAdaptor);
            if (pDialog.isShowing())
                pDialog.dismiss();
        }
    }


}
