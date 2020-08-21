package encapsulacion;

import java.util.List;

public class Datos {
    List<String> listaDato;

    public Datos(List<String> listaDato) {
        this.listaDato = listaDato;
    }
    public List<String> getListaDato() {
        return listaDato;
    }

    public void setListaDato(List<String> listaDato) {
        this.listaDato = listaDato;
    }
}
