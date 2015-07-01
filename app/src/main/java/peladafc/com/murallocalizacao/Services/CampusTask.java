package peladafc.com.murallocalizacao.Services;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
    private static final String urlStr = "https://cdn.fbsbx.com/hphotos-xpt1/v/t59.2708-21/11689315_10206301072113251_1447449821_n.json/institutis-1-1.json?oh=f40347e0eb42ab960de33a1930b54c3f&oe=5595BAE1&dl=1";
    ReadyToGo ready;

    public CampusTask(ReadyToGo ready){
        this.ready = ready;
    }
    @Override
    protected String doInBackground(Void... params) {
        String retorno = "";
        String json = "";
        try {

            URL url = new URL(urlStr);

            URLConnection urlConnection = url.openConnection();
            urlConnection.setReadTimeout(5000);
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            while((retorno = in.readLine()) != null){
                json += retorno;
            }

            in.close();

        } catch (MalformedURLException e) {
            Log.e("Erro", e.getMessage());
            json = "";
        } catch (IOException e) {
            Log.e("Erro", e.getMessage());
            json = "";
        }
        return json;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        Globals.cidades = new ArrayList<Cidade>();
        try {

            if(jsonString != null && jsonString.length() > 0){
                JSONArray listasCidades = new JSONArray(jsonString);
                for (int i = 0; i< listasCidades.length(); i++){
                    Cidade cidade = new Cidade();

                    JSONObject objetoCidade = listasCidades.getJSONObject(i);
                    cidade.setNome(objetoCidade.getString("Nome"));
                    JSONArray listaCampusJson = objetoCidade.getJSONArray("Campus");
                    ArrayList<Campus> listaCampus = new ArrayList<>();
                    for (int j = 0; j < listaCampusJson.length(); j++) {
                        Campus campus = new Campus();
                        JSONObject campusJson = listaCampusJson.getJSONObject(j);
                        campus.setOrdem(campusJson.getInt("Ordem"));
                        campus.setSetor(campusJson.getString("Setor"));
                        campus.setNome(campusJson.getString("Nome"));
                        campus.setLatitude(campusJson.getDouble("Latitude"));
                        campus.setLongitude(campusJson.getDouble("Longitude"));
                        campus.setNomeCidade(cidade.getNome());
                        ArrayList<Instituto> institutos = new ArrayList<>();
                        JSONArray listaInstitutosJson = campusJson.getJSONArray("Institutos");
                        for (int k = 0; k < listaInstitutosJson.length(); k++) {
                            Instituto instituto = new Instituto();
                            JSONObject institutoJson = listaInstitutosJson.getJSONObject(k);
                            instituto.setSigla(institutoJson.getString("Siga"));
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
            }


            ready.readyTo();
        } catch (JSONException e) {
            Log.e("PARSE JSON", "Erro no parsing do Json", e);
        }


    }
}
