
package userslasmanias;

import controlador.ControlUsuarios;
import vista.VistaLogin;
/**
 *
 * @author javi
 */
public class UsersLasManias {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        VistaLogin vistaLogin = new VistaLogin();
        
        ControlUsuarios controlador = new ControlUsuarios(vistaLogin);
        vistaLogin.addControlador(controlador);
    }
}
