package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

@Entity
public class Usuario {
	/**
	 * Genera una id automatica para cada usuario
	 */
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * El nombre del usuario junto a la anotacion NoEmpty que utilizare en las validaciones
	 */
	@NotEmpty(message="El usuario no puede estar vacio")
	private String user;
	/**
	 * La password del usuario junto a la anotacion NoEmpty que utilizare en las validaciones
	 */
	@NotEmpty(message="La contraseña no puede estar vacia")
	private String password;
	/**
	 * El correo que tiene asociado el usuario
	 */
	private String correo;
	/**
	 * El nombre que tiene asociado el usuario
	 */
	private String nombre;
	/**
	 * La direccion que tiene asociado el usuario
	 */
	private String direccion;
	/**
	 * El telefono que tiene asociado el usuario
	 */
	private String telefono;
	
	/**
	 * Un usuario tiene n pedidos
	 */
	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval = true)
	private List<Pedido>pedidos = new ArrayList<>();
	
	
	public Usuario() {
	}
	
	
	
	
	public Usuario(@NotEmpty(message = "El usuario no puede estar vacio") String user,
			@NotEmpty(message = "La contraseña no puede estar vacia") String password, String correo, String nombre,
			String direccion, String telefono) {
		super();
		this.user = user;
		this.password = password;
		this.correo = correo;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
	}


	public Usuario(String user, String password) {
		this.user=user;
		this.password=password;
	}
	
	
	
	/**
	 * Metodo para añadir un pedido a la lista de pedidos del usuario
	 * @param pedido 
	 */
	public void addPedido(Pedido pedido) {
		this.pedidos.add(pedido);
		
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public String getCorreo() {
		return correo;
	}

	public String getNombre() {
		return nombre;
	}


	public String getDireccion() {
		return direccion;
	}



	public String getTelefono() {
		return telefono;
	}


	/**
	 * Metodo para obtener la lista de pedidos ordenados
	 * @return la lista de pedidos ordenados por fecha
	 */
	public List<Pedido> getPedidos() {
		//Collections.sort(this.pedidos);
		return this.pedidos;
	}




	




	@Override
	public int hashCode() {
		return Objects.hash(password, user);
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(password, other.password) && Objects.equals(user, other.user);
	}




	@Override
	public String toString() {
		return "Usuario [id="+ id + "user=" + user + ", password=" + password + ", correo=" + correo + ", nombre=" + nombre
				+ ", direccion=" + direccion + ", telefono=" + telefono + ", pedidos=" + pedidos + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	
}
