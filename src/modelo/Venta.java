package modelo;

import java.time.LocalDateTime;

public class Venta {

	private String idVenta;
	private Empleado empleado;
	private Articulo[] listaArticulos;	
	private double total;
	private String metodoPago;//trnsferencia, contado o creditos
	private double entrega;//para cuentas de creditos
	private LocalDateTime fechaHora; //cuando se realizo la venta
	
	//constructor
	public Venta (String idVenta, Empleado empleado, Articulo[] listaArticulos, String metodoPago, double entrega) {
		this.idVenta=idVenta;
		this.empleado=empleado;
		this.listaArticulos=listaArticulos;
		this.metodoPago=metodoPago;
		this.entrega=entrega;
		this.fechaHora=LocalDateTime.now();
		this.total =calcularTotal();
	}
		
	private double calcularTotal() {
		double suma=0.0;
		if (listaArticulos != null) {
			for (int i = 0; i < listaArticulos.length; i++) {
				if (listaArticulos[i] != null) {
					suma+= listaArticulos[i].getPrecioVenta();	
				}
				
				
				}
			}
			
		
			return suma;
	
		}

	public String getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(String idVenta) {
		this.idVenta = idVenta;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Articulo[] getListaArticulos() {
		return listaArticulos;
	}

	public void setListaArticulos(Articulo[] listaArticulos) {
		this.listaArticulos = listaArticulos;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	public double getEntrega() {
		return entrega;
	}

	public void setEntrega(double entrega) {
		this.entrega = entrega;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}
				
	
	

}
