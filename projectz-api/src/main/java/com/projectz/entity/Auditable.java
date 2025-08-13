package com.projectz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class Auditable {

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modified_by")
    private Long modifiedBy;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;
}
