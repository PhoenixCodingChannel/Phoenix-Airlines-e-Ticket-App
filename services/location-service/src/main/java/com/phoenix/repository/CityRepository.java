package com.phoenix.repository;

import com.phoenix.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CityRepository extends JpaRepository<City, Long> {

    boolean existsByCityCode(String cityCode);

    boolean existsByCityCodeAndIdNot(String cityCode, Long id);

    Page<City> findByCountryCodeIgnoreCase(String countryCode, Pageable pageable);

    @Query("""
             Select c from City c
              Where lower(c.name) like lower(concat('%',:keyword,'%'))
              Or lower(c.cityCode) like lower(concat('%',:keyword,'%'))
              Or lower(c.countryCode) like lower(concat('%',:keyword,'%'))
              Or lower(c.countryName) like lower(concat('%',:keyword,'%'))
              Or lower(c.regionCode) like lower(concat('%',:keyword,'%'))
            """)
    Page<City> searchByKeyword(String keyword, Pageable pageable);
}
