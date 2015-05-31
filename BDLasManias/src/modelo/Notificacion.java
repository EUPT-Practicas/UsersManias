
package modelo;

/**
 *
 * @author Javi
 */
public class Notificacion {
    private String mensaje;
    private String nombreUsuarioEmisor;
    private String nombreUsuarioReceptor;
    private boolean estado;

    public Notificacion(String _mensaje, String _nombreUsuarioEmisor, 
            String _nombreUsuarioReceptor, int _estado) {
        this.mensaje = _mensaje;
        this.nombreUsuarioEmisor = _nombreUsuarioEmisor;
        this.nombreUsuarioReceptor = _nombreUsuarioReceptor;
        if (_estado == 0){
            this.estado = false;
        }else{
           this.estado = true;
        }
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombreUsuarioEmisor() {
        return nombreUsuarioEmisor;
    }

    public void setNombreUsuarioEmisor(String nombreUsuarioEmisor) {
        this.nombreUsuarioEmisor = nombreUsuarioEmisor;
    }

    public String getNombreUsuarioReceptor() {
        return nombreUsuarioReceptor;
    }

    public void setNombreUsuarioReceptor(String nombreUsuarioReceptor) {
        this.nombreUsuarioReceptor = nombreUsuarioReceptor;
    }

    public boolean estaLeido() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String toString() {
        return "\nNotificacion emitida por "+nombreUsuarioEmisor+" "
                + "para "+nombreUsuarioReceptor+""
                + " con mensaje: \n"+mensaje+"\n";
    }
}
