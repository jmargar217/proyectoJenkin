package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Producto;

public interface ProductoService {
	public Producto add(Producto e);
	public List<Producto>findAll();
	public Producto findById(Integer id);
	public Producto edit(Producto e);
}
