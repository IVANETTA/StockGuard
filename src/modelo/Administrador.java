package modelo;

//creo clase hija administrador
public class Administrador extends Usuario{
	
	//invoco constructor de la clase padre
	public Administrador(String codigo, String nombre, String username, String password) {
		super(codigo, nombre, username, password, "Administrador");
		
	}
	 //sobreescribo el metodo abstracto
	@Override
	public void mostrarDashboard() {
		
		System.out.println("Verificando ingreso de: "+ this.getNombre());
	}


}
