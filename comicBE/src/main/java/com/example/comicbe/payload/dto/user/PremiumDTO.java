package com.example.comicbe.payload.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PremiumDTO implements Serializable {

    @NotNull(groups = {Change.class}, message = "PRODUCT_NOT_EMPTY")
    private Long productId;

    public interface Change extends Default {
    }
}
