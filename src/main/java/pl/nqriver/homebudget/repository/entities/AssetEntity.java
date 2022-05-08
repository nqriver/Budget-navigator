package pl.nqriver.homebudget.repository.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import pl.nqriver.homebudget.enums.AssetCategory;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "assets")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class AssetEntity {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private Instant incomeDate;

    @Enumerated(EnumType.STRING)
    private AssetCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private UserEntity user;
}

