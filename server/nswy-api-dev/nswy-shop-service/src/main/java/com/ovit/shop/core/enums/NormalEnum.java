package com.ovit.shop.core.enums;

/**
 * Created by wangxy on 2018-6-12.
 */
public class NormalEnum {


    public enum ShopAccountType {
        Buy("买"),
        Sell( "卖");
        private final String desc;

        private ShopAccountType( String desc) {
            this.desc = desc;
        }

        public String GetDesc() {
            return desc;

        }
    }

    public enum OrderOperateType {
        Cancel(1, "取消"),
        Retreat(2, "退货");


        private int code;
        private final String desc;

        private OrderOperateType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        public int GetCode() {
            return code;
        }

        public String GetDesc() {
            return desc;

        }
    }

    public enum InvoiceType {
        None(0,"无发票"),
        Personal(1, "个人发票"),
        Tax(2, "增值发票");


        private int code;
        private final String desc;

        private InvoiceType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        public int GetCode() {
            return code;
        }

        public String GetDesc() {
            return desc;

        }
    }

    public enum OrderStatusType {
        SendFor(1, "下单"),
        RevisePrice(2, "卖家修改价格"),
        Pay(3, "支付"),
        SendOut (4, "发货"),
        TakeOver (5, "确认收货"),
        BuyerComment(6, "买家评价"),
        SellerComment(7, "卖家评价"),
        BuyerCancel(10, "买家取消"),
        CancelOver(11, "卖家同意取消"),
        SellerCancel(12, "卖家取消"),
        BuyerRetreat(13, "买家退货"),
        RetreatOver(14, "确认退货"),
        Paying(15, "待支付尾款"),
        ImmediatelyCancel(16, "已取消"),
        NotPayCancel(17, "未支付已取消"),
        RetreatCancel(18, "退货拒绝"),
        CancelCancel(19, "取消订单拒绝");

        private int code;
        private final String desc;

        private OrderStatusType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int GetCode() {
            return code;
        }

        public String GetDesc() {
            return desc;

        }
    }
}

