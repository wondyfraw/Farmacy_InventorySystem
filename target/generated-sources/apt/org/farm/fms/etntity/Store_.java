package org.farm.fms.etntity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Store.class)
public abstract class Store_ {

	public static volatile SingularAttribute<Store, Double> unitPrice;
	public static volatile SingularAttribute<Store, Integer> quantityInBox;
	public static volatile SingularAttribute<Store, Date> modifyDate;
	public static volatile SingularAttribute<Store, Double> totalPrice;
	public static volatile SingularAttribute<Store, String> packUnit;
	public static volatile SingularAttribute<Store, Double> salesPrice;
	public static volatile SingularAttribute<Store, String> packType;
	public static volatile SingularAttribute<Store, String> weight;
	public static volatile SingularAttribute<Store, Integer> storeId;
	public static volatile SingularAttribute<Store, Integer> quantityperUnitperTab;
	public static volatile SingularAttribute<Store, String> unit;
	public static volatile SingularAttribute<Store, String> drugName;
	public static volatile SingularAttribute<Store, Date> registrationDate;
	public static volatile SingularAttribute<Store, Integer> quantityperBoxperUnit;
	public static volatile SingularAttribute<Store, Date> manufacturingDate;
	public static volatile SingularAttribute<Store, String> bathcNumber;
	public static volatile SingularAttribute<Store, Date> expireDate;
	public static volatile SingularAttribute<Store, String> brand;
	public static volatile SingularAttribute<Store, String> deletedStatus;

}

