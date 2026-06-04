package modelo;
//creamos la clase articulo
public class Articulo {
	
	private String id;
	private String nombre;
	private double precioCosto;
	private double precioVenta;
	private String descripcion;
	private String proveedor;
	private String categoria;
	private int cantidad;
	
	public Articulo (String id, String nombre, double precioCosto, double precioVenta, String descripcion, String proveedor, String categoria, int cantidad) {
		this.id=id;
		this.nombre=nombre;
		this.precioCosto=precioCosto;
		this.precioVenta=precioVenta;
		this.descripcion=descripcion;
		this.proveedor=proveedor;
		this.categoria=categoria;
		this.cantidad=cantidad;
	}
	//constructor para cargas de Stock rapidas 
	public Articulo(String id, double precioVenta, double precioCosto, int cantidad) {
		this.id=id;
		this.precioVenta=precioVenta;
		this.precioCosto=precioCosto;
		this.cantidad=cantidad;
		
		//inicializo 
		this.nombre="Sin nombre";
		this.descripcion="Sin descripción";
		this.proveedor="No especificado";
		this.categoria="General";
		
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getPrecioCosto() {
		return precioCosto;
	}
	public void setPrecioCosto(double precioCosto) {
		this.precioCosto = precioCosto;
	}
	public double getPrecioVenta() {
		return precioVenta;
	}
	public void setPrecioVenta(double precioVenta) {
		this.precioVenta = precioVenta;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getProveedor() {
		return proveedor;
	}
	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	

}
