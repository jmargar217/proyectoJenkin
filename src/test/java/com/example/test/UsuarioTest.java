package com.example.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.service.UsuarioServiceDB;

class UsuarioTest {
	@Autowired UsuarioServiceDB servicioUser;

	@Test
	void test() {
		assertEquals(3, this.servicioUser.findAll().size());
	}

}
