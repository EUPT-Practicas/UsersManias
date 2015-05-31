
package modelo;


public class ParametrosConexion {
  
  private String dbURL = "jdbc:mysql://155.210.68.170/lasdamas";
  private String usuario = "lasdamas";
  private String password = "lasdamas";
  
  /**
   * Constructor de la clase si queremos pasar nuevos datos.
   * @param _dbURL
   * @param _usuario
   * @param _pass 
   */
  public ParametrosConexion (String _dbURL, String _usuario, String _pass) {
      this.dbURL = _dbURL;
      this.usuario = _usuario;
      this.password = _pass;
  }
  
  /**
   * Constructor de la clase con los datos por defecto.
   */
  public ParametrosConexion(){
  }
  
  /**
   * Método con el que obtenemos la URL de la base de datos.
   * @return la URL
   */
  public String getDbURL() {
      return dbURL;
  }

  /**
   * Método con el que obtenemos el usuario administrador de la base de datos.
   * @return el nombre de usuario
   */
  public String getUsuario() {
      return usuario;
  }

  /**
   * Método con el que obtenemos la contraseña del usuario administrador de
   * la base de datos.
   * @return la contraseña
   */
  public String getPassword() {
      return password;
  }

}
