package org.farm.fms.etntity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "expense", schema = "myfms")
public class Expense implements Serializable {

	private static final long serialVersionUID = -8686228350401399741L;

	private Integer expenseCode;
	private Date expenseDate;
	private String description;
	private Double amount;
	private String userName;

	public Expense() {
		// required by JPA
	}

	@Id
	@SequenceGenerator(name = "myfms.expense_expense_code_seq", sequenceName = "myfms.expense_expense_code_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myfms.expense_expense_code_seq")
	@Column(name = "expense_code", unique = true, nullable = false)
	public Integer getExpenseCode() {
		return expenseCode;
	}

	@Column(name = "expense_date")
	public Date getExpenseDate() {
		return expenseDate;
	}

	@Column(name = "description", length = 200)
	public String getDescription() {
		return description;
	}

	@Column(name = "amount")
	public Double getAmount() {
		return amount;
	}

	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}

	public void setExpenseCode(Integer expenseCode) {
		this.expenseCode = expenseCode;
	}

	public void setExpenseDate(Date expenseDate) {
		this.expenseDate = expenseDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
