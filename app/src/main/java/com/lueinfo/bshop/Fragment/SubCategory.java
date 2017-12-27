package com.lueinfo.bshop.Fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.lueinfo.bshop.Adapter.ItemEntity;
import com.lueinfo.bshop.Adapter.SubCategoryAdaptor;
import com.lueinfo.bshop.Adapter.Utils;
import com.lueinfo.bshop.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by lue on 15-04-2017.
 */
public class SubCategory extends Fragment {
    //ProgressBar pb;
    Dialog dialog;
    TextView text;
    GridView more_related_grid;
    Button retry;
    LinearLayout nonet_ll_cot;
    private static final int SHOW_PROCESS_DIALOG = 1;
    private static final int HIDE_PROCESS_DIALOG = 0;
    ArrayList<ItemEntity> Subcotegory_list=new ArrayList<ItemEntity>();
    MyAsync myAsync;
    String id="",name="";
    public static final String LANGUAGE = "log";
    public static final String MyPref = "MyPref";
    SharedPreferences sharedpreferences;
    String language;
    String catLang="EN";

    SubCategoryAdaptor SubCategoryAdaptor;
   // SubCategoryAdaptor SubCategoryAdaptor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subcategory, container, false);

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //pb = (ProgressBar)getActivity(). findViewById(R.id.pb_subcot);
        dialog = new Dialog(getActivity());
        retry=(Button)view.findViewById(R.id.retry);
        text=(TextView)getActivity().findViewById(R.id.textView26);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fill_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        more_related_grid = (GridView) getActivity().findViewById(R.id.subcot_list);
        sharedpreferences=this.getActivity().getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        language = sharedpreferences.getString(LANGUAGE,"");
        nonet_ll_cot=(LinearLayout)getActivity().findViewById(R.id.nonet_ll_cot);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getString("Id" );
            name=bundle.getString("NAME");
        }
        Log.d("Bundle","  "+id);
;        loadData();
text.setText(name);
    }
    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch(message.what){
                case SHOW_PROCESS_DIALOG :
                    //pb.setVisibility(View.VISIBLE);
                    dialog.show();
                    break;
                case HIDE_PROCESS_DIALOG :
                  //  pb.setVisibility(View.GONE);
                    dialog.hide();
                    break;
            }
            return false;
        }
    });
    class MyAsync extends AsyncTask<String,Void,String> {
         ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

           // pb.setIndeterminate(true);
        }
        @Override
        protected String doInBackground(String... Strings) {

          //  handler.sendEmptyMessage(SHOW_PROCESS_DIALOG);
            Log.d("werftghy","iiiiiiiiiiii");
            String s="";


            try {
                HttpClient httpClient = new DefaultHttpClient();
//                HttpPost httpPost = new HttpPost("http://54.67.107.248/jeptags/apirest/get_subcategories");
                HttpPost httpPost = new HttpPost("http://bshop2u.com/apirest/get_subcategories");
                httpPost.setHeader("Content-type", "application/json");

                JSONObject jsonObject= new JSONObject();
                jsonObject.accumulate( "category_id", Strings[0]);
                jsonObject.accumulate( "lang", Strings[1]);
                Log.d("jdnj","  "+ Strings[0]+ Strings[1]);

                StringEntity stringEntity= new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                s = readResponse(httpResponse);
                Log.d("tag11"," "+s);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return s;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();
            Subcotegory_list.clear();
         //   handler.sendEmptyMessage(HIDE_PROCESS_DIALOG);
            Log.d("onPostExcute",""+s);

            try {
                JSONObject jsonObject=new JSONObject(s);
                JSONArray jsonArray  = jsonObject.getJSONArray("message");

                Log.d("sizee", "kk1111 " + jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    Log.d("object", "kk1111 " + jsonArray.getJSONObject(i).toString());
                    JSONObject jobject = jsonArray.getJSONObject(i);
                    ItemEntity itemEntity = new ItemEntity();
                    itemEntity.setId(jobject.getString("subcat_id"));
                    Log.d("mckm","cc"+jobject.getString("subcat_id"));
                    itemEntity.setName(jobject.getString("name"));
                    itemEntity.setImage(jobject.getString("image_url"));

                    Subcotegory_list.add(itemEntity);

                }
                Log.d("cotegory_list", "size: " + Subcotegory_list.size());
            }catch (Exception e){
                e.printStackTrace();
            }
            if (Subcotegory_list.size()>0){
                more_related_grid.setVisibility(View.VISIBLE);
                nonet_ll_cot.setVisibility(View.GONE);
                SubCategoryAdaptor=new SubCategoryAdaptor(getActivity(),Subcotegory_list);
                more_related_grid.setAdapter(SubCategoryAdaptor);
                more_related_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String id = Subcotegory_list.get(i).getId();
                        Log.d("sjcnj",""+id);

                      /*  TodaysDeal todaysDeal=new TodaysDeal();
                        FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("Id", id);
                        todaysDeal.setArguments(bundle);

                        fragmentTransaction.replace(R.id.container,todaysDeal);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();*/
                      /*  Intent intent=new Intent(getActivity(), MoreRelatedContents.class);
                        startActivity(intent);*/
                    }
                });
            }else {
                more_related_grid.setVisibility(View.GONE);
                nonet_ll_cot.setVisibility(View.VISIBLE);
            }
        }
    }
    public void  loadData(){

        myAsync=new MyAsync();
         if (Utils.isNetworkAvailable(getActivity()) && myAsync.getStatus()!= AsyncTask.Status.RUNNING)
        {
            if (language=="") {
                language = "EN";
                new MyAsync().execute(id, language);
            }else {
                new MyAsync().execute(id, language);
            }
        }else{
            Toast.makeText(getActivity(),"NO net avalable", Toast.LENGTH_LONG).show();
            nonet_ll_cot.setVisibility(View.VISIBLE);
             retry.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     loadData();
                 }
             });
        }
    }


    private String readResponse(HttpResponse httpResponse) {
        InputStream is=null;
        String return_text="";
        try {
            is=httpResponse.getEntity().getContent();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
            String line="";
            StringBuffer sb=new StringBuffer();
            while ((line=bufferedReader.readLine())!=null)
            {
                sb.append(line);
            }
            return_text=sb.toString();
            Log.d("return_text",""+return_text);
        } catch (Exception e)
        {

        }
        return return_text;


    }
    @Override
    public void onPause() {
        super.onPause();
        dialog.dismiss();
        if ( myAsync.getStatus()== AsyncTask.Status.RUNNING)
        {
            myAsync.cancel(true);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
        if ( myAsync.getStatus()== AsyncTask.Status.RUNNING)
        {
            myAsync.cancel(true);
        }
    }

}
