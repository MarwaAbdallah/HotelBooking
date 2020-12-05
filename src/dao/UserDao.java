package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import db.DBManager;
import model.Hotel;
import model.Role;
import model.Room;
import model.User;

public class UserDao {

	private DataSource datasource;
	public UserDao(DataSource datasource) {
		this.datasource = datasource;
	}
	
	public boolean isUserPresentInDb(String principal) throws Exception {
		boolean isUserPresent = false;
		Connection myCon = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myCon = datasource.getConnection();
			String sql = "SELECT EXISTS(SELECT 1 FROM users "
					+ "WHERE email = ?);";
			myStmt = myCon.prepareStatement(sql);
			myStmt.setString(1, principal);
			myRs = myStmt.executeQuery();
			if(myRs.next()) {
				isUserPresent = myRs.getBoolean("exists");
			}
		} finally {
			DBManager.close(myCon, myStmt, myRs);
		}
		return isUserPresent;
	}
	public User getUserfromPrincipal(String principal) throws Exception {
		// today there is only one or 2 interesting fields in user class, 
		//but there could be more, hence DB connection
		User user = null;
		Connection myCon = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		System.out.println("principal"+principal);
		try {
			myCon = datasource.getConnection();
			String sql = "select u.*, ur.role_name from users u, users_roles ur" + 
					" where u.email= ? " + 
					" and u.email = ur.email";
			myStmt = myCon.prepareStatement(sql);
			myStmt.setString(1, principal);
			myRs = myStmt.executeQuery();
			if(myRs.next()) {
				int id = myRs.getInt("id");
				boolean enabled = myRs.getBoolean("enabled");
				String role = myRs.getString("role_name");
				user = new User(id, principal,enabled,role);
			}else {
				throw new Exception("Could not find User ID :"+principal);
			}
			
		} finally {
			DBManager.close(myCon, myStmt, myRs);
		}
		return user;
	}
	
	public Role getUserRole(String principal) throws Exception {
		Role role = null;
		Connection myCon = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myCon = datasource.getConnection();
			String sql = "select * from users_roles where email = ?";
			myStmt = myCon.prepareStatement(sql);
			myStmt.setString(1, principal);
			myRs = myStmt.executeQuery();
			if(myRs.next()) {
				String roleName = myRs.getString("role_name");
				role = new Role(roleName);
			} else {
				throw new Exception("Could not find User ID :"+principal);
				
			}
			
		} finally {
			DBManager.close(myCon, myStmt, myRs);
		}
		return role;
		
	}
	public List<Role> getAllRoles() throws Exception{
		List<Role> roles = new ArrayList<>();
		Connection myCon = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myCon = datasource.getConnection();
			String sql = "select * from roles";
			myStmt = myCon.prepareStatement(sql);
			myRs = myStmt.executeQuery();
			while (myRs.next()) {
				String roleName = myRs.getString("role_name");
				Role role = new Role(roleName);
				roles.add(role);
			} 
		}catch(Exception  e) {
				throw new Exception("Error collecting all Roles");
		} finally {
			DBManager.close(myCon, myStmt, myRs);
		}
		return roles;
		
	}

	public int getEnabledUsersCount() throws SQLException {
		// TODO Auto-generated method stub
		int count = 0;
		Connection myCon = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myCon = datasource.getConnection();
			String sql = "select count(*) from users where enabled=true";
			myStmt = myCon.createStatement();
			myRs = myStmt.executeQuery(sql);
			if(myRs.next()) {
				count = myRs.getInt("count");
			}
		} finally {
			DBManager.close(myCon, myStmt, myRs);
		}
		return count;
	}

	public List<User> getAllUsers() throws SQLException  {
		// TODO Auto-generated method stub
		List<User> users = new ArrayList<>();
		Connection myCon = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myCon = datasource.getConnection();
			String sql = "select * from users";
			myStmt = myCon.createStatement();
			myRs = myStmt.executeQuery(sql);
			while (myRs.next()) {
				int id = myRs.getInt("id");
				String email = myRs.getString("email");
				boolean enabled = myRs.getBoolean("enabled");
				User user = new User(id, email,enabled);
				users.add(user);
			}
			
		} finally {
			DBManager.close(myCon, myStmt, myRs);
		}
		return users;
	}





	public void removeUser(int userId) throws SQLException {
		// TODO Auto-generated method stub
		Connection myCon = null;
		PreparedStatement myStmt=null;
		
		try {
			// get DB connection
			myCon=datasource.getConnection();
			//create SQL for insert
			String sql = "delete from users where users.id= ?";
			myStmt=myCon.prepareStatement(sql);
			myStmt.setInt(1,userId);

			myStmt.execute();	
		} 
		finally {
			DBManager.close(myCon, myStmt, null);
		}
	}

	public void editUser(User user) throws SQLException {
		// TODO Auto-generated method stub
		Connection myCon1 = null;
		Connection myCon2 = null;
		PreparedStatement myStmt1=null;
		PreparedStatement myStmt2=null;
		
		try {
			myCon1=datasource.getConnection();
			String sql1 = "update users" + 
					" set email = ?," + 
					" enabled= ? " + 
					" where users.id = ?";
			
			String sql2 = "update users_roles" + 
					" set role_name= ?" + 
					" where email= ?";
			
			
			myStmt1=myCon1.prepareStatement(sql1);
			myStmt1.setString(1,user.getEmail());
			myStmt1.setBoolean(2,user.isEnabled());
			myStmt1.setInt(3, user.getId());
			myStmt1.execute();	
			
			myCon2=datasource.getConnection();
			myStmt2=myCon2.prepareStatement(sql2);
			myStmt2.setString(1,user.getRole());
			myStmt2.setString(2,user.getEmail());
			myStmt2.execute();	
		} 
		finally {
				DBManager.close(myCon1, myStmt1, null);	
				DBManager.close(myCon2, myStmt2, null);	
		}
	}

	public void addUser(User user) {
		// TODO Auto-generated method stub
		Connection myCon1 = null;
		Connection myCon2 = null;
		PreparedStatement myStmt1=null;
		PreparedStatement myStmt2=null;
		try {
			myCon1 = datasource.getConnection();
			String sql1 = "insert into users(email,upassword,enabled)" + 
					" values (?,?,?)";
			String sql2="insert into users_roles(email,role_name)" + 
					" values(?,?)";
			myStmt1 = myCon1.prepareStatement(sql1);
			myStmt1.setString(1, user.getEmail());
			myStmt1.setString(2, user.getPassword());
			myStmt1.setBoolean(3, user.isEnabled());
			myStmt1.execute();
			
			myCon2 = datasource.getConnection();
			myStmt2 = myCon2.prepareStatement(sql2)
;			myStmt2.setString(1, user.getEmail());
			myStmt2.setString(2, user.getRole());
			myStmt2.execute();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(myCon1, myStmt1, null);
			DBManager.close(myCon2, myStmt2, null);
		}
	}
	
}
