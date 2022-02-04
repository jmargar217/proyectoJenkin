package com.example.demo.controller;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Pedido;
import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioServiceDB;
import com.example.demo.service.LineaPedidoServiceDB;
import com.example.demo.service.PedidoServiceDB;
import com.example.demo.service.ProductoServiceDB;


@Controller
public class CarritoController {
	/**
	 * Sesion que va a utilizar al arrancar
	 */
	@Autowired
	private HttpSession sesion;
	
	/**
	 * Inyeccion del servicio de login
	 */
	@Autowired
	private UsuarioServiceDB servicio;
	
	/**
	 * Inyeccion del servicio de productos
	 */
	@Autowired
	private ProductoServiceDB servicioProducto;
	
	/**
	 * Inyeccion del servicio de pedidos
	 */
	@Autowired
	private PedidoServiceDB servicioPedido;
	
	
	@Autowired
	private LineaPedidoServiceDB servicioLinea;
	
	/**
	 * Variabe estatica que utilizo para no tener que escribir varias veces el redirect al login 
	 */
	private static String redirigeLogin = "redirect:/login";
	
	/**
	 * Nos muestra al acceder al localhost(raiz) o a la ruta especificada el html login
	 * @param model Añade el usuario que se va a crear en el post al hacer login para hacer luego las validaciones
	 * @return Nos devuelve a la pagina de login
	 */
	@GetMapping({"/", "/login"})
	public String listado(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "login";
	}
	
	/**
	 * Invalida la sesión y nos redirige al login
	 * @return Nos devuelve a la pagina de login
	 */
	@GetMapping({"/logout"})
	public String logout() {
	
		sesion.invalidate();
	return redirigeLogin;
	}
	
	
	/**
	 * Recoge los datos del usuario despues de hacer submit en la pagina de login
	 * @param nuevoUsuario Contiene el usuario que se logea
	 * @param bindingResult Contiene los errores que se han podido producir al hacer login (usuario,password o viceversa)
	 * @return Devuelve una ruta dependiendo si hemos iniciado sesión previamente, existen errores al accer login con un determinado usuario o
	 * si los datos del usuario son correctos
	 */
	@PostMapping({"/login/submit"})
	public String seleccion (@Valid @ModelAttribute("usuario") Usuario nuevoUsuario,
			BindingResult bindingResult) {
		
		int pos = this.servicio.findAll().indexOf(nuevoUsuario);
		nuevoUsuario.setId(this.servicio.findAll().get(pos).getId());
		
		Usuario aux = this.servicio.findById(nuevoUsuario.getId());
		sesion.setAttribute("usuario",aux);
		
 		if(aux !=null) {
			boolean isError = bindingResult.hasErrors();
			if(!isError) {
				return "seleccion";
			}else {
				return "login";
			}
		}else {
			return redirigeLogin;
		}
		
	}
	
