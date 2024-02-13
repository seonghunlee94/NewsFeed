package org.example.prepurchase.domain.follow.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
@Setter
public class Follows {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String toUser;

    @NotNull
    private String fromUser;

    private LocalDateTime createTime;

    // 기본 생성자
    public Follows() {
    }

    // 매개변수를 받는 생성자
    public Follows(String toUser, String fromUser, LocalDateTime createTime) {
        this.toUser = toUser;
        this.fromUser = fromUser;
        this.createTime = createTime;
    }


    // Getter와 Setter 메서드 추가
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = this.createTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }


}
