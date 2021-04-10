package Sistema.CapaLogica.Exceptions;

public class NoHayJugadoresException extends Exception
{
    public NoHayJugadoresException() {}

    public NoHayJugadoresException(String mensaje)
    {
       super(mensaje);
    }
}