package my.rest.restController.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "auth")
public class Auth {
    @Id
    private long user_id;
    private UUID accessToken;
    private OffsetDateTime accessTokenExpire;
    private UUID refreshToken;
    private OffsetDateTime refreshTokenExpire;
}
