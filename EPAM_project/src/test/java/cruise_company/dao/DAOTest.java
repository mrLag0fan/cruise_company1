package cruise_company.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cruise_company.dao.linar.LinerController;
import cruise_company.dao.linar.route.RouteController;
import cruise_company.dao.linar.route.port.PortController;
import cruise_company.dao.user.UserController;
import cruise_company.dao.user.UserRoleController;
import cruise_company.dao.user.receipt.ReceiptController;
import cruise_company.entity.linar.Liner;
import cruise_company.entity.linar.route.Route;
import cruise_company.entity.linar.route.port.Port;
import cruise_company.entity.user.User;
import cruise_company.entity.user.UserRole;
import cruise_company.entity.user.receipt.Receipt;

public class DAOTest {
	
	private static Connection con;
	
	@BeforeEach
	void setUp() throws SQLException, FileNotFoundException {
		con = DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/cruise_company","root","159357");  
		ScriptRunner sr = new ScriptRunner(con);
		Reader reader = new BufferedReader(new FileReader("src/sql/db-create.sql"));
		sr.runScript(reader);
		System.out.println("Done");
	}
	
	
	
	@Test
	void userDAOCreateSTest() {
		UserController uc = new UserController();
		try {
			User test = new User("test1", "test1"); 
			assertEquals(2, uc.create(test));
		} catch (DAOException e) {
			fail();
		}
	} 
	
	@Test
	void userDAOCreateFTest() {
		UserController uc = new UserController();
		try {
			User test = new User("test1", "test1"); 
			test.setUserRoleId(-5);
			uc.create(test);
		} catch (DAOException e) {
			return;
		}
		fail();
	} 
	
	@Test
	void userDAOGetByIdSTest() {
		UserController uc = new UserController();
		try {
			String exp =  "User [id=" + 2 + ", email=" + "test1" + ", userRoleId=" + 2 + "]"; 
			User test = new User("test1", "test1"); 
			uc.create(test);
			assertEquals(exp, uc.getEntityByID(2).toString());
		} catch (DAOException e) {
			fail();
		}
	}
	
	@Test
	void userDAOGetAllSTest() {
		UserController uc = new UserController();
		try {
			String exp =  "[User [id=1, email=admin@mail.com, userRoleId=1], "
					+ "User [id=2, email=test1, userRoleId=2], "
					+ "User [id=3, email=test2, userRoleId=2]]"; 
			User test1 = new User("test1", "test1"); 
			User test2 = new User("test2", "test2"); 
			uc.create(test1);
			uc.create(test2);
			assertEquals(exp, uc.getAll().toString());
		} catch (DAOException e) {
			fail();
		}
	}
	
	@Test
	void userDAOPromoteSTest() {
		UserController uc = new UserController();
		try {
			User test1 = new User("test1", "test1");
			uc.create(test1);
			test1.setUserRoleId(1);
			uc.promote(test1);
			assertEquals(1, uc.getEntityByID(2).getUserRoleId());
		} catch (DAOException e) {
			fail();
		}
	}
	

	@Test
	void userDAOPromouteFTest() {
		UserController uc = new UserController();
		try {
			uc.promote(null);
		} catch (DAOException e) {
			return;
		}
		fail();
	} 
	
	@Test
	void userDAOUpdateSTest() {
		UserController uc = new UserController();
		try {
			User test1 = new User("test1", "test1");
			uc.create(test1);
			test1.setBalance(50);
			uc.update(test1);
			assertEquals(test1.getBalance(), uc.getEntityByID(2).getBalance());
		} catch (DAOException e) {
			fail();
		}
	}
	
	@Test
	void userDAOUpdateFTest() {
		UserController uc = new UserController();
		try {
			uc.update(null);
		} catch (DAOException e) {
			return;
		}
		fail();
	}
	
	@Test
	void userDAOGetForLoginSTest() {
		UserController uc = new UserController();
		try {
			User test1 = new User("admin@mail.com", "admin");
			String exp = "User [id=1, email=admin@mail.com, userRoleId=1]";
			assertEquals(exp, uc.getUserForLogin(test1.getEmail(), test1.getPassword()).toString());
		} catch (DAOException e) {
			fail();
		}
	}
 
	@Test
	void userDAODeleteSTest() {
		UserController uc = new UserController();
		try {
			User test1 = new User("test1", "test1"); 
			User test2 = new User("test2", "test2"); 
			uc.create(test1);
			uc.create(test2);
			uc.delete(2);
			assertEquals(2, uc.getAll().size());
		} catch (DAOException e) {
			fail();
		}
	}
	
