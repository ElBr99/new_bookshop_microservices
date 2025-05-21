package ru.ifellow.ebredichina.bookservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ifellow.ebredichina.bookservice.model.OfflineBookShop;


import java.util.UUID;

@Repository
public interface OfflineBookShopRepository extends JpaRepository<OfflineBookShop, UUID> {

    @Query("select case when exists ( select ob from OfflineBookShop ob " +
            "join ob.books bi " +
            "join bi.book b " +
            "where b.id = :bookId and bi.amount >= :amount and ob.id=:bookShopId ) THEN TRUE ELSE FALSE END ")
    boolean isEnoughBooks(@Param("bookId") UUID bookId, @Param("amount") int amount, @Param("bookShopId") UUID bookShopId);


}

