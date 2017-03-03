package com.example.ideafoundation.meetaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import  android.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.design.widget.NavigationView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ideafoundation.meetaapp.Adapter.NeedTableAdapter;
import com.example.ideafoundation.meetaapp.Adapter.NewUserNeedAdapter;
import com.example.ideafoundation.meetaapp.Adapter.UserNeedAdapter;
import com.example.ideafoundation.meetaapp.model.DefaultNeed;
import com.example.ideafoundation.meetaapp.model.Need;
import com.example.ideafoundation.meetaapp.util.NewUserNeedHandler;
import com.example.ideafoundation.meetaapp.util.UserNeedHandler;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ideafoundation on 31/01/17.
 */

public class CreateNeedActivity extends AppCompatActivity
{
           Toolbar mToolbar;
   // ArrayList<Need> dataModels;
   // private static NeedTableAdapter adapter;
    ArrayList<DefaultNeed> dataModels;
    private static NewUserNeedAdapter adapter;
     ListView listview;
    EditText search_view;
    Button submit;

           DrawerLayout mDrawerLayout;
    UserNeedHandler handler;
    NewUserNeedHandler newHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_need_activity);
        handler=new UserNeedHandler(this);
        newHandler= new NewUserNeedHandler(this);
        initview();
        initToolbar();
        Log.e("list size",""+handler.getAllContacts().size());

    }

    void initToolbar(){
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        TextView header=(TextView)mToolbar.findViewById(R.id.header);
        header.setText("CreateNeed");
        ImageView click=(ImageView)mToolbar.findViewById(R.id.click);
        click.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                    Intent intent=new Intent(CreateNeedActivity.this,ActivityNav.class);
                       startActivity(intent);
                   }
               });

           }
public void initview() {
    listview = (ListView) findViewById(R.id.listview);
    search_view = (EditText) findViewById(R.id.search_view);
    submit = (Button) findViewById(R.id.submit);
    dataModels = handler.getAllContacts();
    if (dataModels.size() == 0) {
        Intent intent = new Intent(CreateNeedActivity.this, UserNeeds.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    } else {
        adapter = new NewUserNeedAdapter(dataModels, this);
        listview.setAdapter(adapter);
        listview.setTextFilterEnabled(true);
        search_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = search_view.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ArrayList<Need> actorList = ((NeedTableAdapter)listview.getAdapter()).getSelectActorList();
                ArrayList<DefaultNeed> actorList = ((NewUserNeedAdapter) listview.getAdapter()).getSelectActorList();
                handler.deleteone(actorList);
                newHandler.allpalces(actorList);
                Toast.makeText(CreateNeedActivity.this, "" + actorList.size(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CreateNeedActivity.this, UserNeeds.class);
                intent.putExtra("list", actorList);
                startActivity(intent);
            }
        });
    }

}

}
