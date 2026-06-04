package modelo;

public class clienteFichero {
	private String dni;
	private String nombre;
	private String apellido;
	private double deudaAcumulada;
	
	public clienteFichero(String dni, String nombre, String apellido, double deudaAcumulada) {
		this.dni=dni;
		this.nombre=nombre;
		this.apellido=apellido;
		this.deudaAcumulada=0.0;
	}
	public void sumarDeuda(double monto) {
		this.deudaAcumulada+=monto;
	}
	public void pagarDeuda(double monto) {
		if (monto<= this.deudaAcumulada) {
			this.deudaAcumulada-=monto;
			
		} else {
			this.deudaAcumulada=0.0;

		}
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public double getDeudaAcumulada() {
		return deudaAcumulada;
	}
	public void setDeudaAcumulada(double deudaAcumulada) {
		this.deudaAcumulada = deudaAcumulada;
	}

}
