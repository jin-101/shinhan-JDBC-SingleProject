package JDBCproject.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import JDBCproject.dbutil.util;
import JDBCproject.vo.EmpVO;
import JDBCproject.vo.EquiVO;


public class AssetDAO {
	private Connection conn;
	private Statement st;
	private PreparedStatement pst; // ?지원
	private ResultSet rs;
	private int resultCount; 
	
	CallableStatement cst;
	
	
//장비 관련!!
//1-1-1 대여가능장비 전체조회
	public List<EquiVO> rentPossible() {
		String sql = """
					select EQUIPMENT_ID,
						   equipmentstype_TYPE_ID,
						   equipmentscompany_CO_ID,
						   MODEL,
						   SERIAL_NO,
						   PURCHASE_DATE,
						   PRICE
					from equipments mainEq 
					where mainEq.EQUIPMENT_ID not in (select eq.equipment_id 
														from equipments eq join rental r on(eq.equipment_id = r.equipments_equipment_id) 
														where r.status = '대여')
				""";
		List<EquiVO> list = new ArrayList<>();
		conn = util.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				EquiVO emp = makeEqui(rs);
				list.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.dbDisconnect(rs, st, conn);
		}
		return list;
	}
	
//1-1-2 대여장비 유형별조회
	public List<EquiVO> eqTypeSearch(String typeId) {
		String sql = """
					select EQUIPMENT_ID,
						   equipmentstype_TYPE_ID,
						   equipmentscompany_CO_ID,
						   MODEL,
						   SERIAL_NO,
						   PURCHASE_DATE,
						   PRICE
					from equipments mainEq 
					where mainEq.EQUIPMENT_ID not in (select eq.equipment_id 
														from equipments eq join rental r on(eq.equipment_id = r.equipments_equipment_id) 
														where r.status = '대여')
					and mainEq.equipmentstype_TYPE_ID = ?
				""";
		List<EquiVO> list = new ArrayList<>();
		conn = util.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, typeId);
			rs = pst.executeQuery();
			while(rs.next()) {
				EquiVO emp = makeEqui(rs);
				list.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.dbDisconnect(rs, st, conn);
		}
		return list;
	}
	
//1-1-3 대여장비 모델별조회
	public List<EquiVO> rentModelSearch(String model) {
		String sql = """
					select EQUIPMENT_ID,
						   equipmentstype_TYPE_ID,
						   equipmentscompany_CO_ID,
						   MODEL,
						   SERIAL_NO,
						   PURCHASE_DATE,
						   PRICE
					from equipments mainEq 
					where mainEq.EQUIPMENT_ID not in (select eq.equipment_id from equipments eq join rental r on(eq.equipment_id = r.equipments_equipment_id) 
													  where r.status = '대여')
					and mainEq.MODEL like ?

				""";

		List<EquiVO> list = new ArrayList<>();
		conn = util.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, "%"+model+"%");
			rs = pst.executeQuery();
			while(rs.next()) {
				EquiVO emp = makeEqui(rs);
				list.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.dbDisconnect(rs, st, conn);
		}
		return list;
	}
//1-2-1 사용중인 장비 모두 조회
	public List<EquiVO> empUseEqAllSearch() {
		String sql = """
				select eq.* from rental r join employees emp on (r.employees_EMPLOYEE_ID = emp.EMPLOYEE_ID)
											join equipments eq on (r.equipments_EQUIPMENT_ID = eq.EQUIPMENT_ID)
							where r.STATUS = '대여'
				""";
		List<EquiVO> list = new ArrayList<>();
		conn = util.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			System.out.println(rs);
			while(rs.next()) {
				EquiVO emp = makeEqui(rs);
				System.out.println(emp);
				list.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.dbDisconnect(rs, st, conn);
		}
		return list;
	}
//1-2-2 직원별 사용중인 장비 조회
	public List<EquiVO> userEqSearch(int userId) {
		String sql = """
				select eq.* from rental r join employees emp on (r.employees_EMPLOYEE_ID = emp.EMPLOYEE_ID)
											join equipments eq on (r.equipments_EQUIPMENT_ID = eq.EQUIPMENT_ID)
							where r.STATUS = '대여'
							and r.employees_EMPLOYEE_ID = ?
				""";
		List<EquiVO> list = new ArrayList<>();
		conn = util.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, userId);
			rs = pst.executeQuery();
			while(rs.next()) {
				EquiVO emp = makeEqui(rs);
				list.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.dbDisconnect(rs, st, conn);
		}
		return list;
	}
	
//1-2-3 장비ID별 현재 사용자 조회
	public List<EmpVO> equiIdSearch(int eqId) {
		String sql = """
				select emp.* from rental r join employees emp on (r.employees_EMPLOYEE_ID = emp.EMPLOYEE_ID)
											join equipments eq on (r.equipments_EQUIPMENT_ID = eq.EQUIPMENT_ID)
							where r.STATUS = '대여'
				            and r.equipments_EQUIPMENT_ID = ?
				""";
		List<EmpVO> list = new ArrayList<>();
		conn = util.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, eqId);
			rs = pst.executeQuery();
			while(rs.next()) {
				EmpVO emp = makeEmp(rs);
				list.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.dbDisconnect(rs, st, conn);
		}
		return list;
	}
	
	
