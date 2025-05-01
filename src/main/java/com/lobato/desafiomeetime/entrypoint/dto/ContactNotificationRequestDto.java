package com.lobato.desafiomeetime.entrypoint.dto;

import java.math.BigInteger;

public record ContactNotificationRequestDto(BigInteger objectId,
                                            String changeSource,
                                            BigInteger eventId,
                                            BigInteger subscriptionId,
                                            String subscriptionType,
                                            BigInteger portalId,
                                            BigInteger appId,
                                            BigInteger occurredAt,
                                            Integer attemptNumber,
                                            String changeFlag) {
}
