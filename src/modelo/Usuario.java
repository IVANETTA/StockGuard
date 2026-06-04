package modelo;
//defino clase padre usario
public abstract class Usuario {
	
	// describo atributos privados
	private String codigo;
    private String nombre;
    private String username;
    private String password;
    private String rol;
    
    
    //constructor
    public Usuario(String codigo, String nombre, String username, String password, String rol) {
    	this.codigo=codigo;
    	this.nombre=nombre;
    	this.username=username;
    	this.password=password;
    	this.rol=rol;
    }
    //guardo y recupero datos con metodos setter y getter
    public String getCodigo() {
    	return codigo;
    }
    
    public void setCodigo(String codigo) {
    	this.codigo=codigo;
    }
    
    public String getNombre() {
    	return nombre;
    }
    
    public void setNombre(String nombre) {
    	this.nombre=nombre;
    }
    
    public String getUsername() {
    	return username;
    }
    
    public void setUsername(String username) {
    	this.username=username;
    }
    
    public String getPassword() {
    	return password;
    }
    
    public void setPassword(String password) {
    	this.password=password;
    }
    
    public String getRol() {
    	return rol;
    }
    
    public void setRol(String rol) {
    	this.rol=rol;
    }
    
    public abstract void mostrarDashboard();

}