	@Test
	void userDAOAllWithLimitSTest() {
		UserController uc = new UserController();
		try {
			User test1 = new User("test1", "test1"); 
			User test2 = new User("test2", "test2"); 
			User test3 = new User("test3", "test3"); 
			User test4 = new User("test4", "test4"); 
			User test5 = new User("test5", "test5"); 
			User test6 = new User("test6", "test6"); 
			uc.create(test1);
			uc.create(test2);
			uc.create(test3);
			uc.create(test4);
			uc.create(test5);
			uc.create(test6);
			String exp = "[User [id=1, email=admin@mail.com, userRoleId=1], "
					+ "User [id=2, email=test1, userRoleId=2], "
					+ "User [id=3, email=test2, userRoleId=2]]";
			assertEquals(exp, uc.getAllWithLimit(1, 3).toString());
		} catch (DAOException e) {
			fail();
		}
	}
	
	
	@Test
	void DAOCountOfRowsSTest() {
		UserController uc = new UserController();
		try {
			User test1 = new User("test1", "test1"); 
			User test2 = new User("test2", "test2"); 
			User test3 = new User("test3", "test3"); 
			User test4 = new User("test4", "test4"); 
			User test5 = new User("test5", "test5"); 
			User test6 = new User("test6", "test6"); 
			uc.create(test1);
			uc.create(test2);
			uc.create(test3);
			uc.create(test4);
			uc.create(test5);
			uc.create(test6);
			assertEquals(7, uc.getNumberOfRows("SELECT COUNT(id) AS total FROM user"));
		} catch (DAOException e) {
			fail();
		}
	}
	
	@Test
	void portDAOCreateSTest() {
		try {
			Port port1 = new Port("port1", 1);
			PortController pc = new PortController();
			assertEquals(1, pc.create(port1));
		} catch (DAOException e) {
			fail();
		}
	}
	
	@Test
	void portDAOCreateFTest() {
		try {
			Port port1 = null;
			PortController pc = new PortController();
			pc.create(port1);
		} catch (DAOException e) {
			return;
		}
		fail();
	}
	
	
	@Test
	void linerDAOCreateSTest() {
		try {
			Liner liner = new Liner(1, 1, LocalDate.now(), LocalDate.now(), 1, 1.0, 1, 2);
			Port port1 = new Port("port1", 1);
			Port port2 = new Port("port2", 1);
			LinerController lc = new LinerController();
			PortController pc = new PortController();
			pc.create(port1);
			pc.create(port2);
			assertEquals(1, lc.create(liner));
		} catch (DAOException e) {
			fail();
		}
	}
	
	@Test
	void linerDAOCreateFTest() {
		LinerController lc = new LinerController();
		try {
			Liner liner = null;
			lc.create(liner);
		} catch (DAOException e) {
			return;
		}
		fail();
	}
	
	@Test
	void linerDAOGetByIdTest() {
		try {
			Liner liner = new Liner(1, 1, LocalDate.now(), LocalDate.now(), 1, 1.0, 1, 2);
			Port port1 = new Port("port1", 1);
			Port port2 = new Port("port2", 1);
			LinerController lc = new LinerController();
			PortController pc = new PortController();
			pc.create(port1);
			pc.create(port2);
			lc.create(liner);
			String exp = "Liner [id=1, passengerCapacity=1, visitedPorts=1, dateStart=" + LocalDate.now() +", dateEnd="+ LocalDate.now() +", durationInDays=1, price=1.0, start=1, end=2]";
			assertEquals(exp, lc.getEntityByID(1).toString());
		} catch (DAOException e) {
			fail();
		}
	}
	
	@Test
	void linerDAOGetAllTest() {
		try {
			Liner liner1 = new Liner(1, 1, LocalDate.now(), LocalDate.now(), 1, 1.0, 1, 2);
			Liner liner2 = new Liner(1, 1, LocalDate.now(), LocalDate.now(), 1, 1.0, 1, 2);
			Port port1 = new Port("port1", 1);
			Port port2 = new Port("port2", 1);
			LinerController lc = new LinerController();
			PortController pc = new PortController();
			pc.create(port1);
			pc.create(port2);
			lc.create(liner1);
			lc.create(liner2);
			String exp = "[Liner [id=1, passengerCapacity=1, visitedPorts=1, dateStart="+LocalDate.now()+", dateEnd="+LocalDate.now()+", durationInDays=1, price=1.0, start=1, end=2],"
					+ " Liner [id=2, passengerCapacity=1, visitedPorts=1, dateStart="+LocalDate.now()+", dateEnd="+LocalDate.now()+", durationInDays=1, price=1.0, start=1, end=2]]";
			assertEquals(exp, lc.getAll().toString());
		} catch (DAOException e) {
			fail();
		}
	}
	
