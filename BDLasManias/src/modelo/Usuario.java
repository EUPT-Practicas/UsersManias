
package modelo;

import java.util.HashSet;
import java.util.Set;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;


public class Usuario implements Observable{
  private final int ERROR = -1;
  private final int EN_JUEGO = 0;
  private final int CONECTADO = 1;
  private final int NO_CONECTADO = 2;
  private final int AUSENTE = 3;
  private String nombreUsuario;
  private String password;
  private int estado;
  private String email;
  private Set<Usuario> amigos;
  private Set<Notificacion> notificaciones;
  
  /**
   * Crea un usuario solo con el nombre
   * @param _nombreUsuario 
   */
  public Usuario(String _nombreUsuario) {
    this.nombreUsuario = _nombreUsuario;
  }
  /**
   * Crea un usuario con una lista de amigos
   * @param _nombreUsuario
   * @param _password
   * @param _estado
   * @param _email
   * @param _amigos 
   */
  public Usuario(String _nombreUsuario, String _password, int _estado,
          String _email,Set<Usuario> _amigos) {
    
    this.nombreUsuario = _nombreUsuario;
    this.password = _password;
    this.estado = _estado;
    this.email = _email;
    this.amigos = _amigos;
  }
  
  /**
   * Crea un usuario con una lista de amigos vacia
   * @param _nombreUsuario
   * @param _password
   * @param _estado
   * @param _email 
   */
  public Usuario(String _nombreUsuario, String _password, int _estado, 
          String _email) {
    
    this.nombreUsuario = _nombreUsuario;
    this.password = _password;
    this.estado = _estado;
    this.email = _email;
    this.amigos = new HashSet<>();
  }
  
  public boolean esAmigo(String nombreAmigo){
      Usuario amigo = new Usuario(nombreUsuario);
      return amigos.contains(amigo);
  }
  
  public String getNombreUsuario() {
    return nombreUsuario;
  }

  public String getPassword() {
    return password;
  }

  public int getEstado(){
      return estado;
  }

  public String getEmail() {
    return email;
  }

  public void setNombreUsuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  
  public void setEstado(int estado){
      this.estado = estado;
  }

  public void setEmail(String email) {
    this.email = email;
  }
  
  public String toString() {
    String usuario = "Usuario: " + nombreUsuario + "\nEmail: " 
                     + email;
    return usuario;
  }

    public boolean equals(Object obj) {
        if(!super.equals(obj)) return false;
        if(!(obj instanceof Usuario)) return false;
        Usuario j = (Usuario)obj;
        String nLower = this.nombreUsuario.toLowerCase();
        if(nLower.equals(j.getNombreUsuario().toLowerCase())) return true;
        return false;
    }

    @Override
    public void addListener(InvalidationListener il) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeListener(InvalidationListener il) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
