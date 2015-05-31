
package controlador;

import java.util.Set;
import modelo.GestorBD;
import modelo.Notificacion;
import modelo.Usuario;
import vista.Login;

/**
 *
 * @author Javi
 */
public class ControlUsuarios {
    private Login vistaLogin;
    private GestorBD gbd = new GestorBD();
    private Set<Usuario> amigos;
    private Set<Notificacion> notificaciones;
    public ControlUsuarios(Login v) {
        
        this.vistaLogin  = v;
         
          vistaLogin.setVisible(true);
    }
    
    public boolean loginCorrecto(String _nombre, String _pass){
        return gbd.comprobarAutenticacion(_nombre, _pass);
    }
    
    public Set<Usuario> listaAmigos(String nombreUsuario) {
        return gbd.getListaAmigos(nombreUsuario);
    }
    
    public boolean eliminarAmigo(String nombreUsuario, String nombreAmigo){
        return gbd.eliminarAmigo(nombreUsuario, nombreAmigo);
    }
}
