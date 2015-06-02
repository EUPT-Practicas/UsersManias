
package modelo;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GestorBD {

  protected static final String DRIVER_MYSQL = "com.mysql.jdbc.Driver";
  
  protected final int ERROR = -1;
  protected static GestorBD instancia = null;
  protected ParametrosConexion parametrosConexion;
  protected Connection conexion;
  
  /**
   * Constructor que crea una nueva conexion con la BD
   */
  public GestorBD() {
    conexion = null;

    try {
      parametrosConexion = new ParametrosConexion();
      
      Class.forName(DRIVER_MYSQL).newInstance();
      
      conexion = DriverManager.getConnection(parametrosConexion.getDbURL(), 
                                             parametrosConexion.getUsuario(), 
                                             parametrosConexion.getPassword());
      
    } catch(SQLException e) {
      System.out.println(e.toString());
      conexion = null;
    } catch(Exception e) {
      System.out.println(e.toString());
      conexion = null;
    }
  }
  
    /**
   * Método que sirve para instanciar el Singleton de esta clase.
   *
   * @return la instancia de la clase.
   */
  public static synchronized GestorBD getInstance() {
    if (instancia == null) {
      instancia = new GestorBD();
    }
    return instancia;
  }
  
  /**
   * Devuelve la conexion creada a la BD
   * @return 
   */
  public Connection getConnection() {
    return conexion;
  }
  
  protected String getMD5(String input) {
        byte[] source;
        try {
            //Get byte according by specified coding.
            source = input.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            source = input.getBytes();
        }
        String result = null;
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source);
            //The result should be one 128 integer
            byte temp[] = md.digest();
            char str[] = new char[16 * 2];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = temp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            result = new String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
