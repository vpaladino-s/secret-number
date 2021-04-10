package Sistema.CapaLogica.Exceptions;


public class JugadorNoExisteException extends Exception
{
      public JugadorNoExisteException() {}

      public JugadorNoExisteException(String mensaje)
      {
         super(mensaje);
      }
 }