package org.farm.fms.etntity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Expense.class)
public abstract class Expense_ {

	public static volatile SingularAttribute<Expense, Double> amount;
	public static volatile SingularAttribute<Expense, String> fileName;
	public static volatile SingularAttribute<Expense, String> mimType;
	public static volatile SingularAttribute<Expense, String> invoiceNumber;
	public static volatile SingularAttribute<Expense, String> description;
	public static volatile SingularAttribute<Expense, byte[]> dataFile;
	public static volatile SingularAttribute<Expense, String> userName;
	public static volatile SingularAttribute<Expense, Integer> expenseCode;
	public static volatile SingularAttribute<Expense, Date> expenseDate;

}

