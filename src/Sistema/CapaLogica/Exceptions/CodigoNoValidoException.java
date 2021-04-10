package Sistema.CapaLogica.Exceptions;

public class CodigoNoValidoException extends Exception
{
    public CodigoNoValidoException() {}

    public CodigoNoValidoException(String mensaje)
    {
       super(mensaje);
    }
}