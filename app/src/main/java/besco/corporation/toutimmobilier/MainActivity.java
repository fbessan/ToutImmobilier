package besco.corporation.toutimmobilier;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import besco.corporation.toutimmobilier.Adapters.RecycleAdapter_Annonces;
import besco.corporation.toutimmobilier.Adapters.RecyclerTouchListener;
import besco.corporation.toutimmobilier.Constants.Constant;
import besco.corporation.toutimmobilier.Functions.Functions;
import besco.corporation.toutimmobilier.Models.Annonces;
import besco.corporation.toutimmobilier.Services.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    RecyclerView annonces_recycleview;
    private List<Annonces> annoncesArrayList;
    private RecycleAdapter_Annonces adapter_annonces;

    RelativeLayout relativelayout;
    SwipeRefreshLayout swipeLayout;

    private PrefManager prefManager;

    private static final int PERMISSION_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setTitle(null);
        Toolbar topToolBar = (Toolbar)findViewById(R.id.toolbar);
        topToolBar.setTitle(getString(R.string.app_name));
        setSupportActionBar(topToolBar);

        //topToolBar.setLogo(R.drawable.logo);
        //topToolBar.setLogoDescription(getResources().getString(R.string.app_name));

        monprivilege();//Check Permission

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        prefManager = new PrefManager(this);


        relativelayout = (RelativeLayout) findViewById(R.id.relativelayout);

        Functions functions = new Functions();


        if(functions.isConnected(MainActivity.this)) {
            doGetCountriesList();
        }else{
            alertMessage(getString(R.string.no_connexion));
        }
        buildRecyclerView();

        EditText editText = findViewById(R.id.search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //filter(s.toString());
            }
        });
    }

    @Override public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                doGetCountriesList();
                buildRecyclerView();
                swipeLayout.setRefreshing(false);
            }
        }, 5000);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void monprivilege(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 29;
                requestPermissions(new String[]{android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }
    }

    private void filter(String text) {

        ArrayList<Annonces> filteredList = new ArrayList<>();

        if(annoncesArrayList!= null && annoncesArrayList.size() > 0) {
            for (Annonces item : annoncesArrayList) {
                if (item.getTypeBien() !=null && item.getTypeBien().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
        }

        adapter_annonces.filterList(filteredList);

    }




    private void buildRecyclerView() {

        annonces_recycleview = (RecyclerView)findViewById(R.id.annonces_recycleview);

        adapter_annonces = new RecycleAdapter_Annonces(MainActivity.this,annoncesArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        annonces_recycleview.setLayoutManager(mLayoutManager);
        annonces_recycleview.setAdapter(adapter_annonces);

        annonces_recycleview.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), annonces_recycleview, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Annonces annonces = annoncesArrayList.get(position);
                //prefManager.setPrefSearchValue("PREF_COUNTRY",view.getTag(R.string.label).toString());
                startActivity(new Intent(MainActivity.this, DetailActivity.class));
                finish();
                //Toast.makeText(getApplicationContext(), view.getTag(R.string.id).toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), annonces.getId().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void doGetCountriesList() {

        try {
            String url = Constant.SERVEUR_URL+Constant.SERVEUR_API_VERSION;

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIService service = retrofit.create(APIService.class);

            Call<List<Annonces>> call = service.doGetAnnoncesList();
            Log.d("123-1", call.request().toString());
            call.enqueue(new Callback<List<Annonces>>() {
                @Override
                public void onResponse(@NonNull Call<List<Annonces>> call, @NonNull Response<List<Annonces>> response) {

                    annoncesArrayList = response.body();
                    Log.d("123-2", response.body().toString());
                    annonces_recycleview = (RecyclerView)findViewById(R.id.annonces_recycleview);

                    adapter_annonces = new RecycleAdapter_Annonces(MainActivity.this,annoncesArrayList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
                    annonces_recycleview.setLayoutManager(mLayoutManager);
                    annonces_recycleview.setAdapter(adapter_annonces);


                }

                @Override
                public void onFailure(@NonNull Call<List<Annonces>> call, @NonNull Throwable t) {
                    Log.d("123-3", t.getMessage());
                    alertMessage(getString(R.string.no_connexion));

                }
            });
        }catch (Exception e) {
            alertMessage(getString(R.string.no_connexion));
        }
    }


    private void  alertMessage(String msg){
        Snackbar snackbar = Snackbar.make(relativelayout, msg, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.white));
        snackbar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        /*if(id == R.id.action_youtube){
            Toast.makeText(MainActivity.this, R.string.action_youtube, Toast.LENGTH_LONG).show();
        }*/
        if(id == R.id.action_new){
            Toast.makeText(MainActivity.this, "Create Text", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }



    public  String getBase64FromFile(String path)
    {
        Bitmap bmp = null;
        ByteArrayOutputStream baos = null;
        byte[] baat = null;
        String encodeString = null;
        try
        {
            bmp = BitmapFactory.decodeFile(path);
            baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
            baat = baos.toByteArray();
            encodeString = Base64.encodeToString(baat, Base64.DEFAULT);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return encodeString;
    }
}
