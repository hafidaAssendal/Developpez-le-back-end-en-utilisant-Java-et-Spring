package com.chatop.rental.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "RENTALS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rental {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private BigDecimal surface;
  private BigDecimal price;
  private String picture;
  @Column(length = 2000)
  private String description;
  @CreationTimestamp
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
  // Relation vers User
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id", nullable = false)
  private User owner;
  //@Column(name = "owner_id", nullable = false)
    //  private Integer ownerId;*/


}
