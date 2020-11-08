package com.example.apppokemon.ui.pokemon;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.apppokemon.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class PokemonFragment extends Fragment {

    private PokemonViewModel pokemonViewModel;
    private JSONObject pokemon;
    private JSONObject sprites;
    private TextView nome, id, tipo, base, ataque, defesa;
    private ImageView img;

    public PokemonFragment(JSONObject me) {
        this.pokemon = me;
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pokemon, container, false);
        nome = root.findViewById(R.id.nome);
        id = root.findViewById(R.id.id);
        tipo = root.findViewById(R.id.tipo);
        base = root.findViewById(R.id.base);
        ataque = root.findViewById(R.id.ataque);
        defesa = root.findViewById(R.id.defesa);
        img = root.findViewById(R.id.img);


        try {
            Picasso.with(getContext()).load(pokemon.getJSONObject("sprites").getString("front_shiny")).into(img);
            nome.setText("Nome: "+pokemon.getString("name"));
            id.setText("Id: "+pokemon.getInt("id"));
            base.setText("Base Experience: "+pokemon.getInt("base_experience"));
            tipo.setText("Tipos: "+pokemon.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("name") +", "+pokemon.getJSONArray("types").getJSONObject(1).getJSONObject("type").getString("name"));
            defesa.setText("Defesa Especial: "+pokemon.getJSONArray("stats").getJSONObject(4).getString("base_stat"));
            ataque.setText("Ataque Especial: "+pokemon.getJSONArray("stats").getJSONObject(3).getString("base_stat"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Paras");
    }

    public static Bitmap getBitmapFromURL(String src) {
        Bitmap bitmap;
        try {
            URL url = new URL(src);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
}