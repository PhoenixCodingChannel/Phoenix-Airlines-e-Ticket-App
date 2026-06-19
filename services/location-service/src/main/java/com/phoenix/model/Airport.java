package com.phoenix.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.phoenix.embeddable.Address;
import com.phoenix.embeddable.GeoCode;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String iataCode;

    @Column(nullable = false)
    private String name;

    @Embedded
    private Address address;

    @Embedded
    private GeoCode geoCode;

    @Column(name = "time_zone_id",length = 50)
    private String timeZone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private City city;

    @JsonIgnore
    @Transient
    public String getDetailedName() {
        if(city != null && city.getCityCode() != null) {
            return name.toUpperCase() + "/" + city.getCityCode();
        }
        return name.toUpperCase();
    }
}
