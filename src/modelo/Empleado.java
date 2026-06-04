package modelo;

public class Empleado extends Usuario{
	public Empleado(String codigo, String nombre, String username, String password) {
		
		//super llama al constructor de usuario (Clase padre)
		super(codigo, nombre, username, password, "Empleado/a");
		
	}
	@Override
	public void mostrarDashboard() {
		System.out.println("Mostrando pantalla inicial para: " + this.getNombre());
	}
	

}
