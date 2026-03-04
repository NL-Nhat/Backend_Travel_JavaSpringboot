package com.example.travel.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Table(name = "DiemDen")
public class DestinationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maDiemDen")
    private Integer id;

    @Column(name = "tenDiemDen", nullable = false)
    private String destinationName;

    @Column(name = "thanhPho", nullable = false)
    private String city;

    @Column(name = "moTa")
    private String describe;

    //@ManyToMany và @OnteToMany có fetchType mặc định là LAZY
    @OneToMany(mappedBy = "destination")
    private List<TourEntity> tours = new ArrayList<TourEntity>(); 
}
