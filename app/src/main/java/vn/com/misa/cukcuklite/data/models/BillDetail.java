package vn.com.misa.cukcuklite.data.models;

/**
 * Lớp Hóa đơn chi tiết
 * Created_by Nguyễn Bá Linh on 11/04/2019
 */
public class BillDetail {

    private String BillDetailId;

    private String BillId;

    private String DishId;

    private int Quantity;

    private int TotalMoney;

    private String name;

    public BillDetail(Builder builder) {
        BillDetailId = builder.BillDetailId;
        BillId = builder.BillId;
        DishId = builder.DishId;
        Quantity = builder.Quantity;
        TotalMoney = builder.TotalMoney;
        name = builder.name;
    }

    public String getBillDetailId() {
        return BillDetailId;
    }

    public void setBillDetailId(String billDetailId) {
        BillDetailId = billDetailId;
    }

    public String getBillId() {
        return BillId;
    }

    public void setBillId(String billId) {
        BillId = billId;
    }

    public String getDishId() {
        return DishId;
    }

    public void setDishId(String dishId) {
        DishId = dishId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getTotalMoney() {
        return TotalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        TotalMoney = totalMoney;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Builder {
        private String BillDetailId;
        private String BillId;
        private String DishId;
        private int Quantity;
        private int TotalMoney;
        private String name;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setBillDetailId(String billDetailId) {
            BillDetailId = billDetailId;
            return this;
        }

        public Builder setBillId(String billId) {
            BillId = billId;
            return this;
        }

        public Builder setDishId(String dishId) {
            DishId = dishId;
            return this;
        }

        public Builder setQuantity(int quantity) {
            Quantity = quantity;
            return this;
        }

        public Builder setTotalMoney(int totalMoney) {
            TotalMoney = totalMoney;
            return this;
        }

        public BillDetail build() {
            return new BillDetail(this);
        }
    }
}
