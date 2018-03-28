package org.farm.fms.etntity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Farmacist.class)
public abstract class Farmacist_ {

	public static volatile SingularAttribute<Farmacist, String> emailAddress;
	public static volatile SingularAttribute<Farmacist, String> password;
	public static volatile SingularAttribute<Farmacist, String> address;
	public static volatile SingularAttribute<Farmacist, String> phoneNumber;
	public static volatile SingularAttribute<Farmacist, Integer> idFarmacist;
	public static volatile SingularAttribute<Farmacist, String> specilization;
	public static volatile SingularAttribute<Farmacist, String> farmacistName;
	public static volatile SingularAttribute<Farmacist, Date> creationDate;
	public static volatile SingularAttribute<Farmacist, Double> farmacistFee;
	public static volatile SingularAttribute<Farmacist, Date> unpdatedDate;

}

