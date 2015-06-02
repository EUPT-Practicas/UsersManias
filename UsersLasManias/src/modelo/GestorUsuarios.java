
package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Javi
 */
public class GestorUsuarios extends GestorBD{
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
            + " * FROM Usuario u WHERE u.email = ?");
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
   * Toma todos los datos de un determinado usuario
   */
  public String getUsuarioAleatorio() {
    String nombre = "";
    
    try {
      PreparedStatement consultaSelect = 
              conexion.prepareStatement("SELECT nombreUsuario, password, "
                      + "estado, email FROM Usuario "
                      + "ORDER BY RAND()");
      ResultSet rs = consultaSelect.executeQuery();
      if (!rs.next()) {
        return nombre;
      } else {
            nombre = rs.getString("nombreUsuario");
      }
    } catch (SQLException ex) {
      nombre = null;
    } catch (Exception e) {
      nombre = null;
    }
    return nombre;
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
    
    public Set<Usuario> getBusquedaUsuario(String _nombreUsuario){
    Set<Usuario> usuarios = new HashSet<>();
    Usuario usuario = null;
        System.out.println(_nombreUsuario+"%");
    try {
      PreparedStatement consultaSelect = 
              conexion.prepareStatement("SELECT * FROM Usuario u WHERE u.nombreUsuario LIKE ?");
      consultaSelect.setString(1, _nombreUsuario+"%");
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
  public Set<Usuario> getListaConectados() {
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
}