	/**
	 * Muestra el formulario de un nuevo pedido con diversos campos
	 * @param model contiene la lista de productos obtenidas del servicio producto
	 * @return Devuelve el html del formulario nuevoPedido si nos hemos logueado previamente, de lo contrario nos redirige al login
	 */
	@GetMapping({"/seleccion/nuevoPedido"})
	public String nuevoPedido(Model model) {
		String url = "nuevoPedido";
		if(sesion.getAttribute("usuario")==null) {
			url = redirigeLogin;
		}
		
		model.addAttribute("listaProductos", this.servicioProducto.findAll());
		return url;
	}
	
	
	/**
	 * Recoje las unidades de los productos seleccionados por el usuario que son enviadas desde el formulario nuevoPedido y valida los datos
	 * @param model contiene el mapa con los productos y las unidades indicadas por el usuario y los datos del propio usuario logueado
	 * @param listaUd
	 * @return Devuelve el html "resumen" si los datos están correctos. De encontrarse un valor null o no seleccionar ningun producto
	 *  nos redirige al formulario de nuevo pedido.
	 */
	@PostMapping({"/nuevoPedido/submit"})
	public String resumenPedido(
			Model model, 
			@RequestParam(required=false,name="unidades") Integer[] listaUd) {
		
		String url = "redirect:/seleccion/nuevoPedido";
		int contador = 0;
		for(int i=0; i<listaUd.length;i++) {
			if(listaUd[i] == null) {
				return url;
			}
			else if (listaUd[i]>0) {
				contador++;	
			}
		}
		if(contador >0) {
			
			Pedido nuevoPedido = this.servicioPedido.crearPedido();
			Usuario usuario = (Usuario) sesion.getAttribute("usuario");
			
			this.servicioPedido.vincularDatosEnvio(nuevoPedido, usuario);
			Integer ref = this.servicioPedido.add(nuevoPedido);
		
			nuevoPedido = this.servicioPedido.crearLineas(listaUd,nuevoPedido);
			this.servicioPedido.add(nuevoPedido);
			
			url = "redirect:/nuevoPedido/resumen/"+ref;

		}
		return url;
	}
	
	
	/**
	 * Recibe la referencia del pedido del que hay que sacar sus datos en el resumen
	 * @param ref Contiene la referencia del pedido que resumir
	 * @return Devuelve el html del nuevoPedidoResumen donde vamos a mostrar los datos del pedido concreto
	 */
	@GetMapping({"/nuevoPedido/resumen/{ref}"})
	public String resumenPedido(
			@PathVariable Integer ref,
			Model model) {
			
		model.addAttribute("pedido", this.servicioPedido.findById(ref));
		return "nuevoPedidoResumen";
	
	}
	
	
	
	
	
	/**
	 * Muestra el listado de pedidos del usuario de la sesion al añadir un nuevo pedido
	 * @param model Contiene la lista de pedidos del usuario que se ha logueado
	 * @param tipoEnvio Contiene el tipo de envio seleccionado por el usuario en el formulario resumen de pedido
	 * @return Nos redirige al formulario de nuevoPedido si el tipo de envio es nulo o al listado de pedidos si esta todo correcto
	 */
	@PostMapping({"nuevoPedido/listado"})
	public String listarPedidos(
			@RequestParam(required=false,name="envio") String tipoEnvio,
			@RequestParam(required=false,name="ref") Integer referencia) {
		
		
		String url = "redirect:/listado"; 
		if(tipoEnvio == null) {
			
			//Si no se selecciona envio hay que borrar el pedido porque lo hemos persistido
			//previamente en el /nuevoPedido/submit
			
			Pedido pedido = this.servicioPedido.findById(referencia);
			this.servicioPedido.borrar(pedido);
			url = "redirect:/seleccion/nuevoPedido";
			
		}else {
			Usuario usuario = (Usuario) sesion.getAttribute("usuario");
			
			Pedido pedido = this.servicioPedido.findById(referencia);
			pedido.setTipoEnvio(tipoEnvio);
			usuario.addPedido(pedido);
			
			this.servicioPedido.edit(pedido);
			this.servicio.edit(usuario);   //Al usuario le añado el pedido ya terminado
		
			
			}
		return url;
	}
	
	
	/**
	 * Muestra el listado de pedidos del usuario de la sesion
	 * @param model Contiene la lista de pedidos del usuario que se ha logueado
	 * @return Devuelve el html listado que es donde se imprimen los datos guardados en el model o nos redirige al login si no nos hemos logueado
	 */
	@GetMapping({"listado"})
	public String listarPedidos(Model model) {
		String url = "listar";
		if(sesion.getAttribute("usuario")==null) {
			url =redirigeLogin;
		}
		Usuario usuario = (Usuario) sesion.getAttribute("usuario");
		this.servicio.edit(usuario);
		
		
		model.addAttribute("listaPedidos", this.servicio.findById(usuario.getId()).getPedidos());
		
		return url;
	}

	
	
