package org.farm.fms.entity.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.farm.fms.etntity.Expense;
import org.farm.fms.etntity.Expense_;

@Stateless
public class ExpenseEJB extends AbstructHome<Expense, Integer> {

	private static final long serialVersionUID = 1L;

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Integer id) {

	}

	@Override
	public Expense findById(Integer id) {

		return findById(Expense.class, id);
	}

	public List<Expense> findExpenseByFilter(Date from, Date to) {
		List<Expense> searchResult = new ArrayList<Expense>();

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Expense> query = cb.createQuery(Expense.class);
		Root<Expense> root = query.from(Expense.class);
		query.select(root);
		Predicate predicate = getFilterCriteria(cb, root, from, to);
		if (predicate != null)
			query.where(predicate);
		query.orderBy(cb.desc(root.get(Expense_.expenseDate)));

		TypedQuery<Expense> typedQuery = entityManager.createQuery(query);
		searchResult = typedQuery.getResultList();
		return searchResult;
	}

	public Predicate getFilterCriteria(CriteriaBuilder cb, Root<Expense> root, Date from, Date to) {

		Predicate predicate = null;

		List<Predicate> wherePredicates = new ArrayList<Predicate>();

		if (from != null) {
			wherePredicates.add(cb.greaterThanOrEqualTo(root.get(Expense_.expenseDate), from));
		}

		if (to != null) {
			wherePredicates.add(cb.lessThanOrEqualTo(root.get(Expense_.expenseDate), to));
		}
		if (wherePredicates.size() > 0) {
			predicate = cb.and(wherePredicates.toArray(new Predicate[wherePredicates.size()]));
		}

		return predicate;
	}

}
