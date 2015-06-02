
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
public class GestorNotificaciones extends GestorBD{
    /**
   * Obtiene todos los usuarios que tienen acceso al sistema
   */
  public Set<Notificacion> getNotificaciones(String nombreUsuario) {
    Set<Notificacion> notificaciones = new HashSet<>();
    Notificacion notificacion = null;
    
    try {
        
      PreparedStatement consultaSelect = 
              conexion.prepareStatement("SELECT u.nombreUsuario, n.idNotificacion, n.mensaje,"
                      + " n.estado, n.tipo FROM Usuario u JOIN Notificacion n ON"
                      + " u.idUsuario = n.codUsuarioEmisor WHERE"
                      + " n.codUsuarioReceptor IN (SELECT idUsuario FROM "
                      + "Usuario WHERE nombreUsuario = ?)");
      consultaSelect.setString(1, nombreUsuario);
      
        ResultSet rs = consultaSelect.executeQuery();
      if (!rs.next()) {
          
        return notificaciones;
      } else {
        do {

        notificacion = new Notificacion(rs.getInt("idNotificacion"), rs.getString("mensaje"), 
                rs.getString("nombreUsuario"), nombreUsuario, 
                rs.getInt("estado"), rs.getInt("tipo"));
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
  
  public boolean  eliminarNotificacion(int _cod){
        try {
        PreparedStatement sqlEliminarNotif = conexion.prepareStatement("DELETE"
                + " FROM Notificacion WHERE idNotificacion = ?");
        sqlEliminarNotif.setInt(1, _cod);
        sqlEliminarNotif.executeUpdate();
        return true;
        } catch (SQLException ex) {
          Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex);
          return false;
        }
    }
 
   public Notificacion obtenerNotificacion(int _cod, String _nombreUsuario){
    Notificacion notificacion = null;
    
    try {
        
      PreparedStatement consultaSelect = 
              conexion.prepareStatement("SELECT u.nombreUsuario, n.idNotificacion, n.mensaje,"
                      + " n.estado, n.tipo FROM Usuario u JOIN Notificacion n ON"
                      + " u.idUsuario = n.codUsuarioEmisor WHERE"
                      + " n.idNotificacion = ?");
      consultaSelect.setInt(1, _cod);
      
        ResultSet rs = consultaSelect.executeQuery();
      if (!rs.next()) {
          
        return notificacion;
      } else {
        notificacion = new Notificacion(rs.getInt("idNotificacion"), rs.getString("mensaje"), 
                rs.getString("nombreUsuario"), _nombreUsuario, 
                rs.getInt("estado"), rs.getInt("tipo"));
      }
    } catch (SQLException ex) {
         System.out.println("entra");
      notificacion = null;
    } catch (Exception e) {
        System.out.println("entra");
      notificacion = null;
    }
    return notificacion;
   }
   public int obtenerCodUsuario(String nombre){
        try{
          PreparedStatement sqlObtenCodigo = conexion.prepareStatement("SELECT idUsuario FROM Usuario WHERE nombreUsuario = ?");
          sqlObtenCodigo.setString(1, nombre);
          
          ResultSet rs = sqlObtenCodigo.executeQuery();
          int codUsuario;
          if (!rs.next()) {
        
            codUsuario = -1;
         } else {
            codUsuario = rs.getInt("idUsuario");
        }
          return codUsuario;
        }catch(SQLException ex){
          System.out.println(ex.toString());
          return ERROR;
        }  
    }
   public boolean enviarNotificacion(Notificacion _notificacion){
       try {
           String codEmisor = String.valueOf(obtenerCodUsuario(_notificacion.getNombreUsuarioEmisor()));
          String codReceptor = String.valueOf(obtenerCodUsuario(_notificacion.getNombreUsuarioReceptor()));
    PreparedStatement consultaCreacion = conexion.prepareStatement("INSERT INTO"
            + " Notificacion (mensaje, codUsuarioEmisor, codUsuarioReceptor, estado, tipo"
            + ") VALUES (?, ?, ?, ?, ?)");
    consultaCreacion.setString(1, _notificacion.getMensaje());
    consultaCreacion.setString(2, codEmisor);
    consultaCreacion.setString(3, codReceptor);
    consultaCreacion.setInt(4, _notificacion.getEstado());
    consultaCreacion.setInt(5, _notificacion.getTipo());
    
    consultaCreacion.execute();
    
    return true;
    } catch (SQLException ex) {
      System.out.println(ex.toString());
      return false;
    }
   }
}
