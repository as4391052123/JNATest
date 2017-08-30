package test.icegalaxy;

import java.util.ArrayList;
import java.util.Arrays;

import test.icegalaxy.SPApi;

import java.util.List;

import com.sun.jna.Callback;
import com.sun.jna.Library;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.StdCallLibrary.StdCallCallback;


public class TestSPApi4
{

	static int counter;
	static long status = 0;
	public static double currentBid;
	public static double currentAsk;
	
	static byte[] product = getBytes("CLV7", 16);
	
	/*static int port = 8080;
	static String license = "58A665DE84D02";
	static String app_id = "SPDEMO";
	static String userid = "DEMO201702141";
	static String password = "00000000";
	static String server = "demo.spsystem.info";
*/
	static int port = 8080;
	static String license = "58BA6E2F967DE";
	static String app_id = "SPDEMO";
	static String userid = "DEMO201703047";
	static String password = "830811aa";
	static String server = "demo.spsystem.info";

//	SPApi api;
	

	
	public static int getAccInfo()
	{
		
		SPApi api = SPApi.INSTANCE;
		
		
		SPApi.SPApiAccInfo info = new SPApi.SPApiAccInfo();

		int i = api.SPAPI_GetAccInfo(userid, info);

		System.out.println("AEID: " + Native.toString(info.AEId));
		System.out.println("ClientID: " + Native.toString(info.ClientId));
		System.out.println("AccName: " + Native.toString(info.AccName));
		System.out.println("Buying Power: " + info.BuyingPower);

		api = null;
		System.gc();
		
		return i;

	}

	public static int getPriceByCode()
	{
		SPApi api = SPApi.INSTANCE;
		SPApi.SPApiPrice price = new SPApi.SPApiPrice();
		int i = api.SPAPI_GetPriceByCode(userid, product, price);
		System.out.println("Get price by code: " + price.Last[0] + ", Open: " + price.Open);
		currentBid = price.Bid[0];
		currentAsk = price.Ask[0];
		
		api = null;
		System.gc();
		
		return i;
	}

	public static void processOrder(SPApi.SPApiOrder order, byte buy_sell)
	{

//		
//		int rc;
		

//		setBytes(order.AccNo, userid);
		
		byte[] scr = userid.getBytes();
//		order.AccNo[0] = (byte) scr.length;
		System.arraycopy(scr, 0, order.AccNo, 0, scr.length);
//		order.AccNo = Native.toByteArray(userid);

		order.ProdCode = product; // need the replace necessary byte one by one,
									// not setting the whole new array

//		setBytes(order.Initiator, userid);
		
//		order.Initiator[0] = (byte) scr.length;
		System.arraycopy(scr, 0, order.Initiator, 0, scr.length);
		
//		order.Initiator = Native.toByteArray(userid);

		order.BuySell = buy_sell;
	
		order.Qty = new NativeLong((long) 2);

//		setBytes(order.Ref, "Java");
//		setBytes(order.Ref2, "SPAPI");
//		setBytes(order.GatewayCode, "");
		
		
		

		order.CondType = 0; // normal type
//		setBytes(order.ClOrderId, "2");
		order.ValidType = 0;

		order.DecInPrice = 2;
		
		order.StopType = 'L';
		order.OrderType = 0; // limit
		order.OrderOptions = new NativeLong((long) 0);

		getPriceByCode();

		if (buy_sell == 'B')
		{
			order.Price = currentAsk; // market price
	//		order.OpenClose = 'O';
			order.OpenClose = '\0';
		}
		else
		{
			order.Price = currentBid;
	//		order.OpenClose = 'C';
			order.OpenClose = '\0';
		}
		
	/*	if (buy_sell == 'B')
			order.Price = 50; // market price
		else
			order.Price = 60;*/

	

		// System.out.println("Activate all orders: [" +
		// SPApiDll.INSTANCE.SPAPI_ActivateAllOrders(userid, userid) + "]");

		// System.out.println("order status: " + order.Status);

		
		// if (rc == 0) { if (DllShowTextData != null) DllShowTextData("Add
		// Order Success!"); }
		// else { if (DllShowTextData != null) DllShowTextData("Add Order
		// Failure! " + rc.ToString()); }

	}

