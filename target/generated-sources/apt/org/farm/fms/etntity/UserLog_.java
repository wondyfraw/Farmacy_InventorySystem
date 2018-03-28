package org.farm.fms.etntity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserLog.class)
public abstract class UserLog_ {

	public static volatile SingularAttribute<UserLog, Date> logoutTime;
	public static volatile SingularAttribute<UserLog, Date> loginTime;
	public static volatile SingularAttribute<UserLog, Integer> idUserLog;
	public static volatile SingularAttribute<UserLog, Users> usersId;
	public static volatile SingularAttribute<UserLog, String> userIp;
	public static volatile SingularAttribute<UserLog, String> userName;
	public static volatile SingularAttribute<UserLog, Integer> status;

}

