package vn.com.misa.cukcuklite.data.bill;

import java.util.List;

import vn.com.misa.cukcuklite.data.models.BillDetail;

public interface IBillDataSource {

    List<BillDetail> initNewBillDetailList(String BillId);

}
