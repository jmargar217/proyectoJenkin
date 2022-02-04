package com.example.demo.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.LineaPedido;
import com.example.demo.model.Pedido;
import com.example.demo.model.Producto;
import com.example.demo.model.Usuario;
import com.example.demo.repository.LineaPedidoRepository;
import com.example.demo.repository.PedidoRepository;
import com.example.demo.repository.ProductoRepository;


@Service
public class PedidoServiceDB implements PedidoService {
	@Autowired
	private ProductoRepository repositorioProducto;

	@Autowired
	private LineaPedidoRepository repositorioLineas;
	
	@Autowired
	private PedidoRepository repositorio;
	

	/**
	 * Metodo que crea un nuevo pedido vacio
	 * @return Devuelve un nuevo pedido
	 */
	public Pedido crearPedido() {
		return new Pedido();
	}

	
	/**
	 * Metodo para que el pedido por defecto tenga los datos de envio del usuario siempre y cuando no esten vacios ("").
	 * @param pedido pedido que se quiere crear
	 * @param usuario usuario de la sesion
	 */
	public void vincularDatosEnvio(Pedido pedido, Usuario usuario) {
		if(pedido.getDireccion()==null) {
			pedido.setCorreo(usuario.getCorreo());
			pedido.setDireccion(usuario.getCorreo());
			pedido.setTelefono(usuario.getTelefono());
		}
	}
	
	/**
	 * Metodo que busca el pedido que queremos editar y una vez encontrado reemplaza sus propieddades(mapa,datos de envio...)
	 * @param Pedido pedido que queremos editar
	 * @param listaUd nuevo array de integer con las unidades de los productos del pedido a editar
	 * @param tipoEnvio nuevo tipo de envio del pedido a editar
	 * @return Devuelve el pedido con sus nuevos datos de envio y/o lineas de pedido
	*/
	public Pedido reemplazarPedido(Pedido pedido,Integer[] listaUd,String tipoEnvio,
			String correo, String telefono, String direccion) {
		int contador = 0;
		
		pedido.setTipoEnvio(tipoEnvio);
		pedido.setCorreo(correo);
		pedido.setTelefono(telefono);
		pedido.setDireccion(direccion);
		
		
		Iterator<LineaPedido>it = pedido.getListaLinea().iterator();
		while(it.hasNext()) {
			LineaPedido linea = it.next();
			linea.setUnidades(listaUd[contador]);
			
			contador++;
		}
		return pedido;
		
	}
	
	
	/**
	 * Metodo que recibe un pedido y lo borra todas sus lineas y el propio pedido en el repositorio
	 * @param Pedido pedido que va a borrarse de la tabla pedidos
	 */
	@Override
	public void borrar(Pedido e) {
		Iterator<LineaPedido> it = e.getListaLinea().iterator();
		
		while(it.hasNext()) {
			LineaPedido linea = it.next();
			this.repositorioLineas.delete(linea);
		}
		
		repositorio.delete(e);
	}

	/**
	 * Busca un pedido por su referencia y lo devuelve
	 * @return pedido solicitado
	 */
	@Override
	public Pedido findById(Integer id) {

		return repositorio.findById(id).orElse(null);
	}

	/**
	 * Edita un pedido y lo actualiza en el repositorio
	 */
	@Override
	public Pedido edit(Pedido e) {
		return repositorio.save(e);
	}
	
	
	/**
	 * Devuelve la lista de pedidos del repositorio
	 * @return array de pedidos
	 */
	@Override
	public List<Pedido> findAll() {
		return repositorio.findAll();
	}


	/**
	 * Añade un pedido al repositorio y devuelve su referencia
	 * @return referencia del pedido
	 */
	@Override
	public Integer add(Pedido e) {
		repositorio.save(e);
		return e.getRef() ;
	}



	/**
	 * Crea las lineas de pedido de un pedido
	 * @param listaUd lista de unidades de cada producto
	 * @param nuevoPedido pedido al que se le van a añadir las lineas de pedido
	 * @return pedido pedido cargado con sus lineas y unidades
	 */
	public Pedido crearLineas(Integer[] listaUd, Pedido nuevoPedido) {
		int contador = 0;
		List<Producto> productos = this.repositorioProducto.findAll();
		
		
		for(Producto p: productos) {
			LineaPedido linea = new LineaPedido(listaUd[contador], nuevoPedido, p);
			this.repositorioLineas.save(linea);
			nuevoPedido.addLinea(linea);
			contador++;
					}
		return nuevoPedido;
	}

	



	
}
