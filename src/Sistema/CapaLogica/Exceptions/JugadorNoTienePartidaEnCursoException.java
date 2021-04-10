package Sistema.CapaLogica.Exceptions;

public class JugadorNoTienePartidaEnCursoException extends Exception{
	
    public JugadorNoTienePartidaEnCursoException() {}

    public JugadorNoTienePartidaEnCursoException(String mensaje)
    {
       super(mensaje);
    }

}
