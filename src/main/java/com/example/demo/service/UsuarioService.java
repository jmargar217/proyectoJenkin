package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Pedido;
import com.example.demo.model.Usuario;


public interface UsuarioService {
	public Usuario add(Usuario e);
	public List<Usuario>findAll();
	public Usuario findById(Long id);
	public Usuario edit(Usuario e);
	public Pedido addPedido(Usuario e,Pedido p);
}
