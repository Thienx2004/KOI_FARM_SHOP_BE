package com.group2.KoiFarmShop.repository;

import com.group2.KoiFarmShop.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    public boolean existsByEmail(String email);
}