	@Test
	void linerDAODeleteTest() {
		try {
			Liner liner1 = new Liner(1, 1, LocalDate.now(), LocalDate.now(), 1, 1.0, 1, 2);
			Liner liner2 = new Liner(1, 1, LocalDate.now(), LocalDate.now(), 1, 1.0, 1, 2);
			Port port1 = new Port("port1", 1);
			Port port2 = new Port("port2", 1);
			LinerController lc = new LinerController();
			PortController pc = new PortController();
			pc.create(port1);
			pc.create(port2);
			lc.create(liner1);
			lc.create(liner2);
			assertTrue(lc.delete(1));
		} catch (DAOException e) {
			fail();
		}
	}
	
	@Test
	void linerDAOGetAllWithLimitTest() {
		try {
			Liner liner1 = new Liner(1, 1, LocalDate.now(), LocalDate.now(), 1, 1.0, 1, 2);
			Port port1 = new Port("port1", 1);
			Port port2 = new Port("port2", 1);
			LinerController lc = new LinerController();
			PortController pc = new PortController();
			pc.create(port1);
			pc.create(port2);
			lc.create(liner1);
			lc.create(liner1);
			lc.create(liner1);
			lc.create(liner1);
			String exp = "[Liner [id=1, passengerCapacity=1, visitedPorts=1, dateStart="+LocalDate.now()+", dateEnd="+LocalDate.now()+", durationInDays=1, price=1.0, start=1, end=2],"
					+ " Liner [id=2, passengerCapacity=1, visitedPorts=1, dateStart="+LocalDate.now()+", dateEnd="+LocalDate.now()+", durationInDays=1, price=1.0, start=1, end=2]]";
			assertEquals(exp, lc.getAllWithLimit(1, 2).toString());
		} catch (DAOException e) {
			fail();
		}
	}
	
	
	@Test
	void routeDAOCreateSTest() {
		try {
			Port port1 = new Port("port1", 1);
			Route route = new Route(1, 2);
			PortController pc = new PortController();
			RouteController rc = new RouteController();
			pc.create(port1);
			pc.create(port1);
			assertEquals(1, rc.create(route));
		} catch (DAOException e) { 
			fail();
		}
	}
	
	@Test
	void routeDAOCreateFTest() {
		try {
			RouteController rc = new RouteController();
			rc.create(null);
		} catch (DAOException e) {
			return;
		}
		fail();
	}
	
	@Test
	void linerDAOSetRouteSTest() {
		try {
			Liner liner1 = new Liner(1, 1, LocalDate.now(), LocalDate.now(), 1, 1.0, 1, 2);
			Port port1 = new Port("port1", 1);
			Port port2 = new Port("port2", 1);
			Route route = new Route(1, 2);
			List<Route> routes = new ArrayList<Route>();
			LinerController lc = new LinerController();
			PortController pc = new PortController();
			RouteController rc = new RouteController();
			pc.create(port1);
			pc.create(port2);
			lc.create(liner1);
			rc.create(route);
			routes.add(route);
			assertTrue(lc.setRouteToLiner(liner1, routes));
		} catch (DAOException e) {
			fail();
		}
	}
	
	@Test
	void linerDAOSetRouteFTest() {
		try {
			LinerController lc = new LinerController();
			lc.setRouteToLiner(null, null);
		} catch (DAOException e) {
			return;
		}
		fail();
	}
	
	@Test
	void linerDAOGetRouteSTest() {
		try {
			Liner liner1 = new Liner(1, 1, LocalDate.now(), LocalDate.now(), 1, 1.0, 1, 2);
			Port port1 = new Port("port1", 1);
			Port port2 = new Port("port2", 1);
			Route route = new Route(1, 2);
			List<Route> routes = new ArrayList<Route>();
			LinerController lc = new LinerController();
			PortController pc = new PortController();
			RouteController rc = new RouteController();
			pc.create(port1);
			pc.create(port2);
			lc.create(liner1);
			rc.create(route);
			routes.add(route);
			lc.setRouteToLiner(liner1, routes);
			String exp = "[Route [id=1, from=1, to=2]]";
			assertEquals(exp, lc.getLinerRoute(liner1).toString());
		} catch (DAOException e) {
			fail();
		}
	}
	
	@Test
	void linerDAOGetRouteFest() {
		try {
			Liner liner1 = null;
			LinerController lc = new LinerController();
			lc.getLinerRoute(liner1);
		} catch (DAOException e) {
			return;
		}
		fail();
	}
	
