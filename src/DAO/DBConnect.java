package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class DBConnect {
	
	static String strClassName = DBConnect.class.getName();  
    static Logger logger = Logger.getLogger(strClassName); 

	public Connection Connect2MySQL() throws Exception{
		String driver="com.mysql.jdbc.Driver";
		String url="jdbc:mysql://127.0.0.1:3306/bishe?useUnicode=true&characterEncoding=utf-8&useSSL=false";
		Class.forName(driver);
		Connection conn=DriverManager.getConnection(url, "root", "root");
		return conn;
	}
	
	public boolean chkUser(String username, String userpwd) throws Exception{		
		String chkStr = "Select * from `user` where username = '" + username + "' and userpwd = '" + userpwd + "'";
		Connection conn = this.Connect2MySQL();		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(chkStr);
		User u = new User();
		u.setUsername("null");
		u.setUserpwd("null");
		if(rs.next()){
			u.setUsername(rs.getString("username"));
			u.setUserpwd(rs.getString("userpwd"));
		}
		if(u.getUsername().equals(username) && u.getUserpwd().equals(userpwd)){
			logger.info("user <" + username + "> confirmed");
			rs.close();
			stmt.close();
			conn.close();
			return true;
		}
		return false;			
	}
	
	public boolean isUserExist(String username) throws Exception{		
		String Str = "Select * from `user` where username = '" + username + "'";
		Connection conn = this.Connect2MySQL();		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(Str);		
		if(rs.next()){
			return true;
		}
		return false;			
	}

	public User saveUser(String username) throws Exception {		
		String str = "Select * from `user` where username = '" + username + "'";		
		Connection conn = this.Connect2MySQL();		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(str);			
		User u = new User();		
		if(rs.next()){		
		u.setUsername(rs.getString("username"));
		u.setUsermail(rs.getString("usermail"));
		u.setUserpwd(rs.getString("userpwd"));
		u.setUserphone(rs.getString("userphone"));
		u.setUseradd(rs.getString("useradd"));
		u.setUserauth(rs.getString("userauth"));	
		}
		rs.close();
		stmt.close();
		conn.close();
		return u;	
	}
	
	public void newUser(String username, String userpwd, String usermail, String userphone, String useradd, String userauth) throws Exception {
		String str = "INSERT INTO `bishe`.`user` (`username`, `userpwd`, `usermail`, `userphone`, `useradd`, `userauth`) VALUES (?,?,?,?,?,?);";		
		Connection conn = this.Connect2MySQL();
		PreparedStatement ps = conn.prepareStatement(str);
		ps.setString(1, username);
		ps.setString(2, userpwd);
		ps.setString(3, usermail);
		ps.setString(4, userphone);
		ps.setString(5, useradd);
		ps.setString(6, userauth);
		if(ps.executeUpdate()==1){
			logger.info("new user <" + username + "> created");
		}
		ps.close();
		conn.close();
	}
	
	public void alterUserInfo(String username, String userpwd, String usermail, String userphone, String useradd) throws Exception{		
		String alterStr = "UPDATE `user` SET userpwd='" + userpwd + "', usermail='" +  usermail + "', userphone='" + userphone + "', useradd='" + useradd + "' WHERE username='" + username + "'";		
		Connection conn = this.Connect2MySQL();
		Statement stmt = conn.createStatement();
		stmt.execute(alterStr);
		stmt.close();
		conn.close();
	}
	
	public ArrayList<User> getAllUser() throws Exception {
		ArrayList<User> userlist = new ArrayList<User>();
		String str = "Select * from `user`";		
		Connection conn = this.Connect2MySQL();		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(str);			
		while(rs.next()){	
			User u = new User();
			u.setUsername(rs.getString("username"));
			u.setUsermail(rs.getString("usermail"));
			u.setUserpwd(rs.getString("userpwd"));
			u.setUserphone(rs.getString("userphone"));
			u.setUseradd(rs.getString("useradd"));
			u.setUserauth(rs.getString("userauth"));
			userlist.add(u);
		}
		rs.close();
		stmt.close();
		conn.close();
		return userlist;	
	}
	
	public void deleteUser(String username) throws Exception {
		String deleteStr = "delete from `user` where username = '" + username + "'";		
		Connection conn = this.Connect2MySQL();
		Statement stmt = conn.createStatement();
		stmt.execute(deleteStr);
		logger.warning(deleteStr);
		stmt.close();
		conn.close();
	}
	
	public void uploadItem(String itemname, String itemcate, String itemcond, String itemprice, String itemcount, String iteminfo, String username, String itemimage) throws Exception {
		String str = "INSERT INTO `bishe`.`item` (`itemname`, `itemcate`, `itemcond`, `itemprice`, `itemcount`, `iteminfo`, `itemseller`, `itemimage`) VALUES (?,?,?,?,?,?,?,?);";		
		Connection conn = this.Connect2MySQL();
		PreparedStatement ps = conn.prepareStatement(str);
		ps.setString(1, itemname);
		ps.setString(2, itemcate);
		ps.setString(3, itemcond);
		ps.setString(4, itemprice);
		ps.setString(5, itemcount);
		ps.setString(6, iteminfo);
		ps.setString(7, username);
		ps.setString(8, itemimage);
		if(ps.executeUpdate()==1){
			logger.info("new item <" + itemname + "> uploaded");
		}
		ps.close();
		conn.close();
	}
	
	public void alterItemInfo(String itemname, String itemcate, String itemcond, String itemprice, String itemcount, String iteminfo, String itemimage, String itemid) throws Exception{		
		String alterStr = "UPDATE `item` SET itemname='" + itemname + "', itemcate=" + itemcate + ", itemcond='" + itemcond + "', itemprice=" +  itemprice + ", itemcount=" + itemcount + ", iteminfo='" + iteminfo + "', itemimage='" + itemimage + "' WHERE itemid=" + itemid;		
		Connection conn = this.Connect2MySQL();
		Statement stmt = conn.createStatement();
		stmt.execute(alterStr);
		stmt.close();
		conn.close();
	}
	
	public Item getItem(String itemid) throws Exception {		
		String str = "Select * from `item` where itemid = " + itemid ;
		Connection conn = this.Connect2MySQL();	
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(str);
		Item i = new Item();
		if(rs.next()){
			i.setitemname(rs.getString("itemname"));
			i.setitemcate(rs.getString("itemcate"));
			i.setitemcond(rs.getString("itemcond"));
			i.setitemprice(rs.getString("itemprice"));
			i.setitemcount(rs.getString("itemcount"));
			i.setiteminfo(rs.getString("iteminfo"));
			i.setitemseller(rs.getString("itemseller"));
			i.setitemimage(rs.getString("itemimage"));
		}
		rs.close();
		stmt.close();
		conn.close();
		return i;
	}
	
	public ArrayList<Item> getAllItem() throws Exception {
		ArrayList<Item> itemlist = new ArrayList<Item>();
		String Str = "SELECT * FROM `item`";	
		Connection conn = this.Connect2MySQL();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(Str);	
		while(rs.next()){
			Item i = new Item();
			i.setitemid(rs.getString("itemid"));
			i.setitemname(rs.getString("itemname"));
			i.setitemcate(rs.getString("itemcate"));
			i.setitemcond(rs.getString("itemcond"));
			i.setitemprice(rs.getString("itemprice"));
			i.setitemcount(rs.getString("itemcount"));
			i.setiteminfo(rs.getString("iteminfo"));
			i.setitemseller(rs.getString("itemseller"));
			i.setitemimage(rs.getString("itemimage"));
			itemlist.add(i);
		}
		rs.close();
		stmt.close();
		conn.close();
		return itemlist;
	}
	
	public ArrayList<Item> getNewItem() throws Exception {
		ArrayList<Item> itemlist = new ArrayList<Item>();
		String Str = "SELECT * FROM `item` ORDER BY itemid DESC LIMIT 6";	
		Connection conn = this.Connect2MySQL();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(Str);	
		while(rs.next()){
			Item i = new Item();
			i.setitemid(rs.getString("itemid"));
			i.setitemname(rs.getString("itemname"));
			i.setitemcate(rs.getString("itemcate"));
			i.setitemcond(rs.getString("itemcond"));
			i.setitemprice(rs.getString("itemprice"));
			i.setitemcount(rs.getString("itemcount"));
			i.setiteminfo(rs.getString("iteminfo"));
			i.setitemseller(rs.getString("itemseller"));
			i.setitemimage(rs.getString("itemimage"));
			itemlist.add(i);
		}
		rs.close();
		stmt.close();
		conn.close();
		return itemlist;
	}
	
	public ArrayList<Item> getRecItem(String username) throws Exception {
		String Str = "SELECT orderitem FROM `order` WHERE buyername = '" + username + "' ORDER BY ordertime DESC LIMIT 1";
		Connection conn = this.Connect2MySQL();
		Statement stmt1 = conn.createStatement();
		ResultSet rs1 = stmt1.executeQuery(Str);
		Order lastorder = new Order();
		while(rs1.next()){
			lastorder.setorderitem(rs1.getString("orderitem"));
		}
		rs1.close();
		stmt1.close();
		
		Str = "SELECT itemcate FROM `item` WHERE itemid = " + lastorder.getorderitem();
		Statement stmt2 = conn.createStatement();
		ResultSet rs2 = stmt2.executeQuery(Str);
		Item lastitem = new Item();
		while(rs2.next()){
			lastitem.setitemcate(rs2.getString("itemcate"));
		}
		rs2.close();
		stmt2.close();
		
		String cateid = lastitem.getitemcate();	
		Str = "SELECT * FROM `item` WHERE itemcate = " + cateid + " ORDER BY itemid DESC LIMIT 3";	
		Statement stmt3 = conn.createStatement();
		ResultSet rs3 = stmt3.executeQuery(Str);
		ArrayList<Item> itemlist = new ArrayList<Item>();
		while(rs3.next()){
			Item i = new Item();
			i.setitemid(rs3.getString("itemid"));
			i.setitemname(rs3.getString("itemname"));
			i.setitemcate(rs3.getString("itemcate"));
			i.setitemcond(rs3.getString("itemcond"));
			i.setitemprice(rs3.getString("itemprice"));
			i.setitemcount(rs3.getString("itemcount"));
			i.setiteminfo(rs3.getString("iteminfo"));
			i.setitemseller(rs3.getString("itemseller"));
			i.setitemimage(rs3.getString("itemimage"));
			itemlist.add(i);
		}
		rs3.close();
		stmt3.close();
		conn.close();
		return itemlist;
	}
	
	public ArrayList<Item> getMyitem(String username) throws Exception {
		ArrayList<Item> myitemlist = new ArrayList<Item>();
		String Str = "SELECT * FROM `item` WHERE itemseller = '" + username + "'";	
		Connection conn = this.Connect2MySQL();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(Str);	
		while(rs.next()){
			Item i = new Item();
			i.setitemid(rs.getString("itemid"));
			i.setitemname(rs.getString("itemname"));
			i.setitemcate(rs.getString("itemcate"));
			i.setitemcond(rs.getString("itemcond"));
			i.setitemprice(rs.getString("itemprice"));
			i.setitemcount(rs.getString("itemcount"));
			i.setiteminfo(rs.getString("iteminfo"));
			i.setitemseller(rs.getString("itemseller"));
			i.setitemimage(rs.getString("itemimage"));
			myitemlist.add(i);
		}
		rs.close();
		stmt.close();
		conn.close();
		return myitemlist;
	}
	
	public void deleteItem(String itemid) throws Exception {
		String deleteStr = "delete from `item` where itemid = " + itemid;		
		Connection conn = this.Connect2MySQL();
		Statement stmt = conn.createStatement();
		stmt.execute(deleteStr);
		logger.warning(deleteStr);
		stmt.close();
		conn.close();
	}
	
	public boolean chkFavor(String favoruser, String favoritem) throws Exception{		
		String Str = "Select * from `favor` where favoruser = '" + favoruser + "' and favoritem = " + favoritem;
		Connection conn = this.Connect2MySQL();		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(Str);		
		if(rs.next()){
			return true;
		}
		return false;			
	}
	
	public void favor(String favoruser, String favoritem) throws Exception {
		java.util.Date now = new java.util.Date();
		java.sql.Timestamp ts = new java.sql.Timestamp(now.getTime());
		String str = "INSERT INTO `bishe`.`favor` (`favoruser`, `favoritem`, `favortime`) VALUES (?,?,?);";		
		Connection conn = this.Connect2MySQL();
		PreparedStatement ps = conn.prepareStatement(str);
		ps.setString(1, favoruser);
		ps.setString(2, favoritem);
		ps.setTimestamp(3, ts);
		if(ps.executeUpdate()==1){
			logger.info("itemid " + favoritem + " favored by user <" + favoruser + ">");
		}
		ps.close();
		conn.close();
	}
	
	public void unfavor(String favoruser, String favoritem) throws Exception {
		String cancelStr = "delete from `favor` where favoruser = '" + favoruser + "' and favoritem = " + favoritem;		
		Connection conn = this.Connect2MySQL();
		Statement stmt = conn.createStatement();
		stmt.execute(cancelStr);
		logger.warning(cancelStr);
		stmt.close();
		conn.close();
	}
	
	public ArrayList<Favor> getMyfavor(String favoruser) throws Exception {
		ArrayList<Favor> myfavor = new ArrayList<Favor>();
		String Str = "SELECT * FROM `favor` where favoruser = '" + favoruser + "'";	
		Connection conn = this.Connect2MySQL();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(Str);	
		while(rs.next()){
			Favor f = new Favor();
			f.setfavoritem(rs.getString("favoritem"));
			f.setfavortime(rs.getString("favortime"));
			myfavor.add(f);
		}
		rs.close();
		stmt.close();
		conn.close();
		return myfavor;
	}
	
	public void newOrder(String orderitem, String buyername, String sellername, String ordercount, String ordersum, String ordercond) throws Exception {
		java.util.Date now = new java.util.Date();
		java.sql.Timestamp ts = new java.sql.Timestamp(now.getTime());
		String str1 = "INSERT INTO `bishe`.`order` (`orderitem`, `buyername`, `sellername`, `ordercount`, `ordersum`, `ordercond`, `ordertime`) VALUES (?,?,?,?,?,?,?);";		
		String Str2 = "UPDATE `item` SET itemcount = itemcount - " + ordercount + " WHERE itemid = " + orderitem;
		Connection conn = this.Connect2MySQL();
		PreparedStatement ps = conn.prepareStatement(str1);
		ps.setString(1, orderitem);
		ps.setString(2, buyername);
		ps.setString(3, sellername);
		ps.setString(4, ordercount);
		ps.setString(5, ordersum);
		ps.setString(6, ordercond);
		ps.setTimestamp(7, ts);
		if(ps.executeUpdate()==1){
			logger.info("new order created by <" + buyername + ">");
		}
		ps.close();
		Statement stmt = conn.createStatement();
		stmt.execute(Str2);
		logger.warning(Str2);
		stmt.close();
		conn.close();
	}
	
	public Order getOrder(String orderid) throws Exception {		
		String str = "Select * from `order` where orderid = " + orderid ;
		Connection conn = this.Connect2MySQL();	
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(str);
		Order d = new Order();
		if(rs.next()){
			d.setorderid(rs.getString("orderid"));
			d.setorderitem(rs.getString("orderitem"));
			d.setbuyername(rs.getString("buyername"));
			d.setsellername(rs.getString("sellername"));
			d.setordercount(rs.getString("ordercount"));
			d.setordersum(rs.getString("ordersum"));
			d.setordercond(rs.getString("ordercond"));
			d.setordertime(rs.getString("ordertime"));
			d.setorderscore(rs.getString("orderscore"));
		}
		rs.close();
		stmt.close();
		conn.close();
		return d;
	}
	
	public void cancelOrder(String orderid, String ordercount, String orderitem) throws Exception {
		String Str1 = "UPDATE `item` SET itemcount = itemcount + " + ordercount + " WHERE itemid = " + orderitem; 
		String Str2 = "delete from `order` where orderid = " + orderid;
		Connection conn = this.Connect2MySQL();
		Statement stmt1 = conn.createStatement();
		stmt1.execute(Str1);
		logger.warning(Str1);
		stmt1.close();
		Statement stmt2 = conn.createStatement();
		stmt2.execute(Str2);
		logger.warning(Str2);
		stmt2.close();
		conn.close();
	}
	
	public void deleteOrder(String orderid) throws Exception { 
		String Str = "delete from `order` where orderid = " + orderid;
		Connection conn = this.Connect2MySQL();
		Statement stmt = conn.createStatement();
		stmt.execute(Str);
		logger.warning(Str);
		stmt.close();
		conn.close();
	}
	
	public ArrayList<Order> getOrderBuy(String username) throws Exception {
		ArrayList<Order> orderbuy = new ArrayList<Order>();
		String Str = "SELECT * FROM `order` where buyername = '" + username + "'";
		Connection conn = this.Connect2MySQL();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(Str);	
		while(rs.next()){
			Order d = new Order();
			d.setorderid(rs.getString("orderid"));
			d.setorderitem(rs.getString("orderitem"));
			d.setsellername(rs.getString("sellername"));
			d.setordercount(rs.getString("ordercount"));
			d.setordersum(rs.getString("ordersum"));
			d.setordercond(rs.getString("ordercond"));
			d.setordertime(rs.getString("ordertime"));
			d.setorderscore(rs.getString("orderscore"));
			orderbuy.add(d);
		}
		rs.close();
		stmt.close();
		conn.close();
		return orderbuy;
	}
	
	public ArrayList<Order> getOrderSell(String username) throws Exception {
		ArrayList<Order> ordersell = new ArrayList<Order>();
		String Str = "SELECT * FROM `order` where sellername = '" + username + "'";
		Connection conn = this.Connect2MySQL();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(Str);	
		while(rs.next()){
			Order d = new Order();
			d.setorderid(rs.getString("orderid"));
			d.setorderitem(rs.getString("orderitem"));
			d.setbuyername(rs.getString("buyername"));
			d.setordercount(rs.getString("ordercount"));
			d.setordersum(rs.getString("ordersum"));
			d.setordercond(rs.getString("ordercond"));
			d.setordertime(rs.getString("ordertime"));
			d.setorderscore(rs.getString("orderscore"));
			ordersell.add(d);
		}
		rs.close();
		stmt.close();
		conn.close();
		return ordersell;
	}
	
	public ArrayList<Order> getAllOrder() throws Exception {
		ArrayList<Order> orderall = new ArrayList<Order>();
		String Str = "SELECT * FROM `order`";
		Connection conn = this.Connect2MySQL();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(Str);	
		while(rs.next()){
			Order d = new Order();
			d.setorderid(rs.getString("orderid"));
			d.setorderitem(rs.getString("orderitem"));
			d.setbuyername(rs.getString("buyername"));
			d.setsellername(rs.getString("sellername"));
			d.setordercount(rs.getString("ordercount"));
			d.setordersum(rs.getString("ordersum"));
			d.setordercond(rs.getString("ordercond"));
			d.setordertime(rs.getString("ordertime"));
			d.setorderscore(rs.getString("orderscore"));
			orderall.add(d);
		}
		rs.close();
		stmt.close();
		conn.close();
		return orderall;
	}
	
	public void payOrder(String orderid) throws Exception {
		String alterStr = "update `order` set ordercond = '未发货' where orderid = " + orderid;		
		Connection conn = this.Connect2MySQL();
		Statement stmt = conn.createStatement();
		stmt.execute(alterStr);
		logger.info(alterStr);
		stmt.close();
		conn.close();
	}
	
	public void shipment(String orderid) throws Exception {
		String alterStr = "update `order` set ordercond = '已发货' where orderid = " + orderid;		
		Connection conn = this.Connect2MySQL();
		Statement stmt = conn.createStatement();
		stmt.execute(alterStr);
		logger.info(alterStr);
		stmt.close();
		conn.close();
	}
	
	public void receipt(String orderid) throws Exception {
		String alterStr = "update `order` set ordercond = '已收货' where orderid = " + orderid;		
		Connection conn = this.Connect2MySQL();
		Statement stmt = conn.createStatement();
		stmt.execute(alterStr);
		logger.info(alterStr);
		stmt.close();
		conn.close();
	}
	
	public void rateOrder(String orderid, String orderscore) throws Exception {
		String alterStr = "update `order` set ordercond = '已评价', orderscore = " + orderscore + " where orderid = " + orderid;		
		Connection conn = this.Connect2MySQL();
		Statement stmt = conn.createStatement();
		stmt.execute(alterStr);
		logger.info(alterStr);
		stmt.close();
		conn.close();
	}
	
	public ArrayList<Item> getSearch(String search) throws Exception {
		ArrayList<Item> searchlist = new ArrayList<Item>();
		String searchStr = "SELECT * FROM `item` WHERE (itemname LIKE '%" + search + "%' OR iteminfo LIKE '%" + search + "%')";	
		Connection conn = this.Connect2MySQL();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(searchStr);	
		while(rs.next()){
			Item i = new Item();
			i.setitemid(rs.getString("itemid"));
			i.setitemname(rs.getString("itemname"));
			i.setitemcate(rs.getString("itemcate"));
			i.setitemcond(rs.getString("itemcond"));
			i.setitemprice(rs.getString("itemprice"));
			i.setitemcount(rs.getString("itemcount"));
			i.setiteminfo(rs.getString("iteminfo"));
			i.setitemseller(rs.getString("itemseller"));
			i.setitemimage(rs.getString("itemimage"));
			searchlist.add(i);
		}
		rs.close();
		stmt.close();
		conn.close();
		return searchlist;
	}
	
	public ArrayList<Item> getCategory(String cateid) throws Exception {
		ArrayList<Item> catelist = new ArrayList<Item>();
		String cateStr = "SELECT * FROM `item` WHERE itemcate = " + cateid;	
		Connection conn = this.Connect2MySQL();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(cateStr);	
		while(rs.next()){
			Item i = new Item();
			i.setitemid(rs.getString("itemid"));
			i.setitemname(rs.getString("itemname"));
			i.setitemcate(rs.getString("itemcate"));
			i.setitemcond(rs.getString("itemcond"));
			i.setitemprice(rs.getString("itemprice"));
			i.setitemcount(rs.getString("itemcount"));
			i.setiteminfo(rs.getString("iteminfo"));
			i.setitemseller(rs.getString("itemseller"));
			i.setitemimage(rs.getString("itemimage"));
			catelist.add(i);
		}
		rs.close();
		stmt.close();
		conn.close();
		return catelist;
	}
	
	public String getDonation() throws Exception {
		Double d_sum = 0.00;
		String Str = "SELECT ordersum FROM `order`";
		Connection conn = this.Connect2MySQL();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(Str);
		Order d = new Order();
		while(rs.next()){			
			d.setordersum(rs.getString("ordersum"));
			d_sum += Double.valueOf(d.getordersum());			
		}
		String donation = String.valueOf(d_sum);
		rs.close();
		stmt.close();
		conn.close();
		return donation;
	}
	
}