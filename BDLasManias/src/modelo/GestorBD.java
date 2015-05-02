
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

  private static final String DRIVER_MYSQL = "com.mysql.jdbc.Driver";
  
  
  private static GestorBD instancia = null;
  private ParametrosConexion parametrosConexion;
  private Connection conexion;
  
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
  
  /**
   * Comprueba si existe un usuario dado de alta en la tabla usuario con un 
   * nombre de usuario determinado
   */
  public synchronized boolean existeUsuario(String nombreUsuario) {
    try {
      PreparedStatement consultaExistencia = conexion.prepareStatement("SELECT"
            + " * FROM Usuario u WHERE u.nombreUsuario = ?");
      consultaExistencia.setString(1, nombreUsuario);
      
      ResultSet rs = consultaExistencia.executeQuery();  
      return rs.next();
    } catch (SQLException ex) {
      ex.printStackTrace();
      return false;
    }
  }
  
  /**
   * Comprueba si algún usuario dado de alta en la BD tiene el mismo email que
   * el pasado por parámetro
   */
  public synchronized boolean existeEmail(String email) {
    try {
      PreparedStatement consultaExistencia = conexion.prepareStatement("SELECT"
            + " * FROM Usuario WHERE email = ?");
      consultaExistencia.setString(1, email);
      
      ResultSet rs = consultaExistencia.executeQuery();  
      return rs.next();
    } catch (SQLException ex) {
      ex.printStackTrace();
      return false;
    }
  }
  
  /**
   * Comprueba si la contraseña introducida es la asociada a ese usuario
  */ 
  public synchronized boolean comprobarAutenticacion(String nombreUsuario,
          String contraseña) {
    contraseña = getMD5(contraseña);
    try {
      PreparedStatement sqlAutenticacion = conexion.prepareStatement("SELECT "
            + "u.password FROM Usuario u WHERE u.nombreUsuario = ?");
      sqlAutenticacion.setString(1, nombreUsuario);
      ResultSet rs = sqlAutenticacion.executeQuery();
      if (!rs.next()) {
        return false;
      } else {
        String passBD = rs.getString("password");
        if (passBD.equals(contraseña)) {
          return true;
        }
      }
      return false;
    } catch (SQLException ex) {
      ex.printStackTrace();
      return false;
    }
  }
   
  /**
   * Toma todos los datos de un determinado usuario
   */
  public Usuario getUsuario(String nombreUsuario) {
    Usuario usuario = null;
    
    try {
      PreparedStatement consultaSelect = 
              conexion.prepareStatement("SELECT nombreUsuario, password, "
                      + "estado, email FROM Usuario "
                      + "WHERE nombreUsuario = ?");
      consultaSelect.setString(1, nombreUsuario);
      ResultSet rs = consultaSelect.executeQuery();
      if (!rs.next()) {
        return usuario;
      } else {
        usuario = new Usuario(rs.getString("nombreUsuario"),
                       rs.getString("password"),                     
                       rs.getInt("estado"),
                       rs.getString("email"));
      }
    } catch (SQLException ex) {
      usuario = null;
    } catch (Exception e) {
      usuario = null;
    }
    return usuario;
  }
 
  /**
   * Obtiene todos los usuarios que tienen acceso al sistema
   */
  public Set<Notificacion> getNotificaciones(String nombreUsuario) {
    Set<Notificacion> notificaciones = new HashSet<>();
    Notificacion notificacion = null;
    
    try {
        
      PreparedStatement consultaSelect = 
              conexion.prepareStatement("SELECT u.nombreUsuario, n.mensaje,"
                      + " n.estado FROM Usuario u JOIN Notificacion n ON"
                      + " u.idUsuario = n.codUsuarioReceptor WHERE"
                      + " n.codUsuarioEmisor IN (SELECT idUsuario FROM "
                      + "Usuario WHERE nombreUsuario = ?)");
      consultaSelect.setString(1, nombreUsuario);
      
        ResultSet rs = consultaSelect.executeQuery();
      if (!rs.next()) {
          
        return notificaciones;
      } else {
        do {

        notificacion = new Notificacion(rs.getString("mensaje"), 
                nombreUsuario, rs.getString("nombreUsuario"), 
                rs.getInt("estado"));
        notificaciones.add(notificacion);
        } while (rs.next());
      }
    } catch (SQLException ex) {
         System.out.println("entra");
      notificacion = null;
    } catch (Exception e) {
      notificacion = null;
    }
    return notificaciones;
  }
   /**
   * Obtiene todos los usuarios que tienen acceso al sistema
   */
  public Set<Usuario> getListaAmigos(String nombreUsuario) {
    Set<Usuario> amigos = new HashSet<>();
    Usuario amigo = null;
    
    try {
        
      PreparedStatement consultaSelect = 
              conexion.prepareStatement("SELECT nombreUsuario, password, "
                      + "estado, email  FROM Usuario "
                      + "WHERE idUsuario IN (SELECT codAmigo FROM Amigos "
                      + "WHERE codUsuario IN (SELECT idUsuario FROM Usuario "
                      + "WHERE nombreUsuario = ?));");
      consultaSelect.setString(1, nombreUsuario);
      
      ResultSet rs = consultaSelect.executeQuery();
      if (!rs.next()) {
        return amigos;
      } else {
        do {
        amigo = new Usuario(rs.getString("nombreUsuario"),
                       rs.getString("password"),                     
                       rs.getInt("estado"),
                       rs.getString("email"));
        amigos.add(amigo);
        } while (rs.next());
      }
    } catch (SQLException ex) {
      amigo = null;
    } catch (Exception e) {
      amigo = null;
    }
    return amigos;
  }
  /**
   * Obtiene todos los usuarios que tienen acceso al sistema
   */
  public Set<Usuario> getListaUsuarios() {
    Set<Usuario> usuarios = new HashSet<>();
    Usuario usuario = null;
    
    try {
      PreparedStatement consultaSelect = 
              conexion.prepareStatement("SELECT * FROM Usuario");
      
      ResultSet rs = consultaSelect.executeQuery();
      if (!rs.next()) {
        
        usuarios = null;
      } else {
        do {
          usuario = new Usuario(rs.getString("nombreUsuario"),
                       rs.getString("password"),                     
                       rs.getInt("estado"),
                       rs.getString("email"));
            
          usuarios.add(usuario);
        } while (rs.next());
      }
    } catch (SQLException ex) {
      usuarios = null;
    } catch (Exception e) {
      usuarios = null;
    }
    return usuarios;
  }

  /**
   * Crea un usuario en la tabla usuario
   */
  public synchronized boolean crearUsuario(Usuario u) {
    try {
    PreparedStatement consultaCreacion = conexion.prepareStatement("INSERT INTO"
            + " Usuario (nombreUsuario, password, estado, email"
            + ") VALUES (?, ?, ?, ?)");
    consultaCreacion.setString(1, u.getNombreUsuario());
    consultaCreacion.setString(2, getMD5(u.getPassword()));
    consultaCreacion.setString(3, String.valueOf(u.getEstado()));
    consultaCreacion.setString(4, u.getEmail());
    
    consultaCreacion.execute();
    
    return true;
    } catch (SQLException ex) {
      System.out.println(ex.toString());
      return false;
    }
  }

  /**
   * Cambia la contraseña a un usuario
   */
  public boolean cambiarContraseña(String nombreUsuario, String password) {
      password = getMD5(password);
      try {
      PreparedStatement consultaCambioTipo = conexion.prepareStatement("UPDATE "
              + "Usuario SET password = ? WHERE nombreUsuario = ?");
      
      consultaCambioTipo.setString(1, password);
      consultaCambioTipo.setString(2, nombreUsuario);
      consultaCambioTipo.executeUpdate();

      return true;
    } catch (SQLException ex) {
      System.out.println(ex.toString());
      return false;
    }
  }
  
  public boolean agregarAmigo(String nombreUsuario, String nombreAmigo){
      try{
          PreparedStatement sqlagregaAmigo = conexion.prepareStatement(
          "");
          return true;
      }catch(SQLException ex){
          System.out.println(ex.toString());
          return false;
      }  
  }
  
    public boolean eliminarAmigo(String nombreUsuario, String nombreAmigo){
      try{
          PreparedStatement sqlEliminaAmigo1 = conexion.prepareStatement(
          "DELETE FROM Amigos WHERE idAmigos IN (SELECT idAmigos FROM "
                  + "Amigos WHERE codUsuario IN (SELECT idUsuario FROM "
                  + "Usuario WHERE nombreUsuario = ?) AND codAmigo "
                  + "IN (SELECT idUsuario FROM Usuario "
                  + "WHERE nombreUsuario = ?))");
          sqlEliminaAmigo1.setString(1, nombreUsuario);
          sqlEliminaAmigo1.setString(2, nombreAmigo);
          PreparedStatement sqlEliminaAmigo2 = conexion.prepareStatement(
          "DELETE FROM Amigos WHERE idAmigos IN (SELECT idAmigos FROM "
                  + "Amigos WHERE codUsuario IN (SELECT idUsuario FROM "
                  + "Usuario WHERE nombreUsuario = ?) AND codAmigo "
                  + "IN (SELECT idUsuario FROM Usuario "
                  + "WHERE nombreUsuario = ?))");
          sqlEliminaAmigo2.setString(1, nombreAmigo);
          sqlEliminaAmigo2.setString(2, nombreUsuario);
          
          sqlEliminaAmigo1.executeUpdate();
          sqlEliminaAmigo2.executeUpdate();
          return true;
      }catch(SQLException ex){
          System.out.println(ex.toString());
          return false;
      }  
  }
  public boolean cambiarEstadoUsuario(String nombreUsuario, int estado){
      try{
      PreparedStatement consultaCambioTipo = conexion.prepareStatement("UPDATE "
              + "Usuario SET estado = ? WHERE nombreUsuario = ?");
      
      consultaCambioTipo.setInt(1, estado);
      consultaCambioTipo.setString(2, nombreUsuario);
      consultaCambioTipo.executeUpdate();

      return true;
      }catch (SQLException ex) {
      System.out.println(ex.toString());
      return false;
      }
  }
  
  /**
   * Elimina un usuario pendiente de dar de alta por el administrador
   */
  public void eliminarUsuario(String nombreUsuario) {
    try {
      PreparedStatement sqlEliminarPdteAlta = conexion.prepareStatement("DELETE"
              + " FROM Usuario WHERE nombreUsuario = ?");
      sqlEliminarPdteAlta.setString(1, nombreUsuario);
      sqlEliminarPdteAlta.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  
  private String getMD5(String input) {
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
