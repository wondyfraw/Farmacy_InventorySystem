package org.farm.fms.etntity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Invoice.class)
public abstract class Invoice_ {

	public static volatile SingularAttribute<Invoice, Date> date;
	public static volatile SingularAttribute<Invoice, Sales> salesId;
	public static volatile SingularAttribute<Invoice, Integer> invoiceNum;
	public static volatile SingularAttribute<Invoice, String> customerName;

}

