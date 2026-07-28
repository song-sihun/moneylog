package com.likelion.moneylogbackend.domain.translog;


import com.likelion.moneylogbackend.domain.Category;
import com.likelion.moneylogbackend.domain.Type;
import com.likelion.moneylogbackend.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Entity
@Table(name = "trans_logs")
@NoArgsConstructor
@AllArgsConstructor
public class TransLog {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer amount;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String description;

    private LocalDate date;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void update(TransLogRequestDto req) {
        this.type = req.type();
        this.amount = req.amount();
        this.category = req.category();
        this.description = req.description();
        this.date = req.date();
        this.updatedAt = LocalDateTime.now();
    }
}
