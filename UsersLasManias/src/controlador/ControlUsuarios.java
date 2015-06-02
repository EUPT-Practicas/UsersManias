
package controlador;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import modelo.GestorAmigos;
import modelo.GestorBD;
import modelo.GestorNotificaciones;
import modelo.GestorUsuarios;
import modelo.Notificacion;
import modelo.Usuario;
import vista.VistaLogin;
import vista.VistaNotificaciones;
import vista.VistaPrincipal;

/**
 *
 * @author Javi
 */
public class ControlUsuarios{
    public int CONECTADO = 2;
    public int NO_CONECTADO = 1;
    public int EN_JUEGO = 0;
    public int MENSAJE = 0;
    public int INVITACION_AMISTAD = 1;
    public int INVITACION_JUGAR = 2;
    private VistaLogin vistaLogin;
    private VistaPrincipal vistaPrincipal;
    private VistaNotificaciones vistaNotificaciones;
    private GestorUsuarios gUsuarios = new GestorUsuarios();
    private GestorAmigos gAmigos = new GestorAmigos();
    private GestorNotificaciones gNotificaciones = new GestorNotificaciones();
    private Usuario usuarioPrincipal;
    public ControlUsuarios(VistaLogin v) {
        
        this.vistaLogin  = v;
        this.vistaLogin.setVisible(true);
    }
    
    public boolean nuevoUsuario(Usuario _u){
        if(gUsuarios.crearUsuario(_u)){
            return true;
        }else{
            return false;
        }
    }
    public boolean loginCorrecto(String _nombre, String _pass){
        if(gUsuarios.comprobarAutenticacion(_nombre, _pass)){
            /**
             * Si se loguea se crea un usuario nuevo consus amigos, 
             * notificaciones y datos para usarlo como modelo.
             */
            usuarioPrincipal = new Usuario(_nombre);
            usuarioPrincipal = gUsuarios.getUsuario(_nombre);
            usuarioPrincipal.setNotificaciones(gNotificaciones.getNotificaciones(_nombre));
            usuarioPrincipal.setAmigos(gAmigos.getListaAmigos(_nombre));
            usuarioPrincipal.addObserver(vistaPrincipal);
            gUsuarios.cambiarEstadoUsuario(_nombre, CONECTADO);
            return true;
        }else{
            return false;
        }
    }
    
    public void setVPrincipal(VistaPrincipal _vp){
        this.vistaPrincipal = _vp;
    }
    
    public Set<Usuario> buscarUsuarios(String _nombreUsuario){
        return gUsuarios.getBusquedaUsuario(_nombreUsuario);
    }
    public Set<Usuario> listaUsuarios(){
        return gUsuarios.getListaUsuarios();
    }
    
    public Set<Usuario> listaAmigos(String nombreUsuario) {
        return gAmigos.getListaAmigos(nombreUsuario);
    }
    public Set<Notificacion> listaNotificaciones(String nombreUsuario) {
        return gNotificaciones.getNotificaciones(nombreUsuario);
    }
    
    public boolean eliminarAmigo(String nombreUsuario, String nombreAmigo){
        if(gAmigos.eliminarAmigo(nombreUsuario, nombreAmigo)){
            gAmigos.eliminarAmigo(nombreAmigo, nombreUsuario);
            usuarioPrincipal.setAmigos(gAmigos.getListaAmigos(nombreUsuario));
            return true;
        }else{
            return false;
        }
    }
    
    public boolean enviarNotificacionAmistad(String _nombreUsuario, String _nombreAmigo){
        Notificacion notif = new Notificacion("Invitación de amistad", _nombreUsuario, _nombreAmigo, 0, 1);
        gNotificaciones.enviarNotificacion(notif);
        return true;
    }
    
    public boolean enviarNotificacionJugar(String _nombreUsuario, String _nombreAmigo){
        Notificacion notif = new Notificacion("Invitación para jugar una partida", _nombreUsuario, _nombreAmigo, 0, 2);
        gNotificaciones.enviarNotificacion(notif);
        return true;
    }
    
    public String usuarioAleatorio(){
        return gUsuarios.getUsuarioAleatorio();
    }
    
    public Set<Usuario> listaAmigosConectados(String _nombre, int _estado) {
        return gAmigos.getListaAmigosConectados(_nombre, _estado);
    }
    public int aceptarNotificacion(int _cod){
        Notificacion notificacion = gNotificaciones.obtenerNotificacion(_cod, usuarioPrincipal.getNombreUsuario());
        if(notificacion.getTipo() == INVITACION_AMISTAD){
            Notificacion notif = new Notificacion("Tu solicitud de amistad ha sido aceptada", notificacion.getNombreUsuarioReceptor(), notificacion.getNombreUsuarioEmisor(), 0, 0);
            gNotificaciones.enviarNotificacion(notif);
            gAmigos.agregarAmigo(notificacion.getNombreUsuarioEmisor(), notificacion.getNombreUsuarioReceptor());
            gAmigos.agregarAmigo(notificacion.getNombreUsuarioReceptor(), notificacion.getNombreUsuarioEmisor());
            gNotificaciones.eliminarNotificacion(_cod);
            usuarioPrincipal.setNotificaciones(gNotificaciones.getNotificaciones(usuarioPrincipal.getNombreUsuario()));
            return INVITACION_AMISTAD;
        }if(notificacion.getTipo() == INVITACION_JUGAR){
            //jugar notificacion.getNombreUsuarioEmisor(), notificacion.getNombreUsuarioReceptor()
            return INVITACION_JUGAR;
        }
        return MENSAJE;
    }
    
    public int rechazarNotificacion(int _cod){
        Notificacion notificacion = gNotificaciones.obtenerNotificacion(_cod, usuarioPrincipal.getNombreUsuario());
        if(notificacion.getTipo() == INVITACION_AMISTAD){
            Notificacion notif = new Notificacion("Tu solicitud de amistad ha sido rechazada", notificacion.getNombreUsuarioReceptor(), notificacion.getNombreUsuarioEmisor(), 0, 0);
            gNotificaciones.enviarNotificacion(notif);
            gNotificaciones.eliminarNotificacion(_cod);
            usuarioPrincipal.setNotificaciones(gNotificaciones.getNotificaciones(usuarioPrincipal.getNombreUsuario()));
            return INVITACION_AMISTAD;
        }if(notificacion.getTipo() == INVITACION_JUGAR){
            Notificacion notif = new Notificacion("Tu solicitud para jugar ha sido rechazada", notificacion.getNombreUsuarioReceptor(), notificacion.getNombreUsuarioEmisor(), 0, 0);
            gNotificaciones.enviarNotificacion(notif);
            gNotificaciones.eliminarNotificacion(_cod);
            return INVITACION_JUGAR;
        }else{
            gNotificaciones.eliminarNotificacion(_cod);
            return MENSAJE;
        }
    }
    
    public boolean existeUsuarioEmail(String _usuario, String _email){
        return gUsuarios.existeUsuario(_usuario) || gUsuarios.existeEmail(_email);
    }
    
    public void acaba(String _nombre){
        gUsuarios.cambiarEstadoUsuario(_nombre, NO_CONECTADO);
    }
}
