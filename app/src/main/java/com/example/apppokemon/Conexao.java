package com.example.apppokemon;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Conexao {
    public InputStream obterRespostaHTTP(String endereco) {
        try {
            URL url = new URL(endereco);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            return inputStream;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e ) {
            e.printStackTrace();
        }

        return null;
    }
}
