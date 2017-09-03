package com.example.niharia.mysql;

import android.app.DownloadManager;
import android.app.VoiceInteractor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    private List<Contact> myList;
    private MyListAdapter adapter;
    RequestQueue requestQueue;//used for fetching data from server
    String url="http://incognisys.com/SachinApps/Scripts/ClassesDemo/getData.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv=(ListView) findViewById(R.id.listview);
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        loadData();
    }

    class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return myList.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(),
                        R.layout.list_item, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();

            holder.tv_id.setText(myList.get(position).getId() + "");
            holder.tv_name.setText(myList.get(position).get_name());
            holder.tv_num.setText(myList.get(position).get_phone_number());


            return convertView;
        }

        class ViewHolder {
            TextView tv_id;
            TextView tv_name;
            TextView tv_num;
            LinearLayout row;

            public ViewHolder(View view) {
                tv_id = (TextView) view.findViewById(R.id.txtid);
                tv_name = (TextView) view.findViewById(R.id.txtname);
                tv_num = (TextView) view.findViewById(R.id.txtnumber);
                row = (LinearLayout) view.findViewById(R.id.row);
                view.setTag(this);
            }
        }
    }
    public void loadData() {
        Toast.makeText(getApplicationContext(),"sdfsd",Toast.LENGTH_LONG).show();
        myList = new ArrayList<Contact>();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Contact contact;
                try {
                    JSONArray jArray = new JSONArray(response);

                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        contact = new Contact(
                                Integer.parseInt(json.get("id").toString()),
                                json.get("name").toString(),
                                json.get("phonenum").toString());
                        myList.add(contact);
                        Log.w("data",json.get("id").toString());

                    }
                    adapter=new MyListAdapter();
                    lv.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);
    }

}
