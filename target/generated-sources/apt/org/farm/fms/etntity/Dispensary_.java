package org.farm.fms.etntity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Dispensary.class)
public abstract class Dispensary_ {

	public static volatile SingularAttribute<Dispensary, String> adminName;
	public static volatile SingularAttribute<Dispensary, Integer> quantityPerPack;
	public static volatile SingularAttribute<Dispensary, Integer> totalUnitPack;
	public static volatile SingularAttribute<Dispensary, Integer> quantityInBox;
	public static volatile SingularAttribute<Dispensary, Integer> quantityPerPackPerUnit;
	public static volatile SingularAttribute<Dispensary, Date> modifiedDate;
	public static volatile SingularAttribute<Dispensary, Store> store;
	public static volatile SingularAttribute<Dispensary, Integer> quantityPerUnit;
	public static volatile SingularAttribute<Dispensary, Date> dispensaryDate;
	public static volatile SingularAttribute<Dispensary, Integer> idDispensary;

}

