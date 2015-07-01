package peladafc.com.murallocalizacao;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import peladafc.com.murallocalizacao.Modelos.Campus;
import peladafc.com.murallocalizacao.Modelos.Cidade;
import peladafc.com.murallocalizacao.Modelos.Instituto;
import peladafc.com.murallocalizacao.adapters.CampusAdapter;
import peladafc.com.murallocalizacao.globals.Globals;


public class MainActivity extends ActionBarActivity {
    CampusTask tsk;
    RecyclerView rv;
    LinearLayoutManager llm;
    CampusAdapter adapter;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = (RecyclerView) findViewById(R.id.my_recycler_view);
        llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        adapter = new CampusAdapter();

        if (Globals.cidades == null) {
            tsk = new CampusTask();
            tsk.execute();
        }else{
            adapter.setCampus(Globals.cidades);
            rv.setAdapter(adapter);
        }


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

        return super.onOptionsItemSelected(item);
    }

    public class CampusTask extends AsyncTask<Void, Void, String> {
        private static final String urlStr = "https://cdn.fbsbx.com/hphotos-xpt1/v/t59.2708-21/11689315_10206301072113251_1447449821_n.json/institutis-1-1.json?oh=f40347e0eb42ab960de33a1930b54c3f&oe=5595BAE1&dl=1";

        public CampusTask(){
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

                if (Globals.cidades.size() == 0) {
                    Toast.makeText(getApplicationContext(), "Houve algum problema de conexao", Toast.LENGTH_LONG);
                } else {
                  adapter.setCampus(Globals.cidades);
                    rv.setAdapter(adapter);
                }
            } catch (JSONException e) {
                Log.e("PARSE JSON", "Erro no parsing do Json", e);
            }


        }
    }
}
