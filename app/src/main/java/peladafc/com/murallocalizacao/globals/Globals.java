package peladafc.com.murallocalizacao.globals;

import java.util.List;

import peladafc.com.murallocalizacao.Modelos.Campus;
import peladafc.com.murallocalizacao.Modelos.Cidade;

/**
 * Created by AIRES on 29/06/2015.
 * Classe respons�vel por objetos que ser�o usados durante a execu��o da aplica��o.
 * Este padr�o de projeto torna o acesso a objetos mais din�mico e menos oneroso para a aplica��o.
 */
public class Globals {

    // Lista que guardar� o resultado da interpreta��o do arquivo JSON lido no inicio da aplica��o
    public static List<Cidade> cidades;

    // Objeto campus que ser� atualizado sempre que o usu�rio escolher um campus na listagem de campus da primeira tela
    public static Campus campusSelecionado;

    //String onde est� localizado o arquivo JSON que obt�m todos os dados necess�rios para o funcionamento da aplica��o
    public static final String urlStr = "https://cdn.fbsbx.com/hphotos-xpt1/v/t59.2708-21/11689338_10206317697528876_581035048_n.json/institutis-1-1-9.json?oh=326aec8ce0b8213dca47d9d09ed844db&oe=55973466&dl=1";
}
