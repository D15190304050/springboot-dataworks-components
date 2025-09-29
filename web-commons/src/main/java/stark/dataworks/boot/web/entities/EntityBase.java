package stark.dataworks.boot.web.entities;

import lombok.Data;
import stark.coderaider.fluentschema.commons.annotations.AutoIncrement;
import stark.coderaider.fluentschema.commons.annotations.Column;
import stark.coderaider.fluentschema.commons.annotations.PrimaryKey;

import java.util.Date;

@Data
public abstract class EntityBase
{
    /**
     * ID of the record.
     */
    @PrimaryKey
    @AutoIncrement
    private long id;

    /**
     * ID of the creator of the record.
     */
    private long creatorId;

    /**
     * Creation time of the record.
     */
    @Column(defaultValue = "NOW()")
    private Date creationTime;

    /**
     * ID of the user who modifies the record.
     */
    private long modifierId;

    /**
     * Last modification time of the record.
     */
    @Column(defaultValue = "NOW()", onUpdate = "NOW()")
    private Date modificationTime;
}