	/**
	 * Recibe la referencia del pedido que se quiere editar a traves de la ruta, busca el pedido y vincula los datos de envio del usuario con las
	 * propiedades de envio del pedido (telefono,direccion y email).
	 * @param ref Contiene la referencia del pedido que queremos editar
	 * @param model Contiene los datos del pedido seleccionado, del usuario de la sesion y el mapa de productos y unidades de dicho pedido
	 * @return Nos redirige al login si no estamos logueados o al formulario editar pedido.
	 */
	@GetMapping({"/pedido/editar/{ref}"})
	public String editarPedido(@PathVariable Integer ref, Model model) {
		String url = "editar";
		
		if(sesion.getAttribute("usuario")== null) {
			url = redirigeLogin;
		}else {
			Pedido pedido = this.servicioPedido.findById(ref);
		
			model.addAttribute("pedido", pedido);
			model.addAttribute("usuario",sesion.getAttribute("usuario"));
		
		}
		return url;
   
 }
	/**
	 * Recibe los parametros enviados desde el formulario editar (array de unidades,telefono,direccion...), busca el pedido que 
	 * se quiere editar y lo reemplaza por uno nuevo con los datos obtenidos del formulario.
	 * @param ref Contiene la referencia del pedido editado
	 * @param tipoEnvio Contiene el nuevo tipo de envio del pedido editado
	 * @param listaUd Contiene el nuevo array de numeros con las nuevas unidades del pedido editado
	 * @param direccion Contiene la nueva dirección del pedido editado
	 * @param correo Contiene el nuevo correo del pedido editado
	 * @param telefono Contiene el nuevo telefono del pedido editado
	 * @return Nos redirige al listado de pedidos del usuario.
	 */
	@PostMapping({"/editar/submit"})
	public String editarPedidoSubmit(
			@RequestParam(required=false,name="ref")Integer ref,
			@RequestParam(required=false,value="envio") String tipoEnvio,
			@RequestParam(required=false,name="unidades") Integer[] listaUd,
			@RequestParam(required=false,name="direccion")String direccion,
			@RequestParam(required=false,name="correo")String correo,
			@RequestParam(required=false,name="telefono")String telefono)
	{
		String url = "redirect:/listado";
		if("".equals(tipoEnvio) || listaUd.length==0 || "".equals(direccion) || "".equals(correo) || "".equals(telefono) ) {
			url = "/error";
		}else {
			Usuario usuario = (Usuario) sesion.getAttribute("usuario");
			this.servicio.edit(usuario);
			
			Pedido pedido = this.servicioPedido.findById(ref);
			
			pedido = this.servicioPedido.reemplazarPedido(pedido, listaUd, tipoEnvio, correo, telefono, direccion);
			this.servicioPedido.edit(pedido);
		
		}
		return url;
	}
	
	
	/**
	 * Borra el pedido cuya referencia obtiene de la url
	 * @param ref referencia del pedido
	 * @return devuelve el listado una vez borrado el pedido o la pagina de login si no existe la sesion
	 */
	@GetMapping({"/borrar/{ref}"})
	public String borrarPedido(@PathVariable Integer ref) {
		String url = "redirect:/listado";
		
		if(sesion.getAttribute("usuario")==null) {
			url = redirigeLogin;
		}else {
			Usuario usuario = (Usuario) sesion.getAttribute("usuario");
			Pedido pedido = this.servicioPedido.findById(ref);
		
			usuario.getPedidos().remove(pedido);
			this.servicio.edit(usuario);
			
			this.servicioPedido.borrar(pedido);
			
		}
		return url;
		
	}
	
	/**
	 * Pagina de error al opciones de envio del pedido
	 * @return devuelve al login si no existe la sesion o la pagina de error
	 */
	@GetMapping({"/error"})
	public String paginaError() {
		String url = redirigeLogin;
		
		if(sesion.getAttribute("usuario")!=null) {
			url = "error";
		}
		return url;
		
	}
	
	

}	
