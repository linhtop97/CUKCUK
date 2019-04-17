package vn.com.misa.cukcuklite.data.dao;

/**
 * Mô tả các thuộc tính của mỗi item báo cáo thống kê theo từng ngày
 * @created_by nblinh on 4/17/2019
*/
public class ReportDayItem {

    private String dishId;

    private int quantity;

    private int totalMoney;

    private String dateCreated;

    public ReportDayItem() {

    }

    public ReportDayItem(Builder builder) {
        this.dishId = builder.dishId;
        this.quantity = builder.quantity;
        this.totalMoney = builder.totalMoney;
        this.dateCreated = builder.dateCreated;
    }

    public static class Builder{
        private String dishId;
        private int quantity;
        private int totalMoney;
        private String dateCreated;

        public ReportDayItem build(){
            return new ReportDayItem(this);
        }

        public Builder setDateCreated(String dateCreated) {
            this.dateCreated = dateCreated;
            return this;
        }

        public Builder setDishId(String dishId) {
            this.dishId = dishId;
            return this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setTotalMoney(int totalMoney) {
            this.totalMoney = totalMoney;
            return this;
        }
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }
}
