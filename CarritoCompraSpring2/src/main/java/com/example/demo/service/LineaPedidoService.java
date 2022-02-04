package com.example.demo.service;

import java.util.List;

import com.example.demo.model.LineaPedido;


public interface LineaPedidoService {
	public LineaPedido add(LineaPedido l);
	public List<LineaPedido>findAll();
	public LineaPedido findById(Integer id);
	public LineaPedido edit(LineaPedido l);
	public void borrar(LineaPedido l);

}
