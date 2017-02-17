package test.icegalaxy;

import java.util.Arrays;
import java.util.List;

import javax.imageio.spi.RegisterableService;

import com.sun.jna.Callback;
import com.sun.jna.Library;

import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;

import test.icegalaxy.HelloWorld.CLibrary;

import test.icegalaxy.TestSPApi.SPApiDll.RegisterConn;
import test.icegalaxy.TestSPApi.SPApiDll.RegisterError;
import test.icegalaxy.TestSPApi.SPApiDll.RegisterLoadTradeEnd;
import test.icegalaxy.TestSPApi.SPApiDll.RegisterLoginReply;
import test.icegalaxy.TestSPApi.SPApiDll.RegisterLoginStatusUpdate;
import test.icegalaxy.TestSPApi.SPApiDll.RegisterPServerLink;
import test.icegalaxy.TestSPApi.SPApiDll.RegisterPriceUpdate;
import test.icegalaxy.TestSPApi.SPApiDll.SPApiPrice;

public class TestSPApi {
	
	static int counter;
	static long status = 0;

	public interface SPApiDll extends Library {
		SPApiDll INSTANCE = (SPApiDll) Native.loadLibrary("spapidllm64.dll", SPApiDll.class);
		int SPAPI_Initialize();
		int SPAPI_Uninitialize();
		void SPAPI_SetBackgroundPoll(boolean onoff);
		void SPAPI_SetLoginInfo(String server, int port, String license, String app_id, String userid, String password);
		int SPAPI_Login();
		int SPAPI_GetLoginStatus(int host_id);
		int SPAPI_Logout(String user_id);
		void SPAPI_Poll();
		int SPAPI_SubscribePrice(String user_id, String prod_code, int mode);
		
		void SPAPI_RegisterApiPriceUpdate(RegisterPriceUpdate priceUpdate);
		
		void SPAPI_RegisterConnectingReply(RegisterConn conn);
		
//		void SPAPI_RegisterTradeReport(RegisterTradeReport tradeReport);
		void SPAPI_RegisterLoadTradeEnd(RegisterLoadTradeEnd loadTradeEnd);
		
		void SPAPI_RegisterLoginReply(RegisterLoginReply register);
		void SPAPI_RegisterLoginStatusUpdate(RegisterLoginStatusUpdate update);
		
		void SPAPI_RegisterPServerLinkStatusUpdate(RegisterPServerLink serverLink);
		void SPAPI_RegisterConnectionErrorUpdate(RegisterError error);
		
		
		
		public interface RegisterPriceUpdate extends Callback
		{
			void invoke(SPApiPrice price);
		}
		
		
		public interface RegisterConn extends Callback
		{
			void invoke(long host_type, long con_status);
		}
		
//		public interface RegisterTradeReport extends Callback
//		{
//			void invoke(String acc_no);
//		}
		
		public interface RegisterError extends Callback
		{
			void invoke(short host_id, long link_err);
		}
		
		public interface RegisterPServerLink extends Callback
		{
			void invoke(short host_id, long con_status);
		}
		
		public interface RegisterLoadTradeEnd extends Callback
		{
			void invoke(String acc_no);
		}
		
		
		public interface RegisterLoginReply extends Callback{
			void printLoginStatus(long ret_code, String ret_msg);
		}
		
		public interface RegisterLoginStatusUpdate extends Callback{
			void printStatus(long login_status);
		}
		
		public class SPApiPrice extends Structure
		{
			
//			public static class ByReference extends SPApiPrice implements Structure.ByReference{}
			
			public double[] Bid = new double[20];
			public int[] BidQty = new int[20];
			public int[] BidTicket = new int [20];
			public double[] Ask = new double[20];
			public int[] AskQty = new int[20];
			public int[] AskTicket = new int[20];
			public double[] Last = new double[20];
			public int[] LastQty = new int[20];
			public int[] LastTime = new int[20];
			public double Equil;
			public double Open;
			public double High;
			public double Low;
			public double Close;
			public int CloseDate; 
			public double TurnoverVol;
			public double TurnoverAmt;
			public int OpenInt;
			public char[] ProdCode = new char[16];
			public char[] ProdName = new char[40];
			public String DecInPrice;
			public int ExstateNo;
			public int TradeStateNo;
			public boolean Suspend;
			public int ExpiryYMD;
			public int ContractYMD;
			public int Timestamp;
			
			@Override
			protected List getFieldOrder() {
				return Arrays.asList(new String[]{"Bid","BidQty","BidTicket","Ask","AskQty","AskTicket","Last","LastQty","LastTime","Equil","Open","High","Low","Close","CloseDate","TurnoverVol","TurnoverAmt","OpenInt","ProdCode","ProdName","DecInPrice","ExstateNo","TradeStateNo","Suspend","ExpiryYMD","ContractYMD","Timestamp"});
			}
		
			
			
			
		}
	
		public class SPApiOrder extends Structure
		{

