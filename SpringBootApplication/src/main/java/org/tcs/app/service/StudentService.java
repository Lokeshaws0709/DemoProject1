package org.tcs.app.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.tcs.app.model.Student;
import org.tcs.app.request.StudentListReq;
import org.tcs.app.request.StudentRequest;

@Service
public class StudentService {

	@PersistenceContext(unitName = "mySqlDb")
	@Qualifier("mySqlEntityManagerFactory")
	private EntityManager entityManager;

	public static String insertStudentQry = "insert into student(id,name,branch,city,phno) values(?,?,?,?,?)";

	@Transactional(value = "transactionManager", readOnly = false, isolation = Isolation.SERIALIZABLE)
	public String insertStdData(Student std) {
		Query qry = entityManager.createNativeQuery(insertStudentQry);
		qry.setParameter(1, std.getId());
		qry.setParameter(2, std.getName());
		qry.setParameter(3, std.getBranch());
		qry.setParameter(4, std.getCity());
		qry.setParameter(5, std.getPhno());
		int status = qry.executeUpdate();
		
		if (status == 1) {
			return std.getId() + "inserted Student data Successfully ";
		} else {
			return std.getId() + "Not inserted Student Data";
		}

	}

	public static String selectstdById = "select * from student where id= ?";

	public Student getStudentData(Integer stdid) {

		Query qry = entityManager.createNativeQuery(selectstdById, Student.class);
		qry.setParameter(1, stdid);
		Student std = (Student) qry.getSingleResult();
		System.out.println(std);
		return std;
	}

	public static String updateStdRowData = "update student set name = ?, branch = ?, city = ?, phno = ? where id = ?";

	@Transactional(value = "transactionManager", readOnly = false, isolation = Isolation.SERIALIZABLE)
	public String updateStdData(Student std) {

		Query qry = entityManager.createNativeQuery(updateStdRowData);
		qry.setParameter(1, std.getName());
		qry.setParameter(2, std.getBranch());
		qry.setParameter(3, std.getCity());
		qry.setParameter(4, std.getPhno());
		qry.setParameter(5, std.getId());
		int status = qry.executeUpdate();
		if (status == 1) {
			return std.getId() + "update Student data Successfully ";
		} else {
			return std.getId() + "Not update Student Data";
		}

	}

	public static String retriveStdData = "select * from student";

	@Transactional(value = "transactionManager", readOnly = false, isolation = Isolation.SERIALIZABLE)
	public List<Student> retriveData() {
		Query qry = entityManager.createNativeQuery(retriveStdData, Student.class);
		@SuppressWarnings("unchecked")
		List<Student> std = (List<Student>) qry.getResultList();

		System.out.println(std);
		return std;

	}

	public static String updateStdQry = "update student set ";
	public static String updateStdQry_name = " name =  ";
	public static String updateStdQry_branch = ", branch =  ";
	public static String updateStdQry_city = " , city =  ";
	public static String updateStdQry_phno = " ,  phno = ";
	public static String updateStdQry_where = " where id = ";

	@Transactional(value = "transactionManager", readOnly = false, isolation = Isolation.SERIALIZABLE)
	public String updateStdDynamicData(Student std) {
		StringBuffer nwQry = new StringBuffer(updateStdQry);

		// int count=1;
		if (std.getName() != null) {
			nwQry.append(updateStdQry_name);
			nwQry.append("'" + std.getName() + "'");
			// count++;
			// qry.setParameter(count++, std.getName());
		}
		if (std.getBranch() != null) {
			nwQry.append(updateStdQry_branch);
			nwQry.append("'" + std.getBranch() + "'");
			// qry.setParameter(count++, std.getBranch());
		}
		if (std.getCity() != null) {
			nwQry.append(updateStdQry_city);
			nwQry.append("'" + std.getCity() + "'");
			// count++;
			// qry.setParameter(count++, std.getCity());
		}
		if (std.getPhno() != 0) {
			nwQry.append(updateStdQry_phno);
			nwQry.append(std.getPhno());
			// count++;
			// qry.setParameter(count++, std.getPhno());
		}
		nwQry.append(updateStdQry_where);
		nwQry.append(std.getId());
		System.out.println(">>>>>>>>>>>>>>>>> " + nwQry);
		Query qry = entityManager.createNativeQuery(nwQry.toString());
		int status = qry.executeUpdate();
		if (status == 1) {
			return std.getId() + "update Student data Successfully ";
		} else {
			return std.getId() + "Not update Student Data";
		}

	}

	public static String insertStudentListReqQry = "insert into student(id,name,branch,city,phno) values(?,?,?,?,?)";

	@Transactional(value = "transactionManager", readOnly = false, isolation = Isolation.SERIALIZABLE)
	public List<String> insertStdListData(List<Student> studentList) {

		List<String> statusList = new ArrayList<>();

		for (Student studentRequest : studentList) {
			try {
				Query qry = entityManager.createNativeQuery(insertStudentListReqQry);
				qry.setParameter(1, studentRequest.getId());
				qry.setParameter(2, studentRequest.getName());
				qry.setParameter(3, studentRequest.getBranch());
				qry.setParameter(4, studentRequest.getCity());
				qry.setParameter(5, studentRequest.getPhno());
				int status1 = qry.executeUpdate();
				// statusList.add(status1);
				if (status1 == 1) {
					statusList.add(Integer.toString(studentRequest.getId()) + " - Record is inserted");
				} else {
					statusList.add(Integer.toString(studentRequest.getId()) + " - Record is not inserted");
				}

			} catch (Exception e) {
				statusList.add(Integer.toString(studentRequest.getId()) + " - Record is not inserted");
			}
		}

		return statusList;
	}
}
