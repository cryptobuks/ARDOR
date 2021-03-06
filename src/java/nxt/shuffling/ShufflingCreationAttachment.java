/*
 * Copyright © 2013-2016 The Nxt Core Developers.
 * Copyright © 2016-2018 Jelurida IP B.V.
 *
 * See the LICENSE.txt file at the top-level directory of this distribution
 * for licensing information.
 *
 * Unless otherwise agreed in a custom licensing agreement with Jelurida B.V.,
 * no part of this software, including this file, may be copied, modified,
 * propagated, or distributed except according to the terms contained in the
 * LICENSE.txt file.
 *
 * Removal or modification of this copyright notice is prohibited.
 *
 */

package nxt.shuffling;

import nxt.account.HoldingType;
import nxt.blockchain.Attachment;
import nxt.blockchain.TransactionType;
import nxt.util.Convert;
import org.json.simple.JSONObject;

import java.nio.ByteBuffer;

public final class ShufflingCreationAttachment extends Attachment.AbstractAttachment {

    private final long holdingId;
    private final HoldingType holdingType;
    private final long amount;
    private final byte participantCount;
    private final short registrationPeriod;

    ShufflingCreationAttachment(ByteBuffer buffer) {
        super(buffer);
        this.holdingId = buffer.getLong();
        this.holdingType = HoldingType.get(buffer.get());
        this.amount = buffer.getLong();
        this.participantCount = buffer.get();
        this.registrationPeriod = buffer.getShort();
    }

    ShufflingCreationAttachment(JSONObject attachmentData) {
        super(attachmentData);
        this.holdingId = Convert.parseUnsignedLong((String) attachmentData.get("holding"));
        this.holdingType = HoldingType.get(((Long)attachmentData.get("holdingType")).byteValue());
        this.amount = Convert.parseLong(attachmentData.get("amount"));
        this.participantCount = ((Long)attachmentData.get("participantCount")).byteValue();
        this.registrationPeriod = ((Long)attachmentData.get("registrationPeriod")).shortValue();
    }

    public ShufflingCreationAttachment(long holdingId, HoldingType holdingType, long amount, byte participantCount, short registrationPeriod) {
        this.holdingId = holdingId;
        this.holdingType = holdingType;
        this.amount = amount;
        this.participantCount = participantCount;
        this.registrationPeriod = registrationPeriod;
    }

    @Override
    protected int getMySize() {
        return 8 + 1 + 8 + 1 + 2;
    }

    @Override
    protected void putMyBytes(ByteBuffer buffer) {
        buffer.putLong(holdingId);
        buffer.put(holdingType.getCode());
        buffer.putLong(amount);
        buffer.put(participantCount);
        buffer.putShort(registrationPeriod);
    }

    @Override
    protected void putMyJSON(JSONObject attachment) {
        attachment.put("holding", Long.toUnsignedString(holdingId));
        attachment.put("holdingType", holdingType.getCode());
        attachment.put("amount", amount);
        attachment.put("participantCount", participantCount);
        attachment.put("registrationPeriod", registrationPeriod);
    }

    @Override
    public TransactionType getTransactionType() {
        return ShufflingTransactionType.SHUFFLING_CREATION;
    }

    public long getHoldingId() {
        return holdingId;
    }

    public HoldingType getHoldingType() {
        return holdingType;
    }

    public long getAmount() {
        return amount;
    }

    public byte getParticipantCount() {
        return participantCount;
    }

    public short getRegistrationPeriod() {
        return registrationPeriod;
    }
}