			public double Price;
			public double StopLevel;
			public double UpLevel;
			public double UpPrice;
			public double DownLevel;
			public double DownPrice;
			public bigint ExtOrderNo;
			public int32_t IntOrderNo;
			public int32_t Qty;
			public int32_t TradedQty;
			public int32_t TotalQty;
			public int32_t ValidTime;
			public int32_t SchedTime;
			public int32_t TimeStamp;
			public u_long OrderOptions;
			public STR16 AccNo;
			public STR16 ProdCode;
			public STR16 Initiator;
			public STR16 Ref;
			public STR16 Ref2;
			public STR16 GatewayCode;
			public STR40 ClOrderId;
			public char BuySell;
			public char StopType;
			public char OpenClose;
			public tinyint CondType;
			public tinyint OrderType;
			public tinyint ValidType;
			public tinyint Status;
			public tinyint DecInPrice;
			public tinyint OrderAction;
			public int32_t updateTime;
			public int32_t updateSeqNo;
			
			
			
			@Override
			protected List getFieldOrder()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			
			
		}
		
	}
	
	
	
	
	
	 public static void main(String[] args) {
		 
		 int port = 8080;
		    String license = "76C2FB5B60006C7A";
		    String app_id  = "BS";
		    String userid = "T865829";
		    String password = "ting1980";
		    String server = "futures.bsgroup.com.hk";
		    
//		    int port = 8080;
//		    String license = "58A665DE84D02";
//		    String app_id  = "SPDEMO";
//		    String userid = "DEMO201702141";
//		    String password = "vo2yv";
//		    String server = "demo.spsystem.info";
		 
		 int in = 1;
		 int un = 1;
		 int price = 1;
		 int login = 1;
		 int logout = 1;
		
		 
	
		 
		 RegisterPriceUpdate priceUpdate = new RegisterPriceUpdate() {
			
			@Override
			public void invoke(SPApiPrice price) {
				System.out.println("Lastest Deal: " + price.Last[0]);
				
			}

		};
		 
		 
		 RegisterConn conn = new RegisterConn() {
			
			@Override
			public void invoke(long host_type, long con_status) {
				System.out.println("conn reply- host type: " + host_type + ", con state: " + con_status); 
				status += con_status;
			}
		};
		 
		 RegisterError error = new RegisterError() {
			
			@Override
			public void invoke(short host_id, long link_err) {
				System.out.println("Error: " + link_err);
				
			}
		};
		 
		 RegisterPServerLink serverLink = new RegisterPServerLink() {
			
			@Override
			public void invoke(short host_id, long con_status) {
				System.out.println("Server Link: " +  con_status);
				
			}
		};
		 
		 RegisterLoginReply loginReply = new RegisterLoginReply() {
			
			@Override
			public void printLoginStatus(long ret_code, String ret_msg) {
				System.out.println("Login status: " + ret_code);
				
			}
		};
		
		RegisterLoginStatusUpdate update = new RegisterLoginStatusUpdate() {
				
				 @Override
				public void printStatus(long login_status) {
					System.out.println("Login status: " + login_status);
					
				}
			};
			
			RegisterLoadTradeEnd load = new RegisterLoadTradeEnd() {
				
				@Override
				public void invoke(String acc_no) {
					System.out.println("LoadTradeEnd: " + acc_no);
					
				}
			};
		 
			  in = SPApiDll.INSTANCE.SPAPI_Initialize();
			
//			  SPApiDll.INSTANCE.SPAPI_RegisterLoadTradeEnd(load);
			  
//			 SPApiDll.INSTANCE.SPAPI_RegisterLoginReply(loginReply);
			 
			 SPApiDll.INSTANCE.SPAPI_RegisterApiPriceUpdate(priceUpdate);
		   
//			 SPApiDll.INSTANCE.SPAPI_RegisterLoginStatusUpdate(update);
			
//			 SPApiDll.INSTANCE.SPAPI_RegisterPServerLinkStatusUpdate(serverLink);
			 
//			 SPApiDll.INSTANCE.SPAPI_RegisterConnectionErrorUpdate(error);
	     
//	       SPApiDll.INSTANCE.SPAPI_SetBackgroundPoll(true);
	       
//	       SPApiDll.INSTANCE.SPAPI_Poll();
			 SPApiDll.INSTANCE.SPAPI_RegisterConnectingReply(conn);
			 
	       SPApiDll.INSTANCE.SPAPI_SetLoginInfo(server, port, license, app_id, userid, password);
	       
	       login = SPApiDll.INSTANCE.SPAPI_Login();
	       
	     
  
	     
	      
	      
	       while (status < 9)
	    	   
	       {
	    	   
	    	   try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	    	   
	       }
	      
	       price = SPApiDll.INSTANCE.SPAPI_SubscribePrice(userid, "CLH7", 1);
	       
	       System.out.println("Price subscribed: " + price);
	       
	       while (true)
	       {
	    	   try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	  counter++;
	    	  System.out.println("counter: " + counter);
	    	  if (counter > 10)
	    		  break;
	       }
	      
	       price = SPApiDll.INSTANCE.SPAPI_SubscribePrice(userid, "CLH7", 0);
	       try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       
	       logout = SPApiDll.INSTANCE.SPAPI_Logout(userid);
	       
	       while (logout != 0)
	       {
	    	   try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	   
	    	  System.out.println("Logout: " + logout);
	       }
	    	   
	       
	       System.out.println("init: " + in);
	       System.out.println("login: " + login);
	      
	       System.out.println("logout: " + logout);
	       
	       un = SPApiDll.INSTANCE.SPAPI_Uninitialize();
	       
//	       System.out.println("init: " + in);
//	       System.out.println("login: " + login);
//	      
//	       System.out.println("logout: " + logout);
	       System.out.println("uninit: " + un);
	     
	    }
	
}

