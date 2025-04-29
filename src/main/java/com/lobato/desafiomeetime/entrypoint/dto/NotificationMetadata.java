package com.lobato.desafiomeetime.entrypoint.dto;

import java.math.BigInteger;

public record NotificationMetadata(BigInteger objectId,
                                   String propertyName,
                                   String propertyValue,
                                   String changeSource,
                                   BigInteger eventId,
                                   Integer subscriptionId,
                                   Integer portalId,
                                   BigInteger appId,
                                   BigInteger occurredAt,
                                   String eventType,
                                   BigInteger attemptNumber) {
}
