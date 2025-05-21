package ru.ifellow.ebredichina.bookservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ifellow.ebredichina.bookservice.model.BookStorage;
import ru.ifellow.ebredichina.bookservice.projections.BookStorageProjection;


import java.util.List;
import java.util.UUID;

@Repository
public interface BookStorageRepository extends JpaRepository<BookStorage, UUID> {

    @Query("select case when exists ( select bs from BookStorage bs " +
            "join bs.books bi " +
            "join bi.book b " +
            "where b.id = :bookId and bi.amount >= :amount and bs.id=:bookStorageId ) THEN TRUE ELSE FALSE END ")
    boolean isEnoughBooks(@Param("bookId") UUID bookId, @Param("amount") int amount, @Param("bookStorageId") UUID bookStorageId);


    @Query(value = " select CAST(bs.id as varchar(255)) as storageId, CAST(bi.id as varchar (255)) as bookInfoId, CAST(b.id as varchar(255)) as bookId, bi.amount as amount " +
            "from entities.book_storage bs " +
            "join entities.book_info bi " +
            "on bs.id = bi.storage_id " +
            "join entities.books b on bi.book_id = b.id " +
            "where b.id in (:bookInfoIds)", nativeQuery = true)
    List<BookStorageProjection> findAllStoragesWithBooks(@Param("bookInfoIds") List<UUID> bookInfoIds);


}


