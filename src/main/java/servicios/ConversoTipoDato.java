package servicios;

public class ConversoTipoDato {
    String nombreOriginal;
    String nombreSustituto;

    public ConversoTipoDato(String nombreOriginal, String nombreSustituto) {
        this.nombreOriginal = nombreOriginal;
        this.nombreSustituto = nombreSustituto;
    }

    public String getNombreOriginal() {
        return nombreOriginal;
    }

    public void setNombreOriginal(String nombreOriginal) {
        this.nombreOriginal = nombreOriginal;
    }

    public String getNombreSustituto() {
        return nombreSustituto;
    }

    public void setNombreSustituto(String nombreSustituto) {
        this.nombreSustituto = nombreSustituto;
    }
}
