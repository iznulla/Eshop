package com.eshop.model.product.colors.group;

import com.eshop.model.product.colors.colors.ColorsEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "colors_group")
public class ColorsGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "rgb_code")
    private String rgbCode;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(name = "deleted_at")
    private String deletedAt;

    @OneToMany(mappedBy = "colorsGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ColorsEntity> colors = new ArrayList<>();
}
