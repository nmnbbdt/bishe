<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*, DAO.*, java.util.ArrayList" %>
<% 
request.setCharacterEncoding("UTF-8"); 
response.setCharacterEncoding("UTF-8"); 
response.setContentType("text/html; charset=utf-8"); 
%>
<html>
<head>
<title>订单</title>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>

<%
	User me = new User();
	me = (User)session.getAttribute("currentUser");
	String myname = me.getUsername();
	String myauth = me.getUserauth();
	
	DBConnect conn = new DBConnect();
	ArrayList<Order> orderlist = new ArrayList<Order>();
    orderlist = conn.getOrderBuy(myname);
    Order d = new Order();
%>

<div id="main_container">

	<div id="header">

        <div class="top_right" onselectstart="return false">
			<p>中 财 二 手 义 卖</p>        
        </div>
      
    </div>
    
   <div id="main_content"> 
    	<div id="menu_tab">
        	<ul class="menu">
            	<li><a href="index1.jsp" class="nav">首 页</a></li>
                <li class="divider"></li>
                         
		<%
                    
			if(myauth.equals("1")){
				out.println("<li><a href='orderbuy.jsp' class='nav'>我 的 订 单</a></li>");
				out.println("<li class='divider'></li>");
				out.println("<li><a href='myfavor.jsp' class='nav'>收 藏 夹</a></li>");								
			}else if(myauth.equals("2")){
				out.println("<li><a href='upload.jsp' class='nav'>发 布 新 商 品</a></li>");
				out.println("<li class='divider'></li>");
				out.println("<li><a href='myitem.jsp' class='nav'>我 的 商 品</a></li>");
				out.println("<li class='divider'></li>");
				out.println("<li><a href='ordersell.jsp' class='nav'>订 单 管 理</a></li>");
			}else{
				out.println("<li><a href='itemlist.jsp' class='nav'>商 品 管 理</a></li>");
				out.println("<li class='divider'></li>");
				out.println("<li><a href='userlist.jsp' class='nav'>用 户 管 理</a></li>");
				out.println("<li class='divider'></li>");
				out.println("<li><a href='orderlist.jsp' class='nav'>订 单 管 理</a></li>");
			}
                    
		%>
       
                <li class="divider"></li>
                <li><a href="index.jsp" class="nav">登 出</a></li>
				<li class="divider"></li>
				<li><a href="myinfor.jsp" class="username"><%=myname %></a></li>
			</ul>
		</div><!-- end of menu tab -->
            
    <div class="crumb_navigation">
    导 航：<span class="current">订 单</span>
    </div>        
    
   <div class="left_content">
    <div class="title_box">分 类</div>
    
        <ul class="left_menu">
         <li class="odd"><a href="category1.jsp?id=1">图书教辅</a></li>
        <li class="even"><a href="category1.jsp?id=2">数码产品</a></li>
         <li class="odd"><a href="category1.jsp?id=3">办公文具</a></li>
        <li class="even"><a href="category1.jsp?id=4">体育器材</a></li>
         <li class="odd"><a href="category1.jsp?id=5">生活用品</a></li>
        <li class="even"><a href="category1.jsp?id=6">手工艺品</a></li>
         <li class="odd"><a href="category1.jsp?id=7">男 装</a></li>
        <li class="even"><a href="category1.jsp?id=8">女 装</a></li>
         <li class="odd"><a href="category1.jsp?id=9">其 它</a></li>        
       </ul>
    
   </div><!-- end of left content --> 

   <div class="center_content">
   
   	<div class="center_title_bar">我的订单</div>
   	
   	<%
   	
   		for(int i = orderlist.size(); i > 0 ; i--){
    		
    		d = orderlist.get(i-1);
    		String itemname = conn.getItem(d.getorderitem()).getitemname();
    		String seller = d.getsellername();
    
    		out.println("<div class='prod_box_big'>");	
    		out.println("<div class='center_prod_box_big'>");
    		out.println("<div class='details_big_box'>");
    		out.println("<a href='item1.jsp?id=");
            out.println(d.getorderitem());
            out.println("' class='product_title_big'>");
            out.println(itemname);
            out.println("</a>");
    		out.println("<div class='specifications'>");     
    		out.println("卖家: <span class='blue'>");     
    		out.println(seller);
    		out.println("</span>数量: <span class='blue'>");            
    		out.println(d.getordercount());
    		out.println("</span>总价: <span class='blue'>￥ ");            
    		out.println(d.getordersum());
    		out.println("</span><br/>下单时间: <span class='blue'>");            
    		out.println(d.getordertime());
    		out.println("</span>");               	
    		out.println("<div class='prod_price_big'>订单状态：<span class='price'>");				
    		out.println(d.getordercond());				
    		out.println("</span></div></div></div>");
    		if(d.getordercond().equals("未付款")){
    			out.println("<a href='cancelOrder.jsp?id=");
                out.println(d.getorderid());
                out.println("' class='prod_favor'>取消订单</a>");
    			out.println("<a href='pay.jsp?id=");
                out.println(d.getorderid());
                out.println("' class='prod_details'>选择支付方式</a>");               
    		}else if(d.getordercond().equals("未发货")){    			
                out.println("<a href='cancelOrder.jsp?id=");
                out.println(d.getorderid());
                out.println("' class='prod_favor'>取消订单</a>");
                out.println("<a href='#' class='prod_details'>提醒卖家发货</a>");//TODO:提醒卖家发货
    		}else if(d.getordercond().equals("已发货")){
    			out.println("<a href='#.jsp' class='prod_details'>确认收货</a>");//TODO:确认收货,评价
    		}
    		                             
    		out.println("</div></div>");	
                              
   		}
   	
   %>
   
   </div><!-- end of center content -->

 <div class="right_content">
 
		<div class="title_box">商 品 搜 索</div><br/>		
		<form method="post" action="searchResult1.jsp">				  
			<input type="text" name="search"/>
			<br/><br/>
			<input type="submit" value="搜索"/>			
        </form>
        
   </div><!-- end of right content -->   
        
   </div><!-- end of main content -->
   
   <div class="footer">
   
        <p>中 财 二 手 义 卖. All Rights Reserved 2017</p>
   
   </div>                 


</div>
<!-- end of main_container -->
</body>
</html>