	public static void displayAllTrades()
	{
		ArrayList<SPApi.SPApiTrade> trades = null;

		SPApi api = SPApi.INSTANCE;
		
		api.SPAPI_GetAllTrades(userid, userid, trades);

		System.out.println("Trying to display all trades");

		for (int i = 0; i < trades.size(); i++)
		{

			System.out.println("Rec No: " + trades.get(i).RecNo + ", Price: " + trades.get(i).Price + ", BuySell: "
					+ trades.get(i).BuySell);

		}
		
		api = null;
		System.gc();

	}

	

	
	public static void main(String[] args)
	{

		int in = 1;
		int un = 1;
		int price = 1;
		int login = 1;
		int logout = 1;
		
		SPApi api = SPApi.INSTANCE;
				
		api = null;
		System.gc();
		
		
		api = SPApi.INSTANCE;
		
		SPApi.LogoutReply logoutReply = (accNo, ret_code, ret_msg) -> System.out.println("Logout Reply, accNo: " + accNo + ", ret_code: " + ret_code + ",ret_msg: " + ret_msg );
		
		SPApi.RegisterOrder orderReport = (rec, order) -> System.out.println("Order report, Rec no: " + rec + ", Price: " + order.Price);
		
		SPApi.RegisterOrderB4 orderB4 = (orderB4x) -> System.out.println("Order status b4: " + orderB4x.Status);

		SPApi.RegisterTradeReport tradeReport = (rec_no, trade) -> System.out.println("Rec_no: " + rec_no + ", Price: " + trade.Price);
		
		SPApi.RegisterLoginReply loginReply = (ret_code, ret_msg) -> System.out.println("Login reply: " + ret_msg + " [" + ret_code + "]");
		//Try using lambda
		SPApi.RegisterPriceUpdate priceUpdate = (last) -> System.out.print(last.Last[0] + ", Dec place: " + last.DecInPrice + " " );
	

		SPApi.RegisterConn conn = (host_type,  con_status) ->
		{
			System.out.println("conn reply- host type: " + host_type + ", con state: " + con_status);
			status += con_status;
		};
				
		SPApi.RegisterOrderFail orderFail = (action, order, err_code, err_msg) -> System.out.println("Action no: " + action + 
				", order status: " + order.Status + ", dec place: " + order.DecInPrice + ", Error msg: " + err_msg);

		in = api.SPAPI_Initialize();
		
		api.SPAPI_Logout(userid);
		
		api = null;
		System.gc();
				
		
		api = SPApi.INSTANCE;
		
		api.SPAPI_RegisterAccountLogoutReply(logoutReply);

		// SPApiDll.INSTANCE.SPAPI_RegisterLoginReply(loginReply);

		api.SPAPI_RegisterApiPriceUpdate(priceUpdate);

		api.SPAPI_RegisterTradeReport(tradeReport);
		
		api.SPAPI_RegisterOrderRequestFailed(orderFail);
		
		
		
		//api.SPAPI_RegisterOrderBeforeSendReport(orderB4);

		// SPApiDll.INSTANCE.SPAPI_RegisterLoginStatusUpdate(update);

		// SPApiDll.INSTANCE.SPAPI_RegisterConnectionErrorUpdate(error);

		api.SPAPI_RegisterConnectingReply(conn);
		
		api.SPAPI_RegisterOrderReport(orderReport);

		api.SPAPI_RegisterLoginReply(loginReply);

		api.SPAPI_SetLoginInfo(server, port, license, app_id, userid, password);

		login = api.SPAPI_Login();

		while (status < 9)

		{

			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}

		}
		
		System.out.println("Test 1");

		price = api.SPAPI_SubscribePrice(userid, product, 1);

		System.out.println("Price subscribed: " + price);
		

		while (true)
		{
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			counter++;
		//	System.out.println("counter: " + counter);
			if (counter > 2)
				break;
		}

		System.out.println("Test 2");
		
		System.out.println("AccInfo: " + getAccInfo());

		System.out.println("Test 3");
		
		SPApi.SPApiOrder order = new SPApi.SPApiOrder();
		
		processOrder(order, (byte) 'B');
		
		api.SPAPI_AddOrder(order);
		
		order = null;
		System.gc();

		System.out.println("Add order:  B");

		System.out.println("Test 4");
		
		counter = 0;
		
		while (true)
		{
			sleep(1000);
			counter++;
			if (counter > 10)
				break;
		}
	//	SPApiDll.INSTANCE.SPAPI_DeleteAllOrders(userid, userid);
//		getPriceByCode();

		System.out.println("AccInfo: " + getAccInfo());

		order = new SPApi.SPApiOrder();
		
		processOrder(order, (byte) 'S');
		
		api.SPAPI_AddOrder(order);
		
		order = null;
		System.gc();


		
		counter = 0;
		
		while (true)
		{
			sleep(1000);
			counter++;
			if (counter > 10)
				break;
		}
		
		System.out.println("AccInfo: " + getAccInfo());
//		displayAllTrades();

		price = api.SPAPI_SubscribePrice(userid, product, 0);

		sleep(1000);

		logout = api.SPAPI_Logout(userid);

		while (logout != 0)
		{
			sleep(1000);

			System.out.println("Logout: " + logout);
		}

		System.out.println("init: " + in);
		System.out.println("login: " + login);

		System.out.println("logout: " + logout);

		un = api.SPAPI_Uninitialize(userid);

		// System.out.println("init: " + in);
		// System.out.println("login: " + login);
		//
		// System.out.println("logout: " + logout);
		System.out.println("uninit: " + un);
		api = null;
		System.gc();
	}

	public static void setBytes(byte[] bytes, String s)
	{
		for (int i = 0; i < s.length(); i++)
		{
			bytes[i] = (byte) s.charAt(i);
		}

	}

	public static byte[] getBytes(String s, int size)
	{

		byte[] bytes = new byte[size];

		for (int i = 0; i < s.length(); i++)
		{
			bytes[i] = (byte) s.charAt(i);

		}
		return bytes;

	}

	public static void sleep(int i)
	{
		try
		{
			Thread.sleep(i);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
