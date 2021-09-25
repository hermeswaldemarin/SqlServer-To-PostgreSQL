import encapsulacion.Controladora;
import encapsulacion.MapeoTabla;
import servicios.ConexionBD;

import java.sql.Connection;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    private static Scanner entradaEscaner= new Scanner (System.in);
    public static void main(String[] args) throws ClassNotFoundException {
        Main nuevo = new Main();
        nuevo.aplicandoFunciones(args);
    }
    public void aplicandoFunciones(String[] args){
        //  -----------Datos SQL SERVER--------------
        String sqlServerName = args[0];
        //"DESKTOP-OQC29UT";
        //
        String sqlServerport = args[1];
        //"1433"; //args[1];
        String sqlServerdatabaseName = args[2];
        //"Prubea"; //args[2];
        String sqlServerUserName = args[3];
        //"sa"; //args[3];
        String sqlServerPassword = args[4];
        //""; //args[4];

        //  -----------Datos PostgreSQL--------------
        String pgServerName = args[5];
        //"localhost";//args[4];
        String pgServerport = args[6];
        // "5432"; //args[5];
        String pgServerdatabaseName = args[7];
        //"migracion"; //args[6];
        String pgServerUserName = args[8];
        //"postgres"; //args[7];
        String pgServerPassword = args[9];
        //"1234"; //args[8];

        System.out.println("\n==================================================================");
        System.out.println("Realizando Prueba de conexion...");
        System.out.println("==================================================================\n");
        Connection conSQLTest = null;
        Connection conPgTest = null;
        conSQLTest = ConexionBD.getInstance().getConexionSqlServer(sqlServerName, sqlServerport, sqlServerdatabaseName, sqlServerUserName, sqlServerPassword);
        conPgTest = ConexionBD.getInstance().getPostgresConnection(pgServerName, pgServerport, pgServerdatabaseName, pgServerUserName, pgServerPassword);

        if(conSQLTest!=null && conPgTest!=null) {
            ConexionBD.getInstance().cerrarConexion(conSQLTest);
            ConexionBD.getInstance().cerrarConexion(conPgTest);
            System.out.println("\n==================================================================");
            System.out.println("Aplicancdo funciones para migracion de datos");
            System.out.println("==================================================================\n");
            /**
             *  Aplicancdo funciones...
             */

            //  Mapeando datos... SQL
            Controladora.getInstance().setMapeoTabla(sqlServerName, sqlServerport, sqlServerdatabaseName, sqlServerUserName, sqlServerPassword);

            //  Creando tablas posgreSQL
            //Controladora.getInstance().creandoTablasPG(pgServerName, pgServerport, pgServerdatabaseName, pgServerUserName, pgServerPassword);

            //recuperando datos

            //  Migrando Datos a tablas de posgreSQL

            System.out.println("Starting time " + new Date());


            ThreadPoolExecutor executor =
                    (ThreadPoolExecutor) Executors.newFixedThreadPool(50);
            for (MapeoTabla mt : Controladora.getInstance().getMapeoTabla()) {

                executor.submit(() -> {
                    Controladora.getInstance().recuperandoDatosEnviandoAPG(mt, sqlServerName, sqlServerport, sqlServerdatabaseName, sqlServerUserName, sqlServerPassword, pgServerName, pgServerport, pgServerdatabaseName, pgServerUserName, pgServerPassword);
                });


            }


            while (executor.getActiveCount() > 0){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Finishing time " + new Date());
            System.exit(0);


        }

    }

}
