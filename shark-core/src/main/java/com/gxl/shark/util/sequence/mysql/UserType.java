package com.gxl.shark.util.sequence.mysql;
/*
 * Copyright 2015-2101 gaoxianglong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
//package com.gxl.kratos.utils.sequence;
//
///**
// * 用户类型
// * 
// * @author EX-GAOXIANGLONG001
// */
//public enum UserType {
//	/* 用户类别:1、普通用户类别 , 
//	 * 2、群用户类别, 3、公共账号类别 、
//	 * 4, 群ID、 5, 短信   6,好友 ，
//	 *  11,视频通话 ,12业务员群组,
//	 *  13业务员用户,14平安员工群,
//	 *  15MO预置用户
//	 *  16 离线消息SEQ_ID
//	 *  */
//	USER("01"), GROUP("02"), PUBLIC_ACCOUNT("03"), GROUP_ID("04"),SMS_ID("05"),ROSTER_ID("06"),
//	VIDEOTALK_SESSION("11"),BIZ_ROOM("12"),BIZ_USERNAME("13"),PA_EMP("14"),MO_USER("15"),
//	OFFLINEMESSAGE_ID("16"),QAMESSAGE("18");
//
//	public String type;
//
//	UserType(String type) {
//		this.type = type;
//	}
//	
//	public static UserType getUserTypeByType(String type){
//		if (type.equals(UserType.USER.type)){
//			return UserType.USER;
//		} else if (type.equals(UserType.GROUP.type)){
//			return UserType.GROUP;
//		} else if (type.equals(UserType.PUBLIC_ACCOUNT.type)){
//			return UserType.PUBLIC_ACCOUNT;
//		} else if (type.equals(UserType.GROUP_ID.type)){
//			return UserType.GROUP_ID;
//		} else if (type.equals(UserType.SMS_ID.type)){
//			return UserType.SMS_ID;
//		} else if (type.equals(UserType.ROSTER_ID.type)){
//			return UserType.ROSTER_ID;
//		} else if (type.equals(UserType.VIDEOTALK_SESSION.type)){
//			return UserType.VIDEOTALK_SESSION;
//		} else if (type.equals(UserType.BIZ_ROOM.type)){
//			return UserType.BIZ_ROOM;
//		} else if (type.equals(UserType.BIZ_USERNAME.type)){
//			return UserType.BIZ_USERNAME;
//		} else if (type.equals(UserType.PA_EMP.type)){
//			return UserType.PA_EMP;
//		} else if (type.equals(UserType.MO_USER.type)){
//			return UserType.MO_USER;
//		}else if (type.equals(UserType.QAMESSAGE.type)){
//			return UserType.QAMESSAGE;
//		}
//		return UserType.USER;
//	}
//	
//	
//}