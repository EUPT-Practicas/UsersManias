
package modelo;

/**
 *
 * @author Javi
 */
public class RegistroPartidas {
    private String nombre;
    private String nombreUsuario;
    private int ganadas;
    private int perdidas;

    public RegistroPartidas(String _nombre, String _nombreUsuario, int _ganadas, int _perdidas) {
        this.nombre = _nombre;
        this.nombreUsuario = _nombreUsuario;
        this.ganadas = _ganadas;
        this.perdidas = _perdidas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getGanadas() {
        return ganadas;
    }
    
    public void sumarGanada(){
        ganadas++;
    }

    public int getPerdidas() {
        return perdidas;
    }

    public void sumarPerdida(){
        perdidas++;
    }
    
}
