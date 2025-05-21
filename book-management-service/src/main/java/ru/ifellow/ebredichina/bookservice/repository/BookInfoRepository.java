package ru.ifellow.ebredichina.bookservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ifellow.ebredichina.bookservice.model.BookInfo;


import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface BookInfoRepository extends JpaRepository<BookInfo, UUID> {

    @Query("select bi FROM BookInfo bi " +
            "JOIN FETCH bi.book b " +
            "WHERE" +
            "    (:bookTitle IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :bookTitle, '%'))) " +
            "    AND " +
            "    (:bookAuthor IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :bookAuthor, '%')))")
    List<BookInfo> findBookByAuthorOrTitle(@Param("bookTitle") String bookTitle, @Param("bookAuthor") String bookAuthor);


    @Query("select bi from BookInfo bi join bi.book b where b.author = :author")
    List<BookInfo> findByAuthor(@Param("author") String author);


    @Query("select bi FROM BookInfo bi JOIN bi.book b group by b.genre")
    Map<String, List<BookInfo>> findBooksByGenre();

}
