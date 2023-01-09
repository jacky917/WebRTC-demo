package com.demo.webrtc.constant;

/**
 * 常量类
 */
public final class Constants {

	/**********************通用代码 start***********************/
	//密碼錯誤
	public static final Integer PASSWORD_CHECK_INVALID = 40001;

	//驗證成功
	public static final Integer TOKEN_CHECK_SUCCESS = 20000;

	//伺服器錯誤
	public static final Integer SERVER_ERROR = 50000;
	//參數不完整
	public static final Integer PARAMETERS_MISSING = 50002;

	//非法token
	public static final Integer TOKEN_CHECK_ILLEFALITY_ERROR = 50008;
	//其他客戶端登入
	public static final Integer TOKEN_CHECK_OTHER_LOGIN = 50012;
	//token 過期
	public static final Integer TOKEN_CHECK_STALE_DATED = 50014;

	/**********************通用代碼 end***********************/

	/**
	 * 過期時間
	 */
	public static class ExpireTime {
		private ExpireTime() {
		}
		public static final int TEN_SEC =  10;//10秒
		public static final int THIRTY_SEC =  30;//30秒
		public static final int ONE_MINUTE =  60;//一分鐘
		public static final int THIRTY_MINUTES =  60 * 30;//30分鐘
		public static final int ONE_HOUR = 60 * 60;//一小時
		public static final int THREE_HOURS = 60 * 60 * 3;//三小時
		public static final int TWELVE_HOURS =  60 * 60 * 12;//十二小時，單位s
		public static final int ONE_DAY = 60 * 60 * 24;//二十四小時
	}
}