//1-3-1 장비대여
	public int eqRental(int equipmentId, int employeeId) {
		try {
			String preQueryStatement  = """
					select equipments_EQUIPMENT_ID FROM rental WHERE equipments_EQUIPMENT_ID = ?
				""";
			conn = util.getConnection();
			pst = conn.prepareStatement(preQueryStatement);
			pst.setInt(1, equipmentId);
			rs = pst.executeQuery(); //DML문장 실행한다.
			if (!rs.next()) {
				 String sql = """
							insert into rental(equipments_EQUIPMENT_ID, employees_EMPLOYEE_ID, START_DATE, STATUS) 
							values (?, ?, DATE_FORMAT(now(), '%Y-%m-%d'),'대여')
							""";
						pst = conn.prepareStatement(sql);
						pst.setInt(1, equipmentId);
						pst.setInt(2, employeeId);
						resultCount = pst.executeUpdate(); //DML문장 실행한다. 
			            return resultCount;
			 }else {
				 resultCount = -1;
				 System.out.println("! 이미 대여중인 장비입니다.");
				 return resultCount;
			 }
		} catch(SQLException ex) {
			resultCount = -1;
			ex.printStackTrace();
		} finally {
			util.dbDisconnect(rs, pst, conn);
		}
		return resultCount;
	}
	
//1-3-2 장비반납
	public int eqRetrun(int eqId) {
		String sql = """
				update rental
				set end_date = DATE_FORMAT(now(), '%Y-%m-%d'), status = '반납'
				where equipments_EQUIPMENT_ID = ?
				""";
		conn = util.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, eqId);
			resultCount = pst.executeUpdate(); //DML문장 실행한다.
		} catch (SQLException e) {
			resultCount = -1;
			e.printStackTrace();
		} finally {
			util.dbDisconnect(null, pst, conn);
		}
		return resultCount;
	}
	//rent테이블에 정보 삭제
	public int rentInfoDelete(int eqId) {
		String sql = """
				delete from rental
				where equipments_EQUIPMENT_ID = ?
				""";
		conn = util.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, eqId);
			resultCount = pst.executeUpdate(); //DML문장 실행한다.
		} catch (SQLException e) {
			resultCount = -1;
			e.printStackTrace();
		} finally {
			util.dbDisconnect(null, pst, conn);
		}
		return resultCount;
	}
	
//1-4-1 신규장비등록(insert)
	public int eqInsert(EquiVO eq) {
		String sql = """
				insert into equipments (equipmentstype_TYPE_ID, equipmentscompany_CO_ID, MODEL, SERIAL_NO, PURCHASE_DATE, PRICE)
				values (?, ?, ?, ?, ?, ?)
				""";
		conn = util.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, eq.getEquipmentstype_TYPE_ID());
			pst.setString(2, eq.getEquipmentscompany_CO_ID());
			pst.setString(3, eq.getMODEL());
			pst.setString(4, eq.getSERIAL_NO());
			pst.setDate(5, eq.getPURCHASE_DATE());
			pst.setInt(6, eq.getPRICE());
			resultCount = pst.executeUpdate(); //DML문장 실행한다. 
		} catch (SQLException e) {
			resultCount = -1;
			e.printStackTrace();
		} finally {
			util.dbDisconnect(null, pst, conn);
		}
		return resultCount;
	}
	
//1-4-2 장비정보수정(Update)
	public int eqUpdate(EquiVO eq) {
		String sql = """
				update equipments
				set MODEL = ?, SERIAL_NO = ?, PURCHASE_DATE = ?, PRICE= ?
				where equipment_ID = ?
				""";
		conn = util.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, eq.getMODEL());
			pst.setString(2, eq.getSERIAL_NO());
			pst.setDate(3, eq.getPURCHASE_DATE());
			pst.setInt(4,eq.getPRICE());
			pst.setInt(5,eq.getEquipment_id());
			resultCount = pst.executeUpdate(); //DML문장 실행한다.
		} catch (SQLException e) {
			resultCount = -1;
			e.printStackTrace();
		} finally {
			util.dbDisconnect(null, pst, conn);
		}
		return resultCount;
	}
//1-4-2-sub 장비번호로 직원정보조회
	public EquiVO selectByEqId(int eqId) {
		EquiVO eq = null;
		String sql = "select * from equipments where equipment_id = " + eqId;
		conn = util.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				eq = makeEqui(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.dbDisconnect(rs, st, conn);
		}
		return eq;
	}
//1-4-3 장비삭제
	public int eqDelete(int eqId) {
		String sql = """
				delete from equipments
				where equipment_id = ?
				""";
		conn = util.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, eqId);
			resultCount = pst.executeUpdate(); //DML문장 실행한다.
		} catch (SQLException e) {
			resultCount = -1;
			//e.printStackTrace();
			System.out.println("보유중인 장비는 삭제될 수 없습니다.");
		} finally {
			util.dbDisconnect(null, pst, conn);
		}
		return resultCount;
	}
	
	
