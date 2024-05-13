package com.eshop.model.product.colors.colors;

import com.eshop.model.product.colors.group.ColorsGroupEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "colors")
public class ColorsEntity {
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

    @JoinColumn(name = "color_group_id")
    @ManyToOne
    private ColorsGroupEntity colorsGroup;

    @Column(name = "color_group_id")
    private UUID colorGroupId;
}
