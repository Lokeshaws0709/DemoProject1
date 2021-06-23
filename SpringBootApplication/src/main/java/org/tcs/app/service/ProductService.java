package org.tcs.app.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.tcs.app.model.Product;

@Service
public class ProductService {

	@PersistenceContext(unitName = "mySqlDb")
	@Qualifier("mySqlEntityManagerFactory")
	private EntityManager entityManager;

	public static String insertProductData = "insert into product(name,price,colour,country,createdDate,updatedDate) values(?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";

	@Transactional(value = "transactionManager", readOnly = false, isolation = Isolation.SERIALIZABLE)
	public String insertPrdtData(Product prdt) {

		Query qry = entityManager.createNativeQuery(insertProductData);
		//qry.setParameter(1, prdt.getId());
		qry.setParameter(1, prdt.getName());
		qry.setParameter(2, prdt.getPrice());
		qry.setParameter(3, prdt.getColour());
		qry.setParameter(4, prdt.getCountry());
		//qry.setParameter(6, prdt.getUpdatedDate());
		//qry.setParameter(7, prdt.getUpdatedDate());

		int status = qry.executeUpdate();
		
		
		if (status == 1) {
			Query qry2 = entityManager.createNativeQuery("select max(id) from product");
			Object obj=qry2.getSingleResult();
			return obj + " : inserted Student data Successfully ";
		} else {
			return  "Not inserted Student Data";
		}

	}

}
