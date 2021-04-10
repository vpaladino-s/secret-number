package Sistema.CapaLogica.Exceptions;

public class PartidaNoFinalizadaException extends Exception
{
    public PartidaNoFinalizadaException() {}

    public PartidaNoFinalizadaException(String mensaje)
    {
       super(mensaje);
    }
}