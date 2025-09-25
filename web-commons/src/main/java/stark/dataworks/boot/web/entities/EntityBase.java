package stark.dataworks.boot.web.entities;

import lombok.Data;

import java.util.Date;

@Data
public abstract class EntityBase
{
    /**
     * ID of the record.
     */
    private long id;

    /**
     * ID of the creator of the record.
     */
    private long creatorId;

    /**
     * Creation time of the record.
     */
    private Date creationTime;

    /**
     * ID of the user who modifies the record.
     */
    private long modifierId;

    /**
     * Last modification time of the record.
     */
    private Date modificationTime;
}
