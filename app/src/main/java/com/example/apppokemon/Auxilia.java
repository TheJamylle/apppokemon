package com.example.apppokemon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Auxilia {
    public String converter(InputStream inputStream) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String conteudo = "";
        try {
            while ((conteudo = bufferedReader.readLine())!=null){
                stringBuilder.append(conteudo).append("\n");
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}
