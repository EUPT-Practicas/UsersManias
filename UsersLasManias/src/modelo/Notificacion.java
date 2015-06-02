
package modelo;

/**
 *
 * @author Javi
 */
public class Notificacion {
    private int codigo;
    private String mensaje;
    private String nombreUsuarioEmisor;
    private String nombreUsuarioReceptor;
    private boolean estado;
    private int tipo;
    public Notificacion(int _codigo, String _mensaje, String _nombreUsuarioEmisor, 
            String _nombreUsuarioReceptor, int _estado, int _tipo) {
        this.codigo = _codigo;
        this.mensaje = _mensaje;
        this.nombreUsuarioEmisor = _nombreUsuarioEmisor;
        this.nombreUsuarioReceptor = _nombreUsuarioReceptor;
        if (_estado == 0){
            this.estado = false;
        }else{
           this.estado = true;
        }
        this.tipo = _tipo;
    }
    public Notificacion(String _mensaje, String _nombreUsuarioEmisor, 
            String _nombreUsuarioReceptor, int _estado, int _tipo) {
        this.mensaje = _mensaje;
        this.nombreUsuarioEmisor = _nombreUsuarioEmisor;
        this.nombreUsuarioReceptor = _nombreUsuarioReceptor;
        if (_estado == 0){
            this.estado = false;
        }else{
           this.estado = true;
        }
        this.tipo = _tipo;
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

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    public int getEstado(){
        if(estado){
            return 1;
        }else{
            return 0;
        }
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String toString() {
        return "De: "+ nombreUsuarioEmisor;
    }
}