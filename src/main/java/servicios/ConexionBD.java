package servicios;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Referencias:
 * https://docs.microsoft.com/en-us/sql/connect/jdbc/connection-url-sample?view=sql-server-ver15
 * https://docs.microsoft.com/en-us/sql/connect/jdbc/working-with-a-connection?view=sql-server-ver15
 */
public class ConexionBD {
    private static ConexionBD conexionBD;
    /**
     * Implementado patron Singleton
     */
    private ConexionBD() {
        registroDriver();
    }
    public static ConexionBD getInstance() {
        if(conexionBD==null){
            conexionBD = new ConexionBD();
        }
        return conexionBD;
    }

    /**
     * Registrando Driver de conexion
     */
    private void registroDriver() {
        try {
            Class.forName("org.postgresql.Driver");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //Registrando Drive para SQL Server

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * Abrir Objeto de conexion. 2do paso.
     */
    public  Connection getConexionSqlServer(String puerto, String nombreBD, String usuario, String password)   {

        Connection conexionSql = null;

        //String connectionUrl = "jdbc:sqlserver://<server>:<port>;databaseName=AdventureWorks;user=<user>;password=<password>";
        String connectionUrl = "jdbc:sqlserver://DESKTOP-OQC29UT:" + puerto + ";" + "databaseName=" + nombreBD+";"
                + "user=" + usuario + ";"+"password=" + password;

        //TEST String url = "jdbc:sqlserver://DESKTOP-OQC29UT:1433;databaseName=Prubea;user=sa;password=";
        try {

            conexionSql = DriverManager.getConnection(connectionUrl);
            if(conexionSql!=null){
                System.out.println("[INFO] La conexion se ha realizado de forma correcta..");
            }
        }catch (Exception exception){
            exception.printStackTrace();
            System.out.println("Error al intentar conectar con la BD-sql server");
        }
        return  conexionSql;
    }
    public void cerrarConexion(Connection con) {
        //cerrando conexiones
        try {
            if (con != null) {
                con.close();
            }
            con = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getPostgresConnection(String puerto, String nombreBD, String usuario, String password) {
        Connection postGreSQLConnection = null;
        String connectionUrl = "jdbc:postgresql://localhost:" + puerto + "/" + nombreBD;

        try {

            postGreSQLConnection = DriverManager.getConnection(connectionUrl, usuario, password);
            if (postGreSQLConnection != null) {
                System.out.println("[INFO] - Conexión postgres exitosa!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[Error] - Conexión - " + e.getMessage());
        }
        return postGreSQLConnection;
    }
}