	@Test
	void linerDAONumberOfRowsLinerRouteTest() {
		try {
			Liner liner1 = new Liner(1, 1, LocalDate.now(), LocalDate.now(), 1, 1.0, 1, 2);
			Port port1 = new Port("port1", 1);
			Port port2 = new Port("port2", 1);
			Route route = new Route(1, 2);
			List<Route> routes = new ArrayList<Route>();
			LinerController lc = new LinerController();
			PortController pc = new PortController();
			RouteController rc = new RouteController();
			pc.create(port1);
			pc.create(port2);
			lc.create(liner1);
			rc.create(route);
			routes.add(route);
			lc.setRouteToLiner(liner1, routes);
			assertEquals(1, lc.getNumberOfRowsForLinerRoute(1));
		} catch (DAOException e) {
			fail();
		}
	}
	@Test
	void linerDAOGetLinerRouteWithLimitSTest() {
		try {
			Liner liner1 = new Liner(1, 1, LocalDate.now(), LocalDate.now(), 1, 1.0, 1, 2);
			Port port1 = new Port("port1", 1);
			Port port2 = new Port("port2", 1);
			List<Route> routes = new ArrayList<Route>();
			LinerController lc = new LinerController();
			PortController pc = new PortController();
			RouteController rc = new RouteController();
			pc.create(port1);
			pc.create(port2);
			lc.create(liner1);
			for(int i = 0; i<4;i++) {
				Route route = new Route(1, 2);
				rc.create(route);
				routes.add(route);
			}
			lc.setRouteToLiner(liner1, routes);
			assertEquals(2, lc.getLinerRouteWithLimit(liner1, 1, 2).size());
		} catch (DAOException e) {
			fail();
		}
	}
	
	@Test
	void linerDAOGetLinerRouteWithLimitFTest() {
		try {
			LinerController lc = new LinerController();
			lc.getLinerRouteWithLimit(null, 1, 2);
		} catch (DAOException e) {
			return;
		}
		fail();
	}
	
	@Test
	void portDAOUpdateSTest() {
		try {
			PortController pc = new PortController();
			Port port = new Port("test",1);
			pc.create(port);
			port.setPortStatusId(2);
			pc.update(port);
			assertEquals(port.toString(), pc.getEntityByID(1).toString());
		} catch (DAOException e) {
			fail();
		}
		return;
	}
	
	@Test
	void portDAOUpdateFTest() {
		try {
			PortController pc = new PortController();
			pc.update(null);
		} catch (DAOException e) {
			return;
		}
		fail();
	}
	
	@Test
	void portDAODeleteTest() {
		try {
			PortController pc = new PortController();
			Port port = new Port("test",1);
			pc.create(port);
			pc.create(port);
			pc.delete(1);
			assertEquals(1, pc.getAll().size());
		} catch (DAOException e) {
			fail();
		}
		return;
	}
	
	@Test
	void portDAOGetAllWithLimitTest() {
		try {
			PortController pc = new PortController();
			Port port = new Port("test",1);
			pc.create(port);
			pc.create(port);
			pc.create(port);
			pc.create(port);
			pc.create(port);
			assertEquals(5, pc.getAll().size());
			assertEquals(2, pc.getAllWithLimit(1, 2).size());
		} catch (DAOException e) {
			fail();
		}
		return;
	}
	
	@Test
	void routeDAODeleteTest() {
		try {
			RouteController rc = new RouteController();
			PortController pc = new PortController();
			Route route1 = new Route(1,2);
			Route route2 = new Route(3,4);
			Port port = new Port("port", 1);
			pc.create(port);
			pc.create(port);
			pc.create(port);
			pc.create(port);
			rc.create(route1);
			rc.create(route2);
			rc.delete(1);
			assertEquals(1, rc.getAll().size());
		} catch (DAOException e) {
			fail();
		}
		return;
	}
	
	@Test
	void routeDAOGetAllWithLimitTest() {
		try {
			PortController pc = new PortController();
			RouteController rc = new RouteController();
			Port port = new Port("test",1);
			pc.create(port);
			pc.create(port);
			pc.create(port);
			rc.create(new Route(1, 2));
			rc.create(new Route(1, 3));
			rc.create(new Route(2, 3));
			assertEquals(3, rc.getAll().size());
			assertEquals(2, rc.getAllWithLimit(1, 2).size());
		} catch (DAOException e) {
			fail();
		}
		return;
	}
	
	@Test
	void receiptDAOUpdateSTest() {
		try {
			ReceiptController rc = new ReceiptController();
			PortController pc = new PortController();
			LinerController lc = new LinerController();
			Port port = new Port("test",1);
			pc.create(port);
			pc.create(port);
			Liner liner = new Liner(1, 1, LocalDate.now(),LocalDate.now(), 1, 1.0, 1, 2);
			lc.create(liner);
			Receipt receipt = new Receipt("doc", 1.0, 1, 1, 1);
			assertEquals(1, rc.create(receipt));
		} catch (DAOException e) {
			e.printStackTrace();
			fail();
		}
		return;
	}
	
}
