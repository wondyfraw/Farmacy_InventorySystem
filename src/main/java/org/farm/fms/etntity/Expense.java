package org.farm.fms.etntity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

//@NamedQuery(name = "expenseForTheLastFiveDays", query = "select sum(amount),expenseDate from Expense where expenseDate > current_date - interval 20 days group by expenseDate ")

@Entity
@Table(name = "expense", schema = "myfms")
@NamedQueries({
		@NamedQuery(name = "totalExpenseForEachDay", query = "select sum(amount),expenseDate from Expense group by expenseDate") })
public class Expense implements Serializable {

	private static final long serialVersionUID = -8686228350401399741L;

	private Integer expenseCode;
	private Date expenseDate;
	private String description;
	private Double amount;
	private String userName;
	private String invoiceNumber;
	private String fileName;
	private String mimType;
	private byte[] dataFile;

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

	@Column(name = "invoive_number")
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	@Column(name = "file_name")
	public String getFileName() {
		return fileName;
	}

	@Column(name = "mim_type")
	public String getMimType() {
		return mimType;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "data_file")
	public byte[] getDataFile() {
		return dataFile;
	}

	public void setMimType(String mimType) {
		this.mimType = mimType;
	}

	public void setDataFile(byte[] dataFile) {
		this.dataFile = dataFile;
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

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

}
