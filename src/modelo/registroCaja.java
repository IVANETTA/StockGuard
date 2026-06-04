package modelo;
import java.time.LocalDateTime;

public class registroCaja {
	
	private Empleado empleado;
	private LocalDateTime horaApertura;
	private LocalDateTime horaCierre;
	
	public registroCaja(Empleado empleado) {
		this.empleado=empleado;
		this.horaApertura=LocalDateTime.now();
		this.horaCierre=null;
	}
	//metodo para guardar el horario actual a la hs de cerrar caja
	public void cerrarCaja() {
		horaCierre=LocalDateTime.now();
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public LocalDateTime getHoraApertura() {
		return horaApertura;
	}

	public void setHoraApertura(LocalDateTime horaApertura) {
		this.horaApertura = horaApertura;
	}

	public LocalDateTime getHoraCierre() {
		return horaCierre;
	}

	public void setHoraCierre(LocalDateTime horaCierre) {
		this.horaCierre = horaCierre;
	}
	

}
