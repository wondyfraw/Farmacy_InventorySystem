package org.farm.fms.etntity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Purchase.class)
public abstract class Purchase_ {

	public static volatile SingularAttribute<Purchase, Integer> quantityInBox;
	public static volatile SingularAttribute<Purchase, Double> totalPrice;
	public static volatile SingularAttribute<Purchase, Integer> purchaseId;
	public static volatile SingularAttribute<Purchase, Date> registrationDate;
	public static volatile SingularAttribute<Purchase, Store> store;

}

