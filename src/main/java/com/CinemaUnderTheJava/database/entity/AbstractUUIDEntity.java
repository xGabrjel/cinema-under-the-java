package com.cinemaUnderTheJava.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

import java.util.UUID;

@Getter
@ToString
@MappedSuperclass
@FieldNameConstants
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class AbstractUUIDEntity extends AbstractEntity {

    @EqualsAndHashCode.Include
    @Column(name = "uuid", unique = true, nullable = false)
    protected UUID uuid = UUID.randomUUID();
}
