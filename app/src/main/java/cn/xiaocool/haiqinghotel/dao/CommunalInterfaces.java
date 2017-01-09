package cn.xiaocool.haiqinghotel.dao;

/**
 * Created by wzh on 2016/5/7.
 */
public interface CommunalInterfaces {

    //main number 0x0000开始
    int SEND_CODE = 0x0000;//发送验证码
    int BTN_UNTOUCH = 0x0001;//发送验证码按钮不可点击
    int BTN_TOUCH = 0x0002;//发送验证码按钮可点击
    int REGISTER = 0x0003;//注册
    int LOGIN = 0x0004;//登录
    int CHANGE_PASS = 0x0005;//改密码


    //homepage number 0x0100开始
    int ONSALE_LIST = 0x0100;
    int ROOM_DETAILS = 0x0101;
    int RESERVE_ROOM_NOW = 0x0102;
    int CATERING_DETAILS = 0x0103;
    int RESERVE_CATERING = 0x0104;
    int HOME_RESERVE_ROOM = 0x0105;
    int ONPIC_LIST = 0x0106;


    //ecshop number 0x0200开始
    int SHOP_LIST = 0x0200;
    int GOOD_INTRO = 0x0201;
    int LIKE_GOOD = 0x0202;
    int DISLIKE_GOOD = 0x0204;
    int SHOP_BUY_NOW = 0x0203;

    //facility number 0x0300开始
    int FACILITY_LIST = 0x0300;
    int FACILITY_CLICK_LIST = 0x0301;
    int NEW_LIST = 0x0302;
    //mine number  0x0400开始

    int MINE_ROOM_ORDER = 0x0400;
    int MINE_CATERING_ORDER = 0x0401;
    int MINE_SHOP_ORDER = 0x0402;
    int MY_LIKE_GOOD = 0x0403;
    int MY_INFOR = 0x0404;
    int CHANGE_INFO = 0x0405;
    int CANCEL_ORDER = 0x0406;
    int CHANGE_IMGINFO = 0x0407;


    //others  0x0500开始


}
