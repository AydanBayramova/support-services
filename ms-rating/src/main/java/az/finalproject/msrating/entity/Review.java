package az.finalproject.msrating.entity;

import az.finalproject.msrating.enums.RatingTarget;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID orderId;
    private UUID raterId;
    private UUID targetId;

    @Enumerated(EnumType.STRING)
    private RatingTarget targetType;

    @Column(nullable = false)
    private Integer score;

    private String comment;

    private LocalDateTime createdAt;
}
