package test.icegalaxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.jna.Callback;
import com.sun.jna.Library;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.StdCallLibrary.StdCallCallback;

import test.icegalaxy.TestSPApi.SPApiDll.SPApiOrder;
import test.icegalaxy.TestSPApi.SPApiDll.SPApiPrice;
import test.icegalaxy.TestSPApi.SPApiDll.SPApiTrade;


	public interface SPApi extends StdCallLibrary
	{
		
		SPApi INSTANCE = (SPApi) Native.loadLibrary("spapidllm64.dll", SPApi.class);
		
		SPApi SYNC_INSTANCE = (SPApi) Native.synchronizedLibrary(INSTANCE);

		int SPAPI_Initialize();

		int SPAPI_Uninitialize(String userid);
		


		void SPAPI_GetAllTrades(String user_id, String acc_no, ArrayList<SPApiTrade> trades);
		
		int SPAPI_DeleteAllOrders(String user_id, String acc_no);

		int SPAPI_ActivateAllOrders(String user_id, String acc_no);

		int SPAPI_GetAccInfo(String user_id, Structure acc_info);

		int SPAPI_GetPriceByCode(String user_id, byte[] prod_code, SPApiPrice price);

		void SPAPI_SetLoginInfo(String server, int port, String license, String app_id, String userid, String password);

		int SPAPI_Login();

		int SPAPI_Logout(String user_id);

		int SPAPI_AddOrder(Structure order);

		int SPAPI_SubscribePrice(String user_id, byte[] prod_code, int mode);

		void SPAPI_RegisterTradeReport(StdCallCallback tradeReport);

		void SPAPI_RegisterOrderRequestFailed(StdCallCallback orderFail);
		
		void SPAPI_RegisterAccountLogoutReply(LogoutReply logoutReply);

//		void SPAPI_RegisterOrderBeforeSendReport(RegisterOrderB4 orderB4);

		void SPAPI_RegisterApiPriceUpdate(StdCallCallback priceUpdate);

		void SPAPI_RegisterConnectingReply(StdCallCallback conn);
		
		void SPAPI_RegisterOrderReport(StdCallCallback orderReport);


		void SPAPI_RegisterLoginReply(StdCallCallback register);

		void SPAPI_RegisterLoginStatusUpdate(StdCallCallback update);

		public class SPApiAccInfo extends Structure
		{

			public double NAV;
			public double BuyingPower, CashBal, MarginCall, CommodityPL, LockupAmt, CreditLimit, MaxMargin,
					MaxLoanLimit, TradingLimit, RawMargin, IMargin, MMargin, TodayTrans, LoanLimit, TotalFee, LoanToMR,
					LoanToMV;
			// char[] AccName = new char[16];
			public byte[] AccName = new byte[16];
			public byte[] BaseCcy = new byte[4];
			public byte[] MarginClass = new byte[16];
			public byte[] TradeClass = new byte[16];
			public byte[] ClientId = new byte[16];
			public byte[] AEId = new byte[16];
			public byte AccType;
			public byte CtrlLevel;
			public byte Active;
			public byte MarginPeriod;

			@Override
			protected List getFieldOrder()
			{
				return Arrays.asList(new String[]
				{ "NAV", "BuyingPower", "CashBal", "MarginCall", "CommodityPL", "LockupAmt", "CreditLimit", "MaxMargin",
						"MaxLoanLimit", "TradingLimit", "RawMargin", "IMargin", "MMargin", "TodayTrans", "LoanLimit",
						"TotalFee", "LoanToMR", "LoanToMV", "AccName", "BaseCcy", "MarginClass", "TradeClass",
						"ClientId", "AEId", "AccType", "CtrlLevel", "Active", "MarginPeriod" });
			}

		}
		
		public class SPApiTrade extends Structure
		{
			public double RecNo;
			public double Price;
			public double AvgPrice;
			public long TradeNo;
			public long ExtOrderNo;
			public int IntOrderNo;
			public int Qty;
			public int TradeDate;
			public int TradeTime;
			public byte[] AccNo = new byte[16];
			public byte[] ProdCode = new byte[16];
			public byte[] Initiator = new byte[16];
			public byte[] Ref = new byte[16];
			public byte[] Ref2 = new byte[16];
			public byte[] GatewayCode = new byte[16];
			public byte[] ClOrderId = new byte[40];
			public byte BuySell;
			public byte OpenClose;
			public byte Status;
			public byte DecInPrice;
			public double OrderPrice;
			public byte[] TradeRef = new byte[40];
			public int TotalQty;
			public int RemainingQty;
			public int TradedQty;
			public double AvgTradedPrice;

			@Override
			protected List getFieldOrder()
			{
				return Arrays.asList(new String[]
				{ "RecNo", "Price", "AvgPrice", "TradeNo", "ExtOrderNo", "IntOrderNo", "Qty", "TradeDate", "TradeTime",
						"AccNo", "ProdCode", "Initiator", "Ref", "Ref2", "GatewayCode", "ClOrderId", "BuySell",
						"OpenClose", "Status", "DecInPrice", "OrderPrice", "TradeRef", "TotalQty", "RemainingQty",
						"TradedQty", "AvgTradedPrice" });
			}
		}

		

		public class SPApiPrice extends Structure
		{

			// public static class ByReference extends SPApiPrice implements
			// Structure.ByReference{}

			public double[] Bid = new double[20];
			public int[] BidQty = new int[20];
			public int[] BidTicket = new int[20];
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
			public byte[] ProdCode = new byte[16];
			public byte[] ProdName = new byte[40];
			public byte DecInPrice;
			public int ExstateNo;
			public int TradeStateNo;
			public boolean Suspend;
			public int ExpiryYMD;
			public int ContractYMD;
			public int Timestamp;

			@Override
			protected List getFieldOrder()
			{
				return Arrays.asList(new String[]
				{ "Bid", "BidQty", "BidTicket", "Ask", "AskQty", "AskTicket", "Last", "LastQty", "LastTime", "Equil",
						"Open", "High", "Low", "Close", "CloseDate", "TurnoverVol", "TurnoverAmt", "OpenInt",
						"ProdCode", "ProdName", "DecInPrice", "ExstateNo", "TradeStateNo", "Suspend", "ExpiryYMD",
						"ContractYMD", "Timestamp" });
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
			public long ExtOrderNo;
			public int IntOrderNo;
			public int Qty;
			public int TradedQty;
			public int TotalQty;
			public int ValidTime;
			public int SchedTime;
			public int TimeStamp;
			public NativeLong OrderOptions;
			public byte[] AccNo = new byte[16];
			public byte[] ProdCode = new byte[16];
			public byte[] Initiator = new byte[16];
			public byte[] Ref = new byte[16];
			public byte[] Ref2 = new byte[16];
			public byte[] GatewayCode = new byte[16];
			public byte[] ClOrderId = new byte[40];
			public byte BuySell;
			public byte StopType;
			public byte OpenClose;
			public byte CondType;
			public byte OrderType;
			public byte ValidType;
			public byte Status;
			public byte DecInPrice;
			public byte OrderAction;
			public int updateTime;
			public int updateSeqNo;

			@Override
			protected List getFieldOrder()
			{
				return Arrays.asList(new String[]
				{ "Price", "StopLevel", "UpLevel", "UpPrice", "DownLevel", "DownPrice", "ExtOrderNo", "IntOrderNo",
						"Qty", "TradedQty", "TotalQty", "ValidTime", "SchedTime", "TimeStamp", "OrderOptions", "AccNo",
						"ProdCode", "Initiator", "Ref", "Ref2", "GatewayCode", "ClOrderId", "BuySell", "StopType",
						"OpenClose", "CondType", "OrderType", "ValidType", "Status", "DecInPrice", "OrderAction",
						"updateTime", "updateSeqNo" });
			}

		}
		
		
		public interface LogoutReply extends StdCallCallback {
			void apply(String accNo, long ret_code, String ret_msg);
		};
		
		public interface p_SPAPI_AddOrder extends StdCallCallback {
			int apply(SPApiOrder order);
		};
		
		public interface p_SPAPI_Uninitialize extends StdCallCallback {
			void apply();
		};
		
		public interface RegisterOrder extends StdCallCallback
		{
			void invoke(long rec_no, SPApiOrder order);
		}

		public interface RegisterOrderB4 extends StdCallCallback
		{
			void invoke(SPApiOrder order);
		}

		public interface RegisterOrderFail extends StdCallCallback
		{
			void invoke(int action, SPApiOrder order, long err_code, String err_msg);
		}

		public interface RegisterTradeReport extends StdCallCallback
		{
			void invoke(long rec_no, SPApiTrade trade);
		}

		

		public interface RegisterPriceUpdate extends StdCallCallback
		{
			void invoke(SPApiPrice price);
		}

		public interface RegisterConn extends StdCallCallback
		{
			void invoke(long host_type, long con_status);
		}

		public interface RegisterError extends StdCallCallback
		{
			void invoke(short host_id, long link_err);
		}

		public interface RegisterLoginReply extends StdCallCallback
		{
			void printLoginStatus(long ret_code, String ret_msg);
		}

		public interface RegisterLoginStatusUpdate extends StdCallCallback
		{
			void printStatus(long login_status);
		}
		
		
	}
	
	
