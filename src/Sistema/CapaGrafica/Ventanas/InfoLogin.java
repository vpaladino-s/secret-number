package Sistema.CapaGrafica.Ventanas;


public class InfoLogin {
	
	private String usuario;
	private String codigo;
	private static InfoLogin instancia;
	
	private InfoLogin() {

	 }
	
	public static InfoLogin getInstancia()  { 
		if(instancia== null) 
			instancia = new InfoLogin();
		return instancia;
	}
	
	public String getUsuario()
	{
		return usuario;
	}
	
	public String getCodigo()
	{
		return codigo;
	}
	
	public void setUsuario(String u)
	{
		usuario =u;
	}
	
	public void setCodigo(String c)
	{
		codigo =c;
	}


}
