package com.example.demo.model;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class LineaPedido {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer ref;
	
	private Integer unidades;
	
	
	// un pedido tiene n lineas
	@ManyToOne(cascade = CascadeType.ALL,fetch= FetchType.EAGER)
	private Pedido pedido;
	
	
	//un producto esta en n lineas de pedidos
	@ManyToOne(fetch= FetchType.EAGER)
	private Producto producto;

	
	public LineaPedido() {
		
	}
	
	public LineaPedido(Integer unidades, Pedido pedido, Producto producto) {
		super();
		this.unidades = unidades;
		this.pedido = pedido;
		this.producto = producto;
	}




	public Integer getRef() {
		return ref;
	}

	public void setRef(Integer ref) {
		this.ref = ref;
	}

	public Integer getUnidades() {
		return unidades;
	}

	public void setUnidades(Integer unidades) {
		this.unidades = unidades;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Producto getProducto() {
		return producto;
	}
	


	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ref);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LineaPedido other = (LineaPedido) obj;
		return Objects.equals(ref, other.ref);
	}

	
	
	
	
}
