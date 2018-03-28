package org.farm.fms.entity.ejb;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import org.farm.exception.MyUserException;
import org.farm.fms.etntity.Users;
import org.farm.utils.Utils;

@Stateless
public class UsersEJB extends AbstructHome<Users, Integer> {

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub

	}

	public Users searchUserByEmail(String email) throws MyUserException {
		try {
			TypedQuery<Users> usersQuery = entityManager.createNamedQuery("searchUserByEmail", Users.class);
			usersQuery.setParameter("email", email);
			Users queryResult = usersQuery.getSingleResult();
			return queryResult;
		} catch (Exception e) {
			throw new MyUserException("No User found exception", e);
		}

	}

	public Boolean exist(String email) throws MyUserException {
		return searchUserByEmail(email) != null;
	}

	public void register(Users users) {
		Users shaUsers = new Users();
		shaUsers.setFullName(users.getFullName());
		shaUsers.setAddress(users.getAddress());
		shaUsers.setEmail(users.getEmail());
		shaUsers.setRegistrationdate(users.getRegistrationdate());
		shaUsers.setUserType(users.getUserType());
		shaUsers.setPassword(Utils.encryptSHA1(users.getPassword()));
		persistEntity(shaUsers);
	}

	@Override
	public Users findById(Integer id) {

		return findById(Users.class, id);
	}

}
