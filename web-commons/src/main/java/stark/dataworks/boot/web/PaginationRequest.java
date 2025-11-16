package stark.dataworks.boot.web;

import lombok.Data;

@Data
public class PaginationRequest
{
    private long current;
    private long pageSize;

    private long limit;     // 查询条数
    private long offset;    // 偏移量

    public void calculateLimitOffset()
    {
        if (current < 1)
            current = 1;

        if (pageSize < 1)
            pageSize = 10;

        limit = pageSize;
        offset = (current - 1) * pageSize;
    }
}
