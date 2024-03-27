package stark.dataworks.boot.web;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedData<T>
{
    private long pageSize;
    private long pageCount;
    private long total;
    private long current;
    private List<T> data;
}
