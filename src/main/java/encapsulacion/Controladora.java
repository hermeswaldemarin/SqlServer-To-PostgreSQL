package encapsulacion;

import servicios.ConexionBD;
import servicios.ConversoTipoDato;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Controladora {
    private static  Controladora controladora = null;
    private List<MapeoTabla> ListaMapeoTabla = new ArrayList<>();
    private List<ConversoTipoDato> conversoTipoDatoes = new ArrayList<>();
    private ConexionBD conexionBD = ConexionBD.getInstance();

    /**
     *  Implementando el patron Singleton
     */
    public Controladora() {
        cargandoDatoAcambiar();
    }
    public static Controladora getInstance() {
        if (controladora == null) {
            controladora = new Controladora();
        }
        return controladora;
    }
    public static Controladora getControladora() {
        return controladora;
    }
    public List<MapeoTabla> getMapeoTabla() {
        return ListaMapeoTabla;
    }

    //
    public void setMapeoTabla(String sqlServerName, String puertoSQL, String nombreBDSQL, String usuarioSQL, String passwordSQL) {
        Connection sqlServerConnection = null;
        sqlServerConnection = conexionBD.getConexionSqlServer(sqlServerName, puertoSQL, nombreBDSQL, usuarioSQL, passwordSQL);
        List<String> nombreTablas = new ArrayList<>();
        List<String> nombreColumnas = new ArrayList<>();
        List<String> tipoDeDatos = new ArrayList<>();
        List<TipoDeDato> tipoDato = new ArrayList<>();

        if (sqlServerConnection != null) {
            try {
                nombreTablas = getTables(sqlServerConnection);

                System.out.println("\n==================================================================");
                System.out.println("                        Mapeando tablas...");
                System.out.println("==================================================================\n");
                System.out.println("[INFO] -- Cantidad de tabla a Mapear: "+nombreTablas.size());

                for (String nombre:nombreTablas) {
                    if(!nombre.equalsIgnoreCase("sysdiagrams")) {
                        System.out.println("-------------------------------------------------");
                        if (!nombre.equalsIgnoreCase("")) {
                            System.out.print("Tabla: " + nombre);
                            this.ListaMapeoTabla.add(new MapeoTabla(nombre, conversionTipoDeDato(tiposDedatosTabla(sqlServerConnection, nombre))));
                        }
                    }
                }
            } catch (SQLException throwables) {
                System.out.println("[Error] Ha ocurrrido un error al intentar mapear las tablas!");
                throwables.printStackTrace();
            }
        }
        conexionBD.cerrarConexion(sqlServerConnection);
    }

    // Conversion de datos.
    public void cargandoDatoAcambiar(){
        // conversoTipoDatoLis.add( new ConversoTipoDato(String nombreOriginal, String nombreSustituto) );
        // Crgadtos datos por defectos

        conversoTipoDatoes.add( new ConversoTipoDato("image", "bytea"));//
        conversoTipoDatoes.add( new ConversoTipoDato("decimal", "decimal(19,4)"));//
        conversoTipoDatoes.add( new ConversoTipoDato("varchar", "text"));//
        conversoTipoDatoes.add( new ConversoTipoDato("nvarchar", "text"));//
        conversoTipoDatoes.add( new ConversoTipoDato("char", "text"));//
        conversoTipoDatoes.add( new ConversoTipoDato("ntext", "text"));//
        conversoTipoDatoes.add( new ConversoTipoDato("datetime", "timestamp"));//
        conversoTipoDatoes.add( new ConversoTipoDato("smalldatetime", "timestamp"));
        conversoTipoDatoes.add( new ConversoTipoDato("bit", "boolean"));//
        conversoTipoDatoes.add( new ConversoTipoDato("tinyint", "smallint"));//
        conversoTipoDatoes.add( new ConversoTipoDato("numeric", "numeric(18,2)"));//
        conversoTipoDatoes.add( new ConversoTipoDato("float", "double precision"));//

        //--------------------------------------------------------------------------------------------------//
        conversoTipoDatoes.add( new ConversoTipoDato("nchar", "text"));
        conversoTipoDatoes.add( new ConversoTipoDato("binary", "bytea"));
        conversoTipoDatoes.add( new ConversoTipoDato("varbinary", "bytea"));
        conversoTipoDatoes.add( new ConversoTipoDato("smallmoney", "money"));
        //--------------------------------------------------------------------------------------------------//

    }
    public List<TipoDeDato> conversionTipoDeDato(List<TipoDeDato> listaTipoDato){
        //System.out.println("Cambiando datos....");

        boolean estadoCambio;
        for(TipoDeDato td :listaTipoDato) {
            estadoCambio = false;
            for (ConversoTipoDato ctd : conversoTipoDatoes) {
               // System.out.println("********************************************");
               // System.out.println("DATO ORIGINAL > "+ctd.getNombreOriginal());
               // System.out.println("DATO SUBTITUTO > "+ctd.getNombreSustituto());
                if(td.getTipoDato().equalsIgnoreCase(ctd.getNombreOriginal())){
                    td.setTipoDatoModificado(ctd.getNombreSustituto());
                    estadoCambio = true;
                }
            }
            // Si no existe un tipo de dato registrado dejarlo por el que tiene...
            if(estadoCambio==false){
                td.setTipoDatoModificado(td.getTipoDato());
            }
        }
        return listaTipoDato;
    }
    private List<String> getTables(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String sqlTipoDato = "SELECT Table_Name FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE'";
        ResultSet tableSet = statement.executeQuery(sqlTipoDato);
        List<String> td = new ArrayList<String>();
        while (tableSet.next()) {
            td.add(tableSet.getString(1));
        }
        return td;
    }
    private List<TipoDeDato> tiposDedatosTabla(Connection connection, String nombreTabla) throws SQLException {
        //Esta si esta bien!
        List<TipoDeDato> listaTipoDatos = new ArrayList<TipoDeDato>();

        Statement statement = connection.createStatement();
        String sql = "SELECT COLUMN_NAME, DATA_TYPE Table_Name FROM INFORMATION_SCHEMA.Columns WHERE Table_Name='"+nombreTabla+"'";
        ResultSet tableSet = statement.executeQuery(sql);
        int count = 0;
        int cantFilas = 0;
        while (tableSet.next()) {
            try {
                count++;

                //System.out.println("-------------------------------------------------");
                //System.out.println("Tabla: "+tableSet.getString(3));
                //System.out.println("Columna: "+tableSet.getString(1)+"  Tipo De Dato: "+tableSet.getString(2));
                listaTipoDatos.add(new TipoDeDato(tableSet.getString(1), tableSet.getString(2)));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        System.out.println(" <-> Cant Columnas: "+count);
        return listaTipoDatos;
    }

    //creando tablas
    public void creandoTablasPG(String pgServerName, String pgServerport, String pgServerdatabaseName, String pgServerUserName, String pgServerPassword){
        Connection con = null;
        con = ConexionBD.getInstance().getPostgresConnection(pgServerName, pgServerport, pgServerdatabaseName, pgServerUserName, pgServerPassword);
        boolean estado = false;
        PreparedStatement prepareStatement;
        String sql;

        if(con!=null){
            System.out.println("\n==================================================================");
            System.out.println("[INFO] -- Creando Tablas...");
            System.out.println("==================================================================\n");

            for (MapeoTabla mt: getMapeoTabla()) {
                sql = "";
                sql += "CREATE TABLE IF NOT EXISTS "+mt.getNombreTabla()+"(";
                System.out.println("-----------------------------------------------");
                System.out.println("Tabla: " + mt.getNombreTabla());
                for (int i = 0; i < mt.getTipoDeDato().size(); i++) {
                    sql += mt.getTipoDeDato().get(i).getNombreColumna()+" "+
                            mt.getTipoDeDato().get(i).getTipoDatoModificado();
                    if(i!=mt.getTipoDeDato().size()-1){
                        sql+=",";
                    }
                }
                sql += ");";
                try {
                    prepareStatement = con.prepareStatement(sql);
                    estado = prepareStatement.executeUpdate() > 0;
                    System.out.println("[INFO] -- Tabla creada de forma exitosa! ");
                } catch (SQLException throwables) {
                    System.out.println("[ERROR] -- Ocurrio un error al intentar crear tabla! ");
                    throwables.printStackTrace();
                }
            }
        }
        ConexionBD.getInstance().cerrarConexion(con);

    }

    //Enviando datos recoletados
    private void enviandoDatosAPostgresql(Connection con, MapeoTabla mt) {
        System.out.println("\n------------CANTIDADES------------");
        System.out.println("Columnas ->  "+mt.getTipoDeDato().size());
        System.out.println("Datos    ->  "+mt.getListaDato().size());
        boolean estado = false;
        String sql;
        PreparedStatement prepareStatement;
        if (con != null) {
            for (Datos d:mt.getListaDato()) {
                sql = "INSERT INTO " + mt.getNombreTabla() + " values(";
                for (int i = 0; i < d.listaDato.size() ; i++) {
                    sql +="'"+d.getListaDato().get(i)+"'";
                    if(i!=d.getListaDato().size()-1){
                        sql+=",";
                    }
                }
                sql += ");";
                System.out.println("Salidad -> "+sql);
                try {
                    prepareStatement = con.prepareStatement(sql);
                    estado = prepareStatement.executeUpdate() > 0;
                } catch (SQLException throwables) {
                    System.out.println("[ERROR] -- Ocurrio un error al intentar enviar datos a la tabla! ");
                    throwables.printStackTrace();
                }
            }
        }
    }
    public void recuperandoDatosEnviandoAPG(Connection conSql, Connection conPG){
        List<String> listaDato ;
        Statement statement;
        List<Datos> misDatos;
        if(conSql!=null){
            try {
                for (MapeoTabla mt : getMapeoTabla()) {
                    misDatos = new ArrayList<Datos>();
                    String sql = "SELECT * FROM "+mt.getNombreTabla();
                    statement = conSql.createStatement();
                    ResultSet rs = statement.executeQuery(sql);
                    while (rs.next()) {
                        listaDato = new ArrayList<String>();
                        for (int i = 1; i <= mt.getTipoDeDato().size(); i++) {
                            listaDato.add(rs.getString(i));
                        }
                        misDatos.add( new Datos(listaDato));
                    }
                    mt.setListaDato(misDatos);
                    enviandoDatosAPostgresql(conPG,mt);

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        conexionBD.cerrarConexion(conSql);
        conexionBD.cerrarConexion(conPG);
    }


}
