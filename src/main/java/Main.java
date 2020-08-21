import encapsulacion.Controladora;

import java.util.Scanner;

public class Main {
    private static Scanner entradaEscaner= new Scanner (System.in);
    public static void main(String[] args) throws ClassNotFoundException {

        //  -----------Datos SQL SERVER--------------
        String sqlServerport = "1433"; //args[0];
        String sqlServerdatabaseName = "Prubea"; //args[1];
        String sqlServerUserName = "sa"; //args[2];
        String sqlServerPassword = ""; //args[0];

        //  -----------Datos PostgreSQL--------------

        String pgServerport = "5432";
        String pgServerdatabaseName = "migracion";
        String pgServerUserName = "postgres";
        String pgServerPassword = "1234";

        /**
         *  Aplicancdo funciones...
         */

        //  Mapeando datos... SQL
        Controladora.getInstance().setMapeoTabla(sqlServerport, sqlServerdatabaseName, sqlServerUserName, sqlServerPassword);

        //  Creando tablas posgreSQL
        Controladora.getInstance().creandoTablasPG(pgServerport, pgServerdatabaseName, pgServerUserName, pgServerPassword);

        //  Migrando Datos a tablas de posgreSQL

    }
}
