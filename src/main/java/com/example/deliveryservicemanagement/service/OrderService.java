package com.example.deliveryservicemanagement.service;

import com.example.deliveryservicemanagement.dao.CustomerDao;
import com.example.deliveryservicemanagement.dao.CustomerOrderDao;
import com.example.deliveryservicemanagement.ds.Customer;
import com.example.deliveryservicemanagement.ds.CustomerOrder;
import com.example.deliveryservicemanagement.ds.DeliveryStatus;
import com.example.deliveryservicemanagement.ds.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;

@Service
public class OrderService {
    @Autowired
    private CustomerOrderDao customerOrderRepository;
    @Autowired
    private CustomerDao customerDao;

    //1
    public void saveOrder(CustomerOrder customerOrder, Customer customer){
        customerOrderRepository.save(customerOrder);
        customerDao.save(customer);
    }

    //4
    public void markOrderAsDelivered(CustomerOrder customerOrder) {
        customerOrder.setDeliveryStatus(DeliveryStatus.DELIVERED);
        updateSuccessfulDeliveriesCount(customerOrder.getStaff());
        customerOrderRepository.save(customerOrder);
    }

    //3
    private void updateSuccessfulDeliveriesCount(Staff staff) {
        LocalDate currentDate = LocalDate.now();
        LocalDate lastDeliveryDate = staff.getLastSuccessfulDeliveryDate();

        if (lastDeliveryDate == null || !isSameMonth(currentDate, lastDeliveryDate)) {
            // If it's a new month or the first successful delivery, reset the successfulDeliveries count
            staff.setSuccessfulDeliveries(1);
        } else {
            // If it's the same month, increment the successfulDeliveries count
            staff.setSuccessfulDeliveries(staff.getSuccessfulDeliveries() + 1);
        }
        staff.setLastSuccessfulDeliveryDate(currentDate);
    }

    //2
    private boolean isSameMonth(LocalDate date1, LocalDate date2) {
        return YearMonth.from(date1).equals(YearMonth.from(date2));
    }

    //5
    public void resetSuccessfulDeliveriesForMonth(Staff staff) {
        LocalDate currentDate = LocalDate.now();
        LocalDate lastDeliveryDate = staff.getLastSuccessfulDeliveryDate();
        if (lastDeliveryDate != null && !isSameMonth(currentDate, lastDeliveryDate)) {
            // If it's a new month, reset the successfulDeliveries count
            staff.setSuccessfulDeliveries(0);
        }
    }

}
