
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
        //Usuario u2 = new Usuario("Luis", "luis", 0, "luis@lasmanias.es");
        //gbd.crearUsuario(u2);
         //System.out.println(gbd.getListaUsuarios());
         //amigos = gbd.getListaAmigos("Alvaro");
         
         //System.out.println(amigos.toString());
          //notificaciones = gbd.getNotificaciones("javi");
          //System.out.println(notificaciones.toString());
        //gbd.cambiarEstadoUsuario("Javi", 1);
         
          vistaLogin.setVisible(true);
    }
    
    public boolean loginCorrecto(String _nombre, String _pass){
        return gbd.comprobarAutenticacion(_nombre, _pass);
    }
    
    public Set<Usuario> listaAmigos(String nombreUsuario) {
        return gbd.getListaAmigos(nombreUsuario);

    }
}
