package com.amr.project.converter;

import com.amr.project.model.dto.DiscountDto;
import com.amr.project.model.entity.Discount;
import com.amr.project.model.entity.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface DiscountMapper {

    @Mapping(source = "shop.id", target = "shopId")
    @Mapping(source = "user.id", target = "userId")
    DiscountDto toDto(Discount discount);

    @Mapping(source = "shopId", target = "shop.id")
    @Mapping(source = "userId", target = "user.id")
    Discount fromDto(DiscountDto dto);

    default Collection<Discount> map(Shop shop) {
        return shop.getDiscounts();
    }
}
