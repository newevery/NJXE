package com.xuenen.njxe.constant;

/**
 * Created by Administrator on 2016/12/13 0013.
 */

public class C {

    public interface constant {
        /**
         * SharedPreference保存用户信息的文件名称
         */
        String LOGIN_SAVE_USER_INFO_FILE_NAME = "login";
        /**
         * SharedPreference保存用户信息的用户名的key
         */
        String LOGIN_USER_NAME = "name";
        /**
         * SharedPreference保存用户信息的密码的key
         */
        String LOGIN_USER_PASS = "pass";
        /**
         * SharedPreference保存用户信息的密码是否记忆的key
         */
        String LOGIN_SAVE_PASS = "savePass";
        /**
         * SharedPreference保存用户信息的自动登录的key
         */
        String LOGIN_AUTO_LOGIN = "autoLogin";
        /**
         * 向服务器提交的用户名key
         */
        String POST_USER_NAME = "name";
        /**
         * 向服务器提交的密码key
         */
        String POST_USER_PASS = "pass";
        /**
         * 向服务器提交的操作类别key
         */
        String POST_OPERATE_CODE = "opCode";

        /**
         * 向服务器提交的id数据的key
         */
        String POST_PARAM_ID = "id";
        /**
         * 向服务器提交的数据key
         */
        String POST_PARAM_COMMON = "data";
    }

    public interface URL {
        String BASEURL = "http://192.168.3.30:8080/jyyy/mobile/";
        String loginURL = BASEURL + "login.do";
        String addURL = BASEURL + "add.do";
        String existURL = BASEURL + "exist.do";
        String findDicWithParaURL = BASEURL + "findDicWithPara.do";
        String findDicByDalymdURL = BASEURL + "findDicByDalymd.do";
        String finDicByDmdwURL = BASEURL + "findDicByDmdw.do";
        String queryReborrowURL = BASEURL + "queryReborrow.do";
        String findCommentsByTableNameURL = BASEURL + "findCommentsByTableName.do";
        String findOneCheckin = BASEURL + "findOneCheckin.do";
        String addCheckin = BASEURL + "addCheckin.do";
    }
}
