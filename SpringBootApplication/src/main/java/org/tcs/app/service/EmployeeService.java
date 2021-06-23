package org.tcs.app.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.tcs.app.model.Employe;

@Service
public class EmployeeService {

	@PersistenceContext(unitName = "mySqlDb")
	@Qualifier("mySqlEntityManagerFactory")
	private EntityManager entityManager;

	public static String selectEmpById = "select * from employee where id= ?";
	public static String insertEmployeeQry = "insert into employee(id,name,salary) values(?,?,?)";
	public static String deleteEmpById = "delete from employee where id = ?";

	public Employe getEmployeeData(Integer empid) {

		Query qry = entityManager.createNativeQuery(selectEmpById, Employe.class);
		qry.setParameter(1, empid);
		Employe emp = (Employe) qry.getSingleResult();
		System.out.println(emp);
		return emp;
	}

	@Transactional(value = "transactionManager", readOnly = false, isolation = Isolation.SERIALIZABLE)
	public String insertEmpData(Employe emp) {

		Query qry = entityManager.createNativeQuery(insertEmployeeQry);
		qry.setParameter(1, emp.getId());
		qry.setParameter(2, emp.getName());
		qry.setParameter(3, emp.getSal());
		int i = qry.executeUpdate();
		System.out.println(">>>>>>>>>>> " + i);
		if (i == 1) {
			return emp.getId() + "Record Inserted Succesfully!";
		} else {
			return emp.getId() + "Record NOT Inserted ";
		}
	}

	@Transactional(value = "transactionManager", readOnly = false, isolation = Isolation.SERIALIZABLE)
	public String deleteEmpData(Employe emp) {

		Query qry = entityManager.createNativeQuery(deleteEmpById);
		qry.setParameter(1, emp.getId());
		// qry.setParameter(2, emp.getName());
		// qry.setParameter(3, emp.getSal());
		int i = qry.executeUpdate();
		System.out.println(">>>>>>>>>>> " + i);
		if (i == 1) {
			return emp.getId() + "Record delete Succesfully!";
		} else {
			return emp.getId() + "Record NOT deleted ";
		}

	}

	public static String updateEmpRow = "update employee set name= ?,salary=? where id = ?";

	@Transactional(value = "transactionManager", readOnly = false, isolation = Isolation.SERIALIZABLE)
	public String updateEmpData(Employe emp) {

		Query qry = entityManager.createNativeQuery(updateEmpRow, Employe.class);
		qry.setParameter(1, emp.getName());
		qry.setParameter(2, emp.getSal());
		qry.setParameter(3, emp.getId());
		int status = qry.executeUpdate();
		if (status == 1) {
			return emp.getId() + "Record is successfully updated";

		} else {

			return emp.getId() + "Record is not updated";
		}
	}

	public static String retriveEmpData = "select * from employee";

	@Transactional(value = "transactionManager", readOnly = false, isolation = Isolation.SERIALIZABLE)
	public List<Employe> retriveData() {
		Query qry = entityManager.createNativeQuery(retriveEmpData,Employe.class);
		@SuppressWarnings("unchecked")
		List<Employe> emp = (List<Employe>) qry.getResultList();
		System.out.println(emp);
		return emp;

	}

}
