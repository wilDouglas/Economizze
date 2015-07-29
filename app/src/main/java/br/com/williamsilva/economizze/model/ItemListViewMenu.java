package br.com.williamsilva.economizze.model;

/**
 * Created by William on 20/11/2014.
 */
public class ItemListViewMenu {

    private String texto;
    private int imagemId;

    public ItemListViewMenu(String texto, int imagemId) {
        this.texto = texto;
        this.imagemId = imagemId;
    }


    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getImagemId() {
        return imagemId;
    }

    public void setImagemId(int imagemId) {
        this.imagemId = imagemId;
    }
}
