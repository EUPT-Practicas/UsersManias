
package bdlasmanias;

import controlador.ControlUsuarios;
import vista.Login;
/**
 *
 * @author javi
 */
public class BDLasManias {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        Login vistaLogin = new Login();
        
        ControlUsuarios controlador = new ControlUsuarios(vistaLogin);
        vistaLogin.addControlador(controlador);
    }
}
