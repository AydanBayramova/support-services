package az.finalproject.msrating.mapper;


import az.finalproject.msrating.dto.RatingRequest;
import az.finalproject.msrating.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, imports = LocalDateTime.class)
public interface ReviewMapper {

    @Mapping(target = "raterId", source = "customerId")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    Review toEntity(RatingRequest request);
}
