package com.example.demo.service;

import java.util.List;

import com.example.demo.model.LineaPedido;
import com.example.demo.model.Pedido;
import com.example.demo.model.Producto;
import com.example.demo.model.Usuario;


public interface PedidoService {
	public Integer add(Pedido e);
	public List<Pedido>findAll();
	public Pedido findById(Integer id);
	public Pedido edit(Pedido e);
	public void borrar(Pedido e);

}