//직원 관련!!!
//2-1-1 전체 직원조회
	public List<EmpVO> viewEmp() {
		String sql = """
					select *
					from employees;
				""";
		List<EmpVO> emplist = new ArrayList<>();
		conn = util.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				EmpVO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.dbDisconnect(rs, st, conn);
		}
		return emplist;
	}
	
//2-1-2 직원ID로 직원정보조회
	public EmpVO selectById(int empid) {
		EmpVO emp = null;
		String sql = "select * from employees where employee_id = " + empid;
		conn = util.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				emp = makeEmp(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.dbDisconnect(rs, st, conn);
		}
		return emp;
	}
	
//2-2-1 신규직원등록(insert)
	public int empInsert(EmpVO emp) {
		String sql = """
				insert into employees (FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, HIRE_DATE, Jobs_JOB_ID, SALARY, subpart_PART_NO, Position_POSITION_ID)
				values(?, ?, ?, ?, ?, ?, ?, ?, ?)
				""";
		conn = util.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, emp.getFirst_name());
			pst.setString(2, emp.getLast_name());
			pst.setString(3, emp.getEmail());
			pst.setString(4, emp.getPhone_number());
			pst.setDate(5, emp.getHire_date());
			pst.setString(6, emp.getJobs_JOB_ID());
			pst.setInt(7, emp.getSalary());
			pst.setInt(8, emp.getSubpart_PART_NO());
			pst.setInt(9,emp.getPosition_POSITION_ID());
			resultCount = pst.executeUpdate(); //DML문장 실행한다. 
		} catch (SQLException e) {
			resultCount = -1;
			e.printStackTrace();
		} finally {
			util.dbDisconnect(null, pst, conn);
		}
		return resultCount;
	}
	
//2-2-2 직원정보수정(Update)
	public int empUpdate(EmpVO emp) {
		String sql = """
				update employees
				set EMAIL = ?, hire_date = ?, Jobs_JOB_ID = ?, SALARY= ?, Subpart_PART_NO = ?,  Position_POSITION_ID = ?
				where EMPLOYEE_ID = ?
				""";
		conn = util.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, emp.getEmail());
			pst.setDate(2, emp.getHire_date());
			pst.setString(3, emp.getJobs_JOB_ID());
			pst.setInt(4,emp.getSalary());
			pst.setInt(5, emp.getSubpart_PART_NO());
			pst.setInt(6, emp.getPosition_POSITION_ID());
			pst.setInt(7,emp.getEmployee_id());
			resultCount = pst.executeUpdate(); //DML문장 실행한다. 
		} catch (SQLException e) {
			resultCount = -1;
			e.printStackTrace();
		} finally {
			util.dbDisconnect(null, pst, conn);
		}
		return resultCount;
	}
	
//2-2-3 1건의 직원을 삭제하기(delete)
	public int empDelete(int empId) {
		String sql = """
				delete from employees
				where employee_id = ?
				""";
		conn = util.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, empId);
			resultCount = pst.executeUpdate(); //DML문장 실행한다.
		} catch (SQLException e) {
			resultCount = -1;
			//e.printStackTrace();
			System.out.println("장비를 보유하고 있는 직원은 삭제될 수 없습니다.");
		} finally {
			util.dbDisconnect(null, pst, conn);
		}
		return resultCount;
	}
	
// 장비 필드 생성
	private EquiVO makeEqui(ResultSet rs) throws SQLException {
		EquiVO equi = new EquiVO();
		equi.setEquipment_id(rs.getInt("equipment_id"));
		equi.setEquipmentstype_TYPE_ID(rs.getString("equipmentstype_TYPE_ID"));
		equi.setEquipmentscompany_CO_ID(rs.getString("equipmentscompany_CO_ID"));
		equi.setMODEL(rs.getString("MODEL"));
		equi.setSERIAL_NO(rs.getString("SERIAL_NO"));
		equi.setPURCHASE_DATE(rs.getDate("PURCHASE_DATE"));
		equi.setPRICE(rs.getInt("PRICE"));
		return equi;
	}
	
// 직원 필드 생성
	private EmpVO makeEmp(ResultSet rs) throws SQLException {
		EmpVO emp = new EmpVO();
		emp.setEmployee_id(rs.getInt("Employee_id"));
		emp.setFirst_name(rs.getString("First_name"));
		emp.setLast_name(rs.getString("Last_name"));
		emp.setEmail(rs.getString("Email"));
		emp.setPhone_number(rs.getString("Phone_number"));
		emp.setHire_date(rs.getDate("Hire_date"));
		emp.setJobs_JOB_ID(rs.getString("jobs_JOB_ID"));
		emp.setSalary(rs.getInt("Salary"));
		emp.setSubpart_PART_NO(rs.getInt("subpart_PART_NO"));
		emp.setPosition_POSITION_ID(rs.getInt("position_POSITION_ID"));
		return emp;
	}

	

}