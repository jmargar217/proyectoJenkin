package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.LineaPedido;
import com.example.demo.repository.LineaPedidoRepository;

@Service
public class LineaPedidoServiceDB implements LineaPedidoService {
	@Autowired
	private LineaPedidoRepository repositorioLinea;
	

	@Override
	public LineaPedido add(LineaPedido l) {
		return repositorioLinea.save(l);
	}

	@Override
	public List<LineaPedido> findAll() {
		return repositorioLinea.findAll();
	}

	@Override
	public LineaPedido findById(Integer id) {
		return repositorioLinea.findById(id).orElse(null);
	}

	@Override
	public LineaPedido edit(LineaPedido l) {
		return repositorioLinea.save(l);
	}

	@Override
	public void borrar(LineaPedido l) {
		repositorioLinea.delete(l);
		
	}

}
