package unitTests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.ifellow.jschool.ebredichina.projections.BookStorageProjection;

@RequiredArgsConstructor
@Builder
@Data
@AllArgsConstructor
public class BookStorageProjectionImpl implements BookStorageProjection {

    private String storageId;
    private String bookInfoId;
    private String bookId;
    private Integer amount;

    @Override
    public String getStorageId() {
        return storageId;
    }

    @Override
    public String getBookInfoId() {
        return bookInfoId;
    }

    @Override
    public String getBookId() {
        return bookId;
    }

    @Override
    public Integer getAmount() {
        return amount;
    }
}
