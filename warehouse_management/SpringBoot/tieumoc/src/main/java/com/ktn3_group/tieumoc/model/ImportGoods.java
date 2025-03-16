package com.ktn3_group.tieumoc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ktn3_group.tieumoc.util.SecurityUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "import_goods")
@Getter
@Setter
public class ImportGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    private String description;

    private boolean active;

    private Instant createdAt;
    private String createdBy;

    @OneToMany(mappedBy = "importGoods", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ImportGoodsDetail> importGoodsDetails;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @PrePersist
    public void handleBeforeCreate() {
        this.createdBy = SecurityUtil.getCurrentUserLogin().isPresent() == true
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";

        this.createdAt = Instant.now();
    }
}
