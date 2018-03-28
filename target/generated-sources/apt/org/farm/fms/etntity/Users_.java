package org.farm.fms.etntity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Users.class)
public abstract class Users_ {

	public static volatile SingularAttribute<Users, String> password;
	public static volatile SingularAttribute<Users, String> address;
	public static volatile SingularAttribute<Users, Integer> usersId;
	public static volatile SingularAttribute<Users, String> fullName;
	public static volatile SingularAttribute<Users, Date> registrationdate;
	public static volatile SingularAttribute<Users, Date> updatedDate;
	public static volatile SingularAttribute<Users, String> userType;
	public static volatile SingularAttribute<Users, String> email;

}

