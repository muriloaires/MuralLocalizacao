package peladafc.com.murallocalizacao.Services;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import peladafc.com.murallocalizacao.Modelos.Campus;
import peladafc.com.murallocalizacao.Modelos.Cidade;
import peladafc.com.murallocalizacao.Modelos.Instituto;
import peladafc.com.murallocalizacao.globals.Globals;
//
/**
 * Created by AIRES on 29/06/2015.
 */
public class CampusTask extends AsyncTask <Void, Void, String>{


    @Override
    protected String doInBackground(Void... params) {
        String retorno = null;
        try {
            // Create a URL for the desired page
            URL url = new URL("https://cdn.fbsbx.com/hphotos-xpt1/v/t59.2708-21/11699301_10206291880843475_325070215_n.json/institutis-1.json?oh=6d0035aa0a00678e6f5e43e7ed5dbe96&oe=55936357&dl=1");

            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            retorno = in.readLine();
            in.close();
        } catch (MalformedURLException e) {
            retorno = "";
        } catch (IOException e) {
        }
        retorno = "";
        return retorno;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        super.onPostExecute(jsonString);
        Globals.cidades = new ArrayList<Cidade>();
        try {
            JSONArray listasCidades = new JSONArray(jsonString);
            Iterator<String> chaves = listasCidades.getJSONObject(0).keys();
            for (int i = 0; i< listasCidades.length(); i++){
                Cidade cidade = new Cidade();
                cidade.setNome(chaves.next());
                JSONObject objetoCidade = listasCidades.getJSONObject(i);
                JSONArray listaCampusJson = objetoCidade.getJSONArray("Campus");
                ArrayList<Campus> listaCampus = new ArrayList<>();
                for (int j = 0; j < listaCampusJson.length(); j++) {
                    Campus campus = new Campus();
                    JSONObject campusJson = listaCampusJson.getJSONObject(j);
                    campus.setOrdem(campusJson.getInt("Ordem"));
                    campus.setSetor(campusJson.getString("Setor"));
                    campus.setNome(campusJson.getString("Nome"));
                    campus.setLatitude(campusJson.getDouble("Latitude"));
                    campus.setLongitude(campusJson.getDouble("longitude"));

                    ArrayList<Instituto> institutos = new ArrayList<>();
                    JSONArray listaInstitutosJson = campusJson.getJSONArray("Institutos");
                    for (int k = 0; k < listaInstitutosJson.length(); k++) {
                        Instituto instituto = new Instituto();
                        JSONObject institutoJson = listaInstitutosJson.getJSONObject(k);
                        instituto.setSigla(institutoJson.getString("Sigla"));
                        instituto.setNome(institutoJson.getString("Nome"));
                        instituto.setLatitude(institutoJson.getDouble("Latitude"));
                        instituto.setLongitude(institutoJson.getDouble("Longitude"));
                        institutos.add(instituto);
                    }
                    campus.setInstitutos(institutos);
                    listaCampus.add(campus);
                }
                cidade.setCampusList(listaCampus);
                Globals.cidades.add(cidade);
            }

        } catch (JSONException e) {
            Log.e("PARSE JSON", "Erro no parsing do Json", e);
        }


    }
}
