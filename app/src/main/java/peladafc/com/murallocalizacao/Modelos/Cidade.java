package peladafc.com.murallocalizacao.Modelos;

import java.util.List;

/**
 * Created by AIRES on 29/06/2015.
 */
public class Cidade {

    private String nome;
    private List<Campus> campusList;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Campus> getCampusList() {
        return campusList;
    }

    public void setCampusList(List<Campus> campusList) {
        this.campusList = campusList;
    }
}
