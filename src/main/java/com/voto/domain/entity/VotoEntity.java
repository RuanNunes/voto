package com.voto.domain.entity; 
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.CreationTimestamp;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class VotoEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(readOnly = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public UUID id;

    @Column(length = 100, nullable = false, unique = true)
    public String title;

    @Column(nullable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Schema(readOnly = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Date createdAt;

    public static VotoEntity findById(UUID id) {
        return find("id", id).firstResult();
    }

    public static Optional<VotoEntity> findByIdOptional(UUID id) {
        return find("id", id).firstResultOptional();
    }

    public static boolean exists(VotoEntity entity) {
        return find("title", entity.title).count() > 0;
    }

    public static boolean exists(VotoEntity entity, UUID id) {
        return find("title = ?1 AND id <> ?2", entity.title, id).count() > 0;
    }
}
