package vn.com.misa.cukcuklite.data.dao;

/**
 * Mô tả các thuộc tính của mỗi item báo cáo thống kê tổng quan
 * @created_by nblinh on 4/17/2019
*/
public class ReportOverviewItem {
    private int id ;

    private float totalMoney;

    public ReportOverviewItem(Builder builder) {
        id = builder.id;
        totalMoney = builder.totalMoney;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public static class Builder{
        private int id ;


        private float totalMoney;

        public ReportOverviewItem build(){
            return new ReportOverviewItem(this);
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }


        public Builder setTotalMoney(float totalMoney) {
            this.totalMoney = totalMoney;
            return this;
        }

    }


}
