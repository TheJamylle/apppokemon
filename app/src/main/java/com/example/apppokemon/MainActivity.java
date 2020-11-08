package com.example.apppokemon;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.apppokemon.ui.pokemon.PokemonFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private static final String URL = "https://pokeapi.co/api/v2/pokemon?limit=3&offset=45";
    ArrayList<JSONObject> links = new ArrayList<>();
    ArrayList<JSONObject> pokemons = new ArrayList<>();
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new obterDados().execute();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ReitoriaActivity.class);
                startActivity(i);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_pokemon)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    private class obterDados extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Conexao conexao = new Conexao();
            InputStream inputStream = conexao.obterRespostaHTTP(URL);
            Auxilia auxilia = new Auxilia();
            String textoJSON = auxilia.converter(inputStream);
            JSONObject obj;
            JSONArray results = null;
            try {
                obj = new JSONObject(textoJSON);
                results = obj.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(results != null) {
                for (int i = 0; i < results.length(); i++) {
                    try {
                        links.add(results.getJSONObject(i));
                        InputStream is = conexao.obterRespostaHTTP(links.get(i).getString("url"));
                        Auxilia a = new Auxilia();
                        String tJson = a.converter(is);
                        JSONObject pok;
                        try {
                            pok = new JSONObject(tJson);
                            pokemons.add(pok);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "não foi possível obter JSON pelo servidor", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Downloading: arquivo JSON", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                addMenuItemInNavMenuDrawer();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(aVoid);
        }
    }

    private void addMenuItemInNavMenuDrawer() throws JSONException {
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        final Menu menu = navView.getMenu();

        for(int i = 0; i < links.size();i++){
            menu.add(links.get(i).getString("name"));
            menu.getItem(i).setOnMenuItemClickListener(mostra(i));
        }
    }

    private MenuItem.OnMenuItemClickListener mostra(final int id){

        MenuItem.OnMenuItemClickListener menuItemClickListener = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                displayScreen(id);
                return true;
            }
        };

        return menuItemClickListener;
    }

    private void displayScreen(int itemId) {

        Fragment fragment = null;

        fragment = new PokemonFragment(pokemons.get(itemId));

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_fragment, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}