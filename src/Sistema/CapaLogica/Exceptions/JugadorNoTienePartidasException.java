package Sistema.CapaLogica.Exceptions;

public class JugadorNoTienePartidasException extends Exception
{
    public JugadorNoTienePartidasException() {}

    public JugadorNoTienePartidasException(String mensaje)
    {
       super(mensaje);
    }
}