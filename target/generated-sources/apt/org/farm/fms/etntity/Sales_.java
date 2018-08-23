package org.farm.fms.etntity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Sales.class)
public abstract class Sales_ {

	public static volatile SingularAttribute<Sales, Double> unitPrice;
	public static volatile SingularAttribute<Sales, Integer> dispensary;
	public static volatile SingularAttribute<Sales, Integer> idSales;
	public static volatile SingularAttribute<Sales, Integer> quantity;
	public static volatile SingularAttribute<Sales, Double> totalPrice;
	public static volatile SingularAttribute<Sales, String> packUnit;
	public static volatile SingularAttribute<Sales, String> weight;
	public static volatile SingularAttribute<Sales, Date> registrationdate;
	public static volatile SingularAttribute<Sales, Integer> dose;
	public static volatile SingularAttribute<Sales, String> unit;
	public static volatile SingularAttribute<Sales, String> drugName;
	public static volatile SingularAttribute<Sales, String> salesPerson;
	public static volatile SingularAttribute<Sales, String> brand;
	public static volatile SingularAttribute<Sales, String> batchNumber;

}

