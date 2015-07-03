package peladafc.com.murallocalizacao;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
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
    ProgressBar pb;
    TextView txtFalha;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Lista de campus");
        //inicar as views
        iniciarViews();
        //listar os campus
        carregarLista();

        /**
         * Casso o usuário deseje recarregar a lista
         */
        txtFalha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarLista();
            }
        });


    }

    /**
     * Criar a configura os elementos das views desta activity
     */
    private void iniciarViews(){
        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);

        txtFalha = (TextView) findViewById(R.id.textViewFalha);
        txtFalha.setVisibility(View.INVISIBLE);

        rv = (RecyclerView) findViewById(R.id.my_recycler_view);
        llm = new LinearLayoutManager(this);

        rv.setLayoutManager(llm);
        adapter = new CampusAdapter();
    }

    /**
     * Método responsável por garantir que a lista de cidades seja carregada apenas uma vez.
     */
    private void carregarLista(){
        if (Globals.cidades == null || Globals.cidades.size() == 0) {
            tsk = new CampusTask();
            pb.setVisibility(View.VISIBLE);
            rv.setVisibility(View.INVISIBLE);
            pb.setVisibility(View.INVISIBLE);
            tsk.execute();
        }else{
            adapter.setCampus(Globals.cidades);
            rv.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            carregarLista();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Classe responsável por executar a busca do arquivo JSON na internet
     */
    public class CampusTask extends AsyncTask<Void, Void, String> {


        public CampusTask(){
        }
        @Override
        protected String doInBackground(Void... params) {
            String retorno = "";
            String json = "";
            try {

                URL url = new URL(Globals.urlStr);
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
            parseJson(jsonString);
        }

        /**
         * método responsável por transformar a string json em uma lista de cidades, que será alocada na variável estática da clase Globals
         * @param jsonString
         */
        public void parseJson(String jsonString){
            try {

                if(jsonString != null && jsonString.length() > 0){
                    Globals.cidades = new ArrayList<Cidade>();
                    JSONArray listasCidades = new JSONArray(jsonString);

                    //Iterando entre as cidades
                    for (int i = 0; i< listasCidades.length(); i++){
                        Cidade cidade = new Cidade();

                        JSONObject objetoCidade = listasCidades.getJSONObject(i);
                        cidade.setNome(objetoCidade.getString("Nome"));
                        JSONArray listaCampusJson = objetoCidade.getJSONArray("Campus");
                        ArrayList<Campus> listaCampus = new ArrayList<>();

                        //Iterando entre os campus da cidade indexada em [i]
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

                            //Iterando entre os institutos do campus indexado em [j]
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

                        //adicionando a cidade na lista global
                        Globals.cidades.add(cidade);
                    }
                }

                if (Globals.cidades.size() == 0) {
                    Toast.makeText(getApplicationContext(), "Houve algum problema de conexao", Toast.LENGTH_LONG);
                    txtFalha.setVisibility(View.VISIBLE);
                } else {
                    adapter.setCampus(Globals.cidades);
                    rv.setAdapter(adapter);
                    pb.setVisibility(View.INVISIBLE);
                    rv.setVisibility(View.VISIBLE);
                    txtFalha.setVisibility(View.INVISIBLE);
                }
            } catch (JSONException e) {
                Log.e("PARSE JSON", "Erro no parsing do Json", e);
            }
        }

    }
}
