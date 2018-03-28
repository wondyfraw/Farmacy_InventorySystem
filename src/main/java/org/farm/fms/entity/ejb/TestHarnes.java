package org.farm.fms.entity.ejb;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.farm.fms.etntity.Users;

@Stateless
public class TestHarnes {

	@EJB
	private StoreEJB store;

	@EJB
	private static UsersEJB userEJB;

	public static void main(String[] args) {
		TestHarnes test = new TestHarnes();
		// test.resister();
		Date now = Calendar.getInstance().getTime();
		Users users = new Users();
		users.setFullName("Wonde");
		users.setAddress("via lame");
		users.setEmail("www@gmail.com");
		users.setPassword("123456");
		users.setRegistrationdate(now);
		users.setUserType("user");
		userEJB.persistEntity(users);

	}

	public void resister() {
		Date now = Calendar.getInstance().getTime();
		Users users = new Users();
		users.setFullName("Wonde");
		users.setAddress("via lame");
		users.setEmail("www@gmail.com");
		users.setPassword("123456");
		users.setRegistrationdate(now);
		users.setUserType("user");
		userEJB.persistEntity(users);
	}

}
