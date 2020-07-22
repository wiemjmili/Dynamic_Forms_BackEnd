package com.app;

import com.app.model.Group;
import com.app.model.User;

import junit.framework.TestCase;

public class SpringJunitTest extends TestCase {

	private User user;
	private Group gp;

	protected void setUp() throws Exception {
		user = new User("nom1", "nom1@bd.com");
		gp = new Group("1", "group1");
	}

	protected void tearDown() throws Exception {
		user = null;
		gp = null;
	}

	public void testUser() {
		assertNotNull("L'instance n'est pas crée", user);
	}

	public void testGetNom() {
		assertEquals("Le nom est incorrect", "nom1", user.getUsername());
	}

	public void testSetNom() {
		user.setUsername("nom2");
		assertEquals("Le nom est incorrect", "nom2", user.getUsername());
	}

	public void testGetPrenom() {
		assertEquals("L'email est incorrect", "nom1@bd.com", user.getEmail());
	}

	public void testSetPrenom() {
		user.setEmail("nom2@bd.com");
		assertEquals("L'email est incorrect", "nom2@bd.com", user.getEmail());
	}

	public void testGroup() {
		assertNotNull("L'instance n'est pas crée", gp);
	}

	public void testGetNomGp() {
		assertEquals("Le nom est incorrect", "group1", gp.getName());
	}

	public void testSetNomGp() {
		gp.setName("group2");
		assertEquals("Le nom est incorrect", "group2", gp.getName());
	}

	public void testGetId() {
		assertEquals("L'id est incorrect", "1", gp.getId());
	}

	public void testSetId() {
		gp.setId("2");
		assertEquals("L'id est incorrect", "2", gp.getId());
	}

}
