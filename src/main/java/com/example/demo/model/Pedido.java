package com.example.demo.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
public class Pedido implements Comparable<Pedido> {
	
	/**
	 * Referencia del pedido(id) que va a ser igual al contador
	 */
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer ref;
	/**
	 * Array de LineaPedida que contiene el producto, las unidades y el pedido
	 */
	@OneToMany(cascade = CascadeType.ALL,fetch= FetchType.EAGER, orphanRemoval = true)
	private List<LineaPedido> listaLinea;

	/**
	 * Fecha del pedido que esta inicilizada a la fecha del sistema en el momento de crear una instancia
	 */
	@Column(name = "REGIST_DATE", updatable = false, nullable = false)
	@Type(type="date")
	@Temporal(TemporalType.DATE)
	private Date fecha;
	/**Datos de envio
	 * Tipo de envio que va a tener el pedido
	 */
	private String tipoEnvio;
	/**Dtos de envio
	 * Direccion asociado al pedido
	 */
	private String direccion;
	/**
	 * Datos de envio
	 * Correo electronico asociado al pedido
	 */
	private String correo;
	/**
	 * Datos de envio
	 * Telefono asociado al pedido
	 */
	private String telefono;
	
	
	
	public Pedido() {
		this.fecha= new Date();
		this.listaLinea = new ArrayList<LineaPedido>();
	}
	
	public Pedido(String tipoEnvio) {
		super();
		this.correo="";
		this.direccion="";
		this.telefono="";
		this.tipoEnvio=tipoEnvio;
		this.fecha= new Date();
	}
	

	public String getTipoEnvio() {
		return tipoEnvio;
	}

	
	
	public void setTipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}

	/**
	 * Metodo para formatar la fecha del pedido a un formato especifico
	 * @return Devuelve la fecha ordenada en el formato indicado
	 */
	public String getFechaOrdenada() {
		return new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(this.fecha);
	}
	
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
	public void addLinea(LineaPedido linea) {
		this.listaLinea.add(linea);
	}

	

	public List<LineaPedido> getListaLinea() {
		return listaLinea;
	}

	public void setListaLinea(List<LineaPedido> listaLinea) {
		this.listaLinea = listaLinea;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ref);
	}

	public Integer getRef() {
		return ref;
	}

	public void setRef(Integer ref) {
		this.ref = ref;
	}

	/**
	 * Metodo para indicar cuando un pedido es igual a otro, en este caso por referencia
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return ref == other.ref;
	}

	

	

	@Override
	public String toString() {
		return "Pedido [ref=" + ref + ", listaLinea=" + listaLinea + ", fecha=" + fecha + ", tipoEnvio=" + tipoEnvio
				+ ", direccion=" + direccion + ", correo=" + correo + ", telefono=" + telefono + "]";
	}

	/**
	 * Metodo para ordenar los pedidos por fecha. La fecha mas reciente primero.
	 */
	@Override
	public int compareTo(Pedido o) {
		int resultado = 0;
		
		if(this.fecha.before(o.getFecha())){
			resultado = 1;
		}else {
			resultado = -1;
		}
		return resultado;
	}



	public String getDireccion() {
		return direccion;
	}



	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}



	public String getCorreo() {
		return correo;
	}



	public void setCorreo(String correo) {
		this.correo = correo;
	}



	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	

	
	
}
