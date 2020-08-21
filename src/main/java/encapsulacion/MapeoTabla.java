package encapsulacion;

import java.util.List;

public class MapeoTabla {
    String nombreTabla;
    List<TipoDeDato> tipoDeDato;
    List<Datos> listaFilaDato;

    //
    public MapeoTabla(String nombreTabla, List<TipoDeDato> tipoDeDato) {
        this.nombreTabla = nombreTabla;
        this.tipoDeDato = tipoDeDato;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    public List<TipoDeDato> getTipoDeDato() {
        return tipoDeDato;
    }

    public void setTipoDeDato(List<TipoDeDato> tipoDeDato) {
        this.tipoDeDato = tipoDeDato;
    }

    public List<Datos> getListaDato() {
        return listaFilaDato;
    }

    public void setListaDato(List<Datos> listaDato) {
        this.listaFilaDato = listaDato;
    }
}
