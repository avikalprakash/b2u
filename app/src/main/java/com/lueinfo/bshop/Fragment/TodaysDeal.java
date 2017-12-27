package com.lueinfo.bshop.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.lueinfo.bshop.Adapter.AddtoCartMobile;
import com.lueinfo.bshop.Adapter.ItemEntity;
import com.lueinfo.bshop.Adapter.TodaysDealAdapter;
import com.lueinfo.bshop.Adapter.Utils;
import com.lueinfo.bshop.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class TodaysDeal extends Fragment implements AdapterView.OnItemClickListener{
    private static final int SHOW_PROCESS_DIALOG = 1;
    private static final int HIDE_PROCESS_DIALOG = 0;
    RecyclerView recyclerView;
    Context context;
    ProgressBar mprogressBar;
    String[] items;
    RecyclerView.Adapter recyclerView_Adapter;
    CardView change_view;
    ImageView view_con_img;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    private List<ItemEntity> cotegory_list = new ArrayList<ItemEntity>();
    private final HttpClient Client = new DefaultHttpClient();
    private String Content;
    MyAsync myAsync;
    int count=1;
    TextView next,privious,headertext;
    int currentPage = 1;
    String id="";
    Dialog dialog;
    LinearLayout filter1layout;
    RelativeLayout pre_next;
    Button retry;
    LinearLayout nonet_ll_cot;
    LinearLayout toadydeallayout;
    int Checkmode=0;
    String lan;
    String FilterType="";
    public TodaysDeal() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todays_deal, container, false);


    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        implementView();
        mprogressBar=(ProgressBar)getActivity().findViewById(R.id.circular_progress_bar) ;

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Checkmode = bundle.getInt("value", Checkmode);
            FilterType=bundle.getString("filtertype",FilterType);
        }

        change_view = (CardView) getActivity(). findViewById(R.id.change_view);
        pre_next=(RelativeLayout)getActivity().findViewById(R.id.pre_next);
        retry=(Button)view.findViewById(R.id.retry);
        view_con_img = (ImageView) getActivity(). findViewById(R.id.view_con_img);
        nonet_ll_cot=(LinearLayout)getActivity().findViewById(R.id.nonet_ll_cot);
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fill_dialog);
        headertext=(TextView)getActivity().findViewById(R.id.headertext);
        lan=getActivity().getResources().getString(R.string.Deal);
        headertext.setText(lan);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        toadydeallayout=(LinearLayout) getActivity().findViewById(R.id.toadydeallayout);
        Bundle bundle1 = this.getArguments();
        if (bundle1 != null) {
            id = bundle1.getString("Id" );
        }
        next=(TextView)getActivity().findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAsync=new MyAsync();
                myAsync.cancel(true);
                currentPage++;
                Log.d("CurrentPage","  "+currentPage);
                loadData();
              recyclerView_Adapter.notifyDataSetChanged();
            }
        });
        privious=(TextView)getActivity().findViewById(R.id.previous);
        privious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAsync=new MyAsync();
                myAsync.cancel(true);
                currentPage--;
                Log.d("CurrentPage","  "+currentPage);
                loadData();
                recyclerView_Adapter.notifyDataSetChanged();
            }
        });
//        change_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (count==1){
//                    count=2;48
//                    view_con_img.setImageResource(R.drawable.list);
//                    implementView();
//                }else{
//                    count=1;
//                    view_con_img.setImageResource(R.drawable.grid);
//                    implementView();
//                }
//            }
//        });

