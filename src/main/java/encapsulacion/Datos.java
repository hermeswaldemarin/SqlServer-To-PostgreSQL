package encapsulacion;

import java.util.List;
import java.util.Map;

public class Datos {
    Map<String, Object> listaDato;

    public Datos(Map<String, Object> listaDato) {
        this.listaDato = listaDato;
    }
    public Map<String, Object> getListaDato() {
        return listaDato;
    }

    public void setListaDato(Map<String, Object> listaDato) {
        this.listaDato = listaDato;
    }
}
