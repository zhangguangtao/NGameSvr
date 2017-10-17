package com.game.metaxml;

import com.game.annotation.IgnoreDb;

public final  class Account  implements java.io.Serializable {
 		public Account(){}
		/** uin */
 		@IgnoreDb
		private  int  Uin;
		/** uin */
		private  String  RoleName;
		/** uin */
		private  String  Password;
		public int getUin(){
			return this.Uin;
		}
		public void setUin(int Uin){
			this.Uin = Uin;
		}
		public String getRoleName(){
			return this.RoleName;
		}
		public void setRoleName(String RoleName){
			this.RoleName = RoleName;
		}
		public String getPassword(){
			return this.Password;
		}
		public void setPassword(String Password){
			this.Password = Password;
		}

	}