package com.xht.android.serverhelp.net;

public class BaseApi {
	public static final String BZ_ITEMS_URL = "http://www.xiaohoutai.com.cn:8888/XHT/homeOrderDetailController/LoadCertificateDetail";
	public static final String BZ_ProsInit_URL = "http://www.xiaohoutai.com.cn:8888/XHT/homeOrderDetailController/LoadCertificateDetailJingDu";
	public static final String BZ_PIC_UPLOAD_Url ="";

    //首页数据
    public static final String URL_Main = "http://www.xiaohoutai.com.cn:8888/XHT/homeOrderDetailController/TogetherOrderNum";
    //任务池
    public static final String URL_TaskBar = "http://www.xiaohoutai.com.cn:8888/XHT/homeOrderDetailController/loadMissonDetail";
    //接受任务
    public static final String Task_ITEM_POST_URL = "http://www.xiaohoutai.com.cn:8888/XHT/homeOrderDetailController/saveLoadMissonDetail";
    //访问个人绩效
    public static final String PERSONAL_POST_URL ="http://www.xiaohoutai.com.cn:8888/XHT/homeOrderDetailController/IndividualOrderDetail";
    //访问预警数据
    public static final String WARNING_POST_URL = "http://www.xiaohoutai.com.cn:8888/XHT/homeOrderDetailController/LoadWarmingDetail";
    //通讯录客户
    public static final String CONTACTSPOST_URL = "http://www.xiaohoutai.com.cn:8888/XHT/homeOrderDetailController/AddressBookCustomerDetail";
    //通讯录内部员工
    public static final String CONTACTS_GONGSI_URL = "http://www.xiaohoutai.com.cn:8888/XHT/homeOrderDetailController/AddressBookEmployeeDetail";
}
