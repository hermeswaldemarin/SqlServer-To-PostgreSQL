package encapsulacion;

public class TipoDeDato {
    String nombreColumna;
    String tipoDato;
    String tipoDatoModificado;

    public TipoDeDato(String nombreColumna, String tipoDato) {
        this.nombreColumna = nombreColumna;
        this.tipoDato = tipoDato;
    }

    public String getNombreColumna() {
        return nombreColumna;
    }

    public void setNombreColumna(String nombreColumna) {
        this.nombreColumna = nombreColumna;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public String getTipoDatoModificado() {
        return tipoDatoModificado;
    }

    public void setTipoDatoModificado(String tipoDatoModificado) {
        this.tipoDatoModificado = tipoDatoModificado;
    }
}
