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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.lueinfo.bshop.Adapter.ItemEntity;
import com.lueinfo.bshop.Adapter.MainCotegotieAdapter;
import com.lueinfo.bshop.Adapter.Utils;
import com.lueinfo.bshop.R;

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
 * A simple {@link Fragment} subclass.
 */
public class
CotegoriesFragment extends Fragment {
    private static final int SHOW_PROCESS_DIALOG = 1;
    private static final int HIDE_PROCESS_DIALOG = 0;
    ProgressBar pb;
    ListView listView;
    TextView headertext;
    MainCotegotieAdapter mainCotegotieAdapter;
    ArrayList<ItemEntity> cotegory_list=new ArrayList<ItemEntity>();
    MyAsync myAsync;
    LinearLayout nonet_ll_cot;
    Dialog dialog;
    Button retry;
    public static final String LANGUAGE = "log";
    public static final String MyPref = "MyPref";
    SharedPreferences sharedpreferences;
    String language;
    String catLang="EN";
String lan;
    public CotegoriesFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cotegories, container, false);

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myAsync=new MyAsync();
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fill_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        retry=(Button)view.findViewById(R.id.retry);

        headertext=(TextView)getActivity().findViewById(R.id.headertext);
        lan=getActivity().getResources().getString(R.string.Categories);
        headertext.setText(lan);
        listView=(ListView)getActivity().findViewById(R.id.cot_list);
        nonet_ll_cot=(LinearLayout)getActivity().findViewById(R.id.nonet_ll_cot);
        sharedpreferences=this.getActivity().getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        language = sharedpreferences.getString(LANGUAGE,"");
        loadData();
    }
   Handler handler=new Handler(new Handler.Callback() {
    @Override
    public boolean handleMessage(Message message) {
        switch(message.what){
            case SHOW_PROCESS_DIALOG :
               // pb.setVisibility(View.VISIBLE);
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
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
/*        @Override
        protected String doInBackground(String... strings) {
            handler.sendEmptyMessage(SHOW_PROCESS_DIALOG);
            LoadApi loadApi=new LoadApi();
           String cote= loadApi.getCotegories1();
            Log.d("category",""+cote);
            return cote;
        }*/
@Override
protected String doInBackground(String... params) {
  //  handler.sendEmptyMessage(SHOW_PROCESS_DIALOG);
    String return_text="";
    try{
        HttpClient httpClient=new DefaultHttpClient();
//        HttpPost httpPost=new HttpPost("http://54.67.107.248/jeptags/apirest/get_categories");
        HttpPost httpPost=new HttpPost("http://bshop2u.com/now/apirest/get_categories");
        JSONObject jsonObject=new JSONObject();
        jsonObject.accumulate("lang",params[0]);
        StringEntity httpiEntity=new StringEntity(jsonObject.toString());
        httpPost.setEntity(httpiEntity);
        HttpResponse httpResponse=httpClient.execute(httpPost);
        return_text=readResponse(httpResponse);
    }catch(Exception e){
        e.printStackTrace();
    }
    return return_text;
}
        public String readResponse(HttpResponse res) {
            InputStream is=null;
            String return_text="";
            try {
                is=res.getEntity().getContent();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
                String line="";
                StringBuffer sb=new StringBuffer();
                while ((line=bufferedReader.readLine())!=null)
                {
                    sb.append(line);
                }
                return_text=sb.toString();
            } catch (Exception e)
            {

            }
            return return_text;

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           // handler.sendEmptyMessage(HIDE_PROCESS_DIALOG);
            pDialog.dismiss();
            Log.d("onPostExcute",""+s);
            cotegory_list.clear();
            try {
                //JSONObject jsonObject=new JSONObject(s);
                JSONArray jsonArray=new JSONArray(s);
                Log.d("sizee", "kk1111 " + jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    Log.d("object", "kk1111 " + jsonArray.getJSONObject(i).toString());
                    JSONObject jobject = jsonArray.getJSONObject(i);
                    ItemEntity itemEntity = new ItemEntity();
                    itemEntity.setId(jobject.getString("entity_id"));
                    Log.d("mckm","cc"+jobject.getString("entity_id"));
                    itemEntity.setName(jobject.getString("name"));
                    cotegory_list.add(itemEntity);
                }
                Log.d("cotegory_list", "size: " + cotegory_list.size());
            }catch (Exception e){
                e.printStackTrace();
            }


            if (cotegory_list.size()>0){
            listView.setVisibility(View.VISIBLE);
                nonet_ll_cot.setVisibility(View.GONE);
            mainCotegotieAdapter=new MainCotegotieAdapter(getActivity(),cotegory_list);
            listView.setAdapter(mainCotegotieAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                       String id = cotegory_list.get(i).getId();
String name=cotegory_list.get(i).getName();
                       SubCategory subCategory=new SubCategory();
                        FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("Id", id);
                        bundle.putString("NAME",name);
                        subCategory.setArguments(bundle);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.replace(R.id.container,subCategory);
                        fragmentTransaction.commit();
                      /*  Intent intent=new Intent(getActivity(), MoreRelatedContents.class);
                        startActivity(intent);*/
                    }
                });
            }else {
                listView.setVisibility(View.GONE);
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
               myAsync.execute(language);
           }else {
               myAsync.execute(language);
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
