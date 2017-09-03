package com.example.niharia.mysql;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class addcontant extends AppCompatActivity {
    EditText txtname,txtnumber,txtid;
    Button btnSave, btnUpdate,btnDelete;
    ListView listView;
    //DatabaseHandler db;
    List<Contact> myList;
    //AppAdapter mAdapter;
    private MainActivity.MyListAdapter adapter;
    RequestQueue requestQueue;//used for fetching data from server
    String url="http://incognisys.com/SachinApps/Scripts/ClassesDemo/insert.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontant);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        txtid = (EditText) findViewById(R.id.txtid);
        txtname = (EditText) findViewById(R.id.txtname);
        txtnumber = (EditText) findViewById(R.id.txtnumber);
        listView = (ListView) findViewById(R.id.listView);
        requestQueue= Volley.newRequestQueue(getApplicationContext());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog loading = ProgressDialog.show(addcontant.this, "Loading...", "Please wait...", false, false);
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");

                                Toast.makeText(getApplicationContext(), "Contact Saved Successfully!", Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("name", txtname.getText().toString());
                        parameters.put("phonenum", txtnumber.getText().toString());
                        return parameters;
                    }
                };

                requestQueue.add(request);
            }

        });

    }
}
