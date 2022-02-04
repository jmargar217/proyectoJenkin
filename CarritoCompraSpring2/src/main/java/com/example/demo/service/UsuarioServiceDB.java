package com.example.demo.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.demo.model.Pedido;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;

@Primary
@Service("usuarioServiceDB")
public class UsuarioServiceDB implements UsuarioService {
	
	@Autowired
	private UsuarioRepository repositorio;
	
	
	/**
	 * Añade un usuario al repositorio de usuarios
	 */
	@Override
	public Usuario add(Usuario e) {
		return repositorio.save(e);
	}
	/**
	 * Devuelve la lista de usuarios del repositorio usuarios
	 * @return array de usuarios
	 */
	@Override
	public List<Usuario> findAll() {
		return repositorio.findAll();
	}

	/**
	 * Busca un pedido a través de su id
	 * @return usuario usuario que coincide con esa id en el repositorio o null en caso de no encontrarse
	 */
	@Override
	public Usuario findById(Long id) {
		return repositorio.findById(id).orElse(null);
	}

	/**
	 * Edita un usuario existente en el repositorio de usuarios actualizando su lista de pedidos
	 */
	@Override
	public Usuario edit(Usuario e) {
		
		Usuario aux = findById(e.getId());
		
		aux.setPedidos(e.getPedidos());
		return repositorio.save(aux);
	}
	
	/**
	 * Carga los usuarios por defecto que vamos a utilizar
	 */
	@PostConstruct
	public void init() {
		repositorio.save(new Usuario("jmargar217", "4959", "joaquin96.RM@gmail.com", "Joaquin", "C/Pablo Picasso Nº40", "645810775"));
		repositorio.save(new Usuario("jorge", "123", "jorge@gmail.com", "Jorge", "C/Diamantino Nº21", "645102589"));
		repositorio.save(new Usuario("franA", "777", "franarroyo@gmail.com", "Fran", "C/Prio Nº3", "698789789"));
	}

	/**
	 * Añade un pedido al usuario
	 */
	@Override
	public Pedido addPedido(Usuario e, Pedido p) {
		e.addPedido(p);
		return p;
	}

	
		
	
	
}


