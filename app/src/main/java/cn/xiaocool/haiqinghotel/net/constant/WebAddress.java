package cn.xiaocool.haiqinghotel.net.constant;

/**
 * Created by wzh on 2016/5/7.
 */
public interface WebAddress extends NetBaseConstant {
    /**
     * 发送验证码后缀
     */
    String SEND_CODE = NET_BASE_PREFIX + "a=SendMobileCode";
    /**
     * 登录后缀
     */
    String LOGIN = NET_BASE_PREFIX + "a=applogin";
    /**
     * 注册后缀
     */
    String REGISTER = NET_BASE_PREFIX + "a=AppRegister";
    /**
     * 修改密码
     */
    String CHANGE_PASS = NET_BASE_PREFIX + "a=forgetpwd";
    /**
     * 修改个人资料
     */
    String CHANGE_INFO = NET_BASE_PREFIX + "a=savepersonalinfo";
    /**
     * 获取个人信息
     */
    String MY_INFOR = NET_BASE_PREFIX + "a=getuserinfo";
    /**
     * 首页促销列表
     */
    String ONSALE_LIST = NET_BASE_PREFIX + "a=getpromotionlist";
    /**
     * 首页幻灯片列表
     */
    String ONPIC_LIST = NET_BASE_PREFIX + "a=getslidelist";
    /**
     * 房间详情
     */
    String ROOM_DETAILS = NET_BASE_PREFIX + "a=showbedroominfo";
    /**
     * 立即预定房间
     */
    String RESERVE_ROOM_NOW = NET_BASE_PREFIX + "a=bookingroom";
    /**
     * 获取餐饮信息
     */
    String CATERING_DETAILS = NET_BASE_PREFIX + "a=showcateringinfo";
    /**
     * 预订餐饮
     */
    String RESERVE_CATERING = NET_BASE_PREFIX + "a=bookingcatering";

    /**
     * 获取首页预订房间列表
     */
    String HOME_RESERVE_ROOM = NET_BASE_PREFIX + "a=getbedroomlist";

    /**
     * 获取我的房间订单
     */
    String MINE_ROOM_ORDER = NET_BASE_PREFIX + "a=getbookingorderlist";
    /**
     * 获取我的餐饮订单
     */
    String MINE_CATERING_ORDER = NET_BASE_PREFIX + "a=getcateringorderlist";
    /**
     * 获取我的餐饮订单
     */
    String MINE_SHOP_ORDER = NET_BASE_PREFIX + "a=getshoppingorderlist";
    /**
     * 获取设施列表
     */
    String FACILITY_LIST = NET_BASE_PREFIX + "a=getFacilitytypelist";
    /**
     * 获取资讯列表
     */
    String NEWS_LIST = NET_BASE_PREFIX + "a=getNewslist";
    /**
     * 获取设施列表
     */
    String FACILITY_CLICK_LIST = NET_BASE_PREFIX + "a=getFacilitylist";
    /**
     * 获取商城列表
     */
    String SHOP_LIST = NET_BASE_PREFIX + "a=getshoppinglist";
    /**
     * 获取商品详情
     */
    String GOOD_INTRO = NET_BASE_PREFIX + "a=showshoppinginfo";
    /**
     * 收藏商品
     */
    String LIKE_GOOD = NET_BASE_PREFIX + "a=addfavorite";
    /**
     * 取消收藏商品
     */
    String DISLIKE_GOOD = NET_BASE_PREFIX + "a=cancelfavorite";
    /**
     * 立即购买商品
     */
    String SHOP_BUY_NOW = NET_BASE_PREFIX + "a=bookingshopping";
    /**
     * 获取收藏列表
     */
    String MY_LIKE_GOOD= NET_BASE_PREFIX + "a=getfavoritelist";

    /*
    *取消订单
    */
    String CANCEL_ORDER = NET_BASE_PREFIX + "a=cancelorder";


    /*
    *上传头像
    */
    String UPLOADAVATAR ="http://hq.xiaocool.net/index.php?g=apps&m=index&a=uploadavatar";
    String UPLOADAVATAR1 ="http://parking.xiaocool.net/index.php?g=apps&m=index&a=WriteMicroblog_upload";
}
