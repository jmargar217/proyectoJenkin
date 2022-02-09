package com.example.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.demo.model.Usuario;

class UsuarioTest {
	Usuario usuario;
	
	
	@BeforeEach
	void init() {
		usuario = new Usuario("jmargar217", "4959");
	}

	@Test
	void test() {
		assertEquals("pepe", usuario.getUser());
	}

}
