package peladafc.com.murallocalizacao.globals;

import java.util.List;

import peladafc.com.murallocalizacao.Modelos.Campus;
import peladafc.com.murallocalizacao.Modelos.Cidade;

/**
 * Created by AIRES on 29/06/2015.
 * Classe responsável por objetos que serão usados durante a execução da aplicação.
 * Este padrão de projeto torna o acesso a objetos mais dinâmico e menos oneroso para a aplicação.
 */
public class Globals {

    // Lista que guardará o resultado da interpretação do arquivo JSON lido no inicio da aplicação
    public static List<Cidade> cidades;

    // Objeto campus que será atualizado sempre que o usuário escolher um campus na listagem de campus da primeira tela
    public static Campus campusSelecionado;

    //String onde está localizado o arquivo JSON que obtém todos os dados necessários para o funcionamento da aplicação
    public static final String urlStr = "https://cdn.fbsbx.com/hphotos-xpt1/v/t59.2708-21/11689338_10206317697528876_581035048_n.json/institutis-1-1-9.json?oh=326aec8ce0b8213dca47d9d09ed844db&oe=55973466&dl=1";
}
