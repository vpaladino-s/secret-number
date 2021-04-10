package Sistema.CapaLogica.Exceptions;

public class JugadorYaExisteException extends Exception
{
    public JugadorYaExisteException() {}

    public JugadorYaExisteException(String mensaje)
    {
       super(mensaje);
    }
}