package com.bristoHQ.devHub.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bristoHQ.devHub.models.premium.RedeemCode;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RedeemCodeRepository extends MongoRepository<RedeemCode, String> {

    Boolean existsByRedeemCode(String redeemCode);

    RedeemCode findByRedeemCode(String redeemCode);

    @SuppressWarnings({ "null", "rawtypes", "unchecked" })
    Optional findById(String id);

    @SuppressWarnings("null")
    boolean existsById(String id);

    Boolean existsByRedeemCodeAndIsUsed(String redeemCode, boolean isUsed);

    List<RedeemCode> findByIsUsed(boolean isUsed);

    List<RedeemCode> findByRedeemedBy(String redeemedBy);

    List<RedeemCode> findByCreatedAtBetween(Date startDate, Date endDate);

    List<RedeemCode> findByRedeemedAtBetween(Date startDate, Date endDate);
}
