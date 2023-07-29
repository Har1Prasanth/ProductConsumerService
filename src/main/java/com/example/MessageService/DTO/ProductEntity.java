package com.example.MessageService.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {



    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long productId;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    private long price;

    private long quantity;

}