//    filter1layout=(LinearLayout)getActivity().findViewById(R.id.filter1layout);
//        filter1layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DoneREfactor doneREfactor = new DoneREfactor();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.container, doneREfactor);
//                transaction.addToBackStack(null);
//                transaction.commit();
//            }
//        });

        loadData();
    }
    private void implementView(){
        recyclerView = (RecyclerView)getActivity(). findViewById(R.id.recycler_todays_deal);
        recyclerViewLayoutManager = new GridLayoutManager(context, count);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView_Adapter = new TodaysDealAdapter(getActivity(),cotegory_list,null,getFragmentManager());
        recyclerView.setAdapter(recyclerView_Adapter);
        recyclerView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
             Log.d("chsc","############");
          }
      });

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String entityId=cotegory_list.get(i).getId();
        String image=cotegory_list.get(i).getImage();
        Intent intent=new Intent(getActivity(),AddtoCartMobile.class);
        intent.putExtra("KeyValue",entityId);
        intent.putExtra("image",image);
        startActivity(intent);

    }


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
           // mprogressBar.setIndeterminate(true);

        }

        @Override
        protected String doInBackground(String... strings) {
           // handler.sendEmptyMessage(SHOW_PROCESS_DIALOG);
            String s="";
            HttpGet httpget = new HttpGet(strings[0]);
            httpget.setHeader("Accept", "application/json");
            httpget.setHeader("Content-type", "application/json");
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            try {
                Content = Client.execute(httpget, responseHandler);
                HttpResponse httpResponse = Client.execute(httpget);
                HttpEntity httpEntity = httpResponse.getEntity();
                s = readResponse(httpResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }


            Log.d("tag11"," "+s);

            return s;

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
         //   handler.sendEmptyMessage(HIDE_PROCESS_DIALOG);
            pDialog.dismiss();
            Log.d("onPostExcutea", "" + s);
            cotegory_list.clear();
            try {
                //JSONObject jsonObject=new JSONObject(s);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Iterator<?> keys = jsonObject.keys();
                try {
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        if (jsonObject.get(key) instanceof JSONObject) {
                            String sn = jsonObject.getString(key);
                            JSONObject Dimension = new JSONObject(sn);
                            ItemEntity itemEntity = new ItemEntity();
                            itemEntity.setId(Dimension.getString("entity_id"));
                            itemEntity.setName(Dimension.getString("name"));
                            itemEntity.setSalePrice(Dimension.getString("final_price_with_tax"));
                            itemEntity.setMrpPrice(Dimension.getString("regular_price_with_tax"));
                            itemEntity.setImage(Dimension.getString("image_url"));
                            // itemEntity.setDescription(Dimension.getString("short_description"));
                            cotegory_list.add(itemEntity);
                            cotegory_list.size();

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }if (cotegory_list.size() >0){
                    toadydeallayout.setVisibility(View.VISIBLE);
                    nonet_ll_cot.setVisibility(View.GONE);
                    recyclerView_Adapter = new TodaysDealAdapter(getActivity(),cotegory_list,null,getFragmentManager());
                    recyclerView.setAdapter(recyclerView_Adapter);
                }else {
                    toadydeallayout.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"No Items in List", Toast.LENGTH_LONG).show();
                    nonet_ll_cot.setVisibility(View.VISIBLE);
                }
            }finally {
                return;
            }
        }
    }
    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch(message.what){
                case SHOW_PROCESS_DIALOG :
                  //  mprogressBar.setVisibility(View.VISIBLE);
                    dialog.show();
                    break;
                case HIDE_PROCESS_DIALOG :
                  //  mprogressBar.setVisibility(View.GONE);
                    dialog.hide();
                    break;
            }
            return false;
        }
    });

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
            Log.d("return_texta",""+return_text);
        } catch (Exception e)
        {

        }
        return return_text;


    }

    public void  loadData(){
        myAsync=new MyAsync();
        if (Utils.isNetworkAvailable(getActivity()) && myAsync.getStatus()!= AsyncTask.Status.RUNNING)
        {
            String serverURL1 = "http://bshop2u.com/api/rest/products?filter[1][attribute]=mode"+FilterType+"&filter[1][in]="+Checkmode;
            String serverURL = "http://bshop2u.com/api/rest/products?category_id="+id+"&page="+currentPage;

            // Create Object and call AsyncTask execute Method
            if(!FilterType.equals("")){
                new MyAsync().execute(serverURL1);
            }else {
                new MyAsync().execute(serverURL);
            }
        }else{
            Toast.makeText(getActivity(),"NO net avalable", Toast.LENGTH_LONG).show();
            nonet_ll_cot.setVisibility(View.VISIBLE);
            toadydeallayout.setVisibility(View.GONE);
            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadData();
                }
            });        }
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
