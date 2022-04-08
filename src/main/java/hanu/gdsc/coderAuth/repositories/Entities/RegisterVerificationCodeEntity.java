package hanu.gdsc.coderAuth.repositories.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import hanu.gdsc.coderAuth.domains.RegisterVerificationCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Entity
@Table(name = "coder_auth_register_verification_code")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RegisterVerificationCodeEntity {
    @Id
    @Column(columnDefinition = "VARCHAR(30)")
    private String id;
    private String code;
    private String expireAt;

    public static RegisterVerificationCodeEntity toEntity(RegisterVerificationCode registerVerificationCode) {
        return RegisterVerificationCodeEntity.builder()
            .id(registerVerificationCode.getId().toString())
            .code(registerVerificationCode.getCode())
            .expireAt(registerVerificationCode.getExpiredAt().toString())
            .build()
        ;
    }

    public RegisterVerificationCode toDomain() {
        return new RegisterVerificationCode(
            new hanu.gdsc.share.domains.Id(id), 
            code, 
            new hanu.gdsc.share.domains.DateTime(expireAt));
    }
}