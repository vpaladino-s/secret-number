package Sistema.CapaLogica;
import java.io.*;


public class Monitor {
	
	private int cantLectores;
	private boolean escribiendo;
	
	public Monitor() {
		this.cantLectores = 0;
		this.escribiendo = false;
		
	}

	public synchronized void comienzoLectura() {
        if (escribiendo) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        cantLectores++;
		//System.out.println("-Comienzo Lectura: CantLectores " + cantLectores + " Escribiendo " + escribiendo );	

   	}
	

	public synchronized void terminoLectura() {
		cantLectores--;
        if (cantLectores==0)
        	notify();
		//System.out.println("-Finalizo Lectura: CantLectores " + cantLectores + " Escribiendo " + escribiendo );	
		
	}
	
	public synchronized void comienzoEscritura() {
		
       if (cantLectores>0 || escribiendo) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    escribiendo=true;
		//System.out.println("-Comienzo Escritura: CantLectores " + cantLectores + " Escribiendo " + escribiendo );	

		
	}
	
	public synchronized void terminoEscritura() {
		
        escribiendo=false;
        if (cantLectores==0)
    		notify();
    	//inalizo Escritura: CantLectores " + cantLectores + " Escribiendo " + escribiendo);	

	}
}
