package com.tacoloco.order.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Price_Chart")
public class PriceChart {

    @Id
    @Column(name = "Item_Name")
    private String itemName;

    @Column(name = "Item_Price")
    private Double itemPrice;
}
