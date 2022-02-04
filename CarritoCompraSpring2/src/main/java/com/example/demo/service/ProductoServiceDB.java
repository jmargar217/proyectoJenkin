package com.example.demo.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Producto;
import com.example.demo.repository.ProductoRepository;


@Service("productoServiceDB")
public class ProductoServiceDB implements ProductoService {
	
	@Autowired
	private ProductoRepository repositorio;
	
	/**
	 * Añade producto a la entidad Productos
	 */
	@Override
	public Producto add(Producto e) {
		return repositorio.save(e);
	}
	

	/**
	 * Metodo para obtener la lista de productos
	 * @return la lista de productos
	 */
	@Override
	public List<Producto> findAll() {
		return repositorio.findAll();
	}
	
	/**
	 * Metodo para obtener un producto concreto a través de su id
	 */
	@Override
	public Producto findById(Integer id) {
		return repositorio.findById(id).orElse(null);
	}

	@Override
	public Producto edit(Producto e) {
		return repositorio.save(e);
	}
	
	
	/**
	 * Metodo para inicializar la lista de productos en el momento de creación del servicio producto
	 */
	@PostConstruct
	public void init() {
				repositorio.save(new Producto("Manzana", 0.35));
				repositorio.save(new Producto("Pera", 0.15));
				repositorio.save(new Producto("Platano", 0.45));
				repositorio.save(new Producto("Aguacate", 0.75));
				repositorio.save(new Producto("Kiwi", 0.50));
				repositorio.save(new Producto("Melocotón", 0.40));
		
		
	}
}
