package com.example.airtrip.domain.entity.entityformvc;

import com.example.airtrip.domain.entity.BasicEntity;
import com.example.airtrip.domain.enums.TypeOfWeapon;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Product extends BasicEntity {
    private String name;
    @Enumerated(value = EnumType.STRING)
    private TypeOfWeapon typeOfWeapon;
    @Lob
    @Column(length = 2147483647)
    private byte[] image;

    @ManyToMany(cascade ={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinTable( name = "product_order"
            ,joinColumns = @JoinColumn(name = "product_id")
            ,inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<OrderM> orders;

    public void addOrder(OrderM orderM){
        if(orders==null || orders.isEmpty()){
            orders = new ArrayList<>();
        }
        orders.add(orderM);
    }
    public void deleteOrder(OrderM orderM){
        if(orders!=null && orders.contains(orderM)){
            orders.remove(orderM);
        }
    }
}
