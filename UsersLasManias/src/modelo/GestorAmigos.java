
package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Javi
 */
public class GestorAmigos extends GestorBD {
     
  
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
public Set<Usuario> getListaAmigosConectados(String _nombre, int _estado) {
        Set<Usuario> amigos = new HashSet<>();
    Usuario amigo = null;
    
    try {
        
      PreparedStatement consultaSelect = 
              conexion.prepareStatement("SELECT nombreUsuario, password, "
                      + "estado, email  FROM Usuario "
                      + "WHERE idUsuario IN (SELECT codAmigo FROM Amigos "
                      + "WHERE codUsuario IN (SELECT idUsuario FROM Usuario "
                      + "WHERE nombreUsuario = ?)) AND estado = ?;");
      consultaSelect.setString(1, _nombre);
      consultaSelect.setInt(2, _estado);
      
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
  
  public boolean agregarAmigo(String nombreUsuario, String nombreAmigo){
      try{
          String codUsuario = String.valueOf(obtenerCodUsuario(nombreUsuario));
          String codAmigo = String.valueOf(obtenerCodUsuario(nombreAmigo));
          PreparedStatement sqlagregaAmigo = conexion.prepareStatement(
          "INSERT INTO Amigos(codUsuario, codAmigo) VALUES (? , ?)");
          sqlagregaAmigo.setString(1, codUsuario);
          sqlagregaAmigo.setString(2, codAmigo);
          sqlagregaAmigo.executeUpdate();
          return true;
      }catch(SQLException ex){
          System.out.println(ex.toString());
          return false;
      }  
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
    public int obtenerCodAmigos(String nombreUsuario, String nombreAmigo){
                 try{
          PreparedStatement sqlObtenCodigo = conexion.prepareStatement("SELECT idAmigos FROM Amigos WHERE codUsuario IN (SELECT idUsuario FROM Usuario WHERE nombreUsuario = ?) AND codAmigo IN (SELECT idUsuario FROM Usuario WHERE nombreUsuario = ?)");
          sqlObtenCodigo.setString(1, nombreUsuario);
          sqlObtenCodigo.setString(2, nombreAmigo);
          
          ResultSet rs = sqlObtenCodigo.executeQuery();
          int codAmigos;
          if (!rs.next()) {
        
            codAmigos = -1;
          } else {
            codAmigos = rs.getInt("idAmigos");
      }
          return codAmigos;
      }catch(SQLException ex){
          System.out.println(ex.toString());
          return ERROR;
      }  
    }
    public boolean eliminarAmigo(String nombreUsuario, String nombreAmigo){
      try{
          String codAmigos = String.valueOf(obtenerCodAmigos(
                  nombreUsuario, nombreAmigo));
          PreparedStatement sqlEliminaAmigo1 = conexion.prepareStatement(
          "DELETE FROM Amigos WHERE idAmigos = ?");
          sqlEliminaAmigo1.setString(1, codAmigos);
          
          sqlEliminaAmigo1.executeUpdate();
          return true;
      }catch(SQLException ex){
          System.out.println(ex.toString());
          return false;
      }  
  }
}
