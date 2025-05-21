package ru.ifellow.ebredichina.bookservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ifellow.ebredichina.bookservice.model.FavouriteBook;
import ru.ifellow.ebredichina.bookservice.model.FavouriteBookId;


import java.util.List;
import java.util.UUID;

@Repository
public interface FavouriteBookRepository extends JpaRepository<FavouriteBook, FavouriteBookId> {

    @Query("select fb from FavouriteBook fb JOIN FETCH fb.bookInfoFavourite bi where fb.userId = :customerId")
    List<FavouriteBook> findFavouriteBooksByUserId(@Param("customerId")UUID customerId);

}
