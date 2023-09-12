package com.example.airtrip.domain.entity.entityformvc;

import com.example.airtrip.domain.entity.BasicEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderM extends BasicEntity {
    @ManyToOne
    private ConflictCountry conflictCountry;
    private String companyName;
    @Lob
    @Column(length = 2147483647)
    private byte[] imageOfCompany;
    @ManyToMany(mappedBy = "orders", cascade ={CascadeType.MERGE})
    private List<Product> products;

    public void addProduct(Product product){
        if(products==null || products.isEmpty()){
            products = new ArrayList<>();
        }
        products.add(product);
    }
    public void deleteProduct(Product product){
        if(products!=null && products.contains(product)){
           products.remove(product);
        }
    }
}
