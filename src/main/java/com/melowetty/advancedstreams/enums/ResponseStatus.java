package com.melowetty.advancedstreams.enums;

import com.melowetty.advancedstreams.AdvancedStreams;

public enum ResponseStatus {
    SUCCESSFUL_ADDED(AdvancedStreams.getInstance().getSettingsManager().getMessageSuccessfulAdded()),
    SUCCESSFUL_DELETED(AdvancedStreams.getInstance().getSettingsManager().getMessageSuccessfulDeleted()),
    NOT_FOUND(AdvancedStreams.getInstance().getSettingsManager().getMessageNotFound()),
    OVERFLOW(AdvancedStreams.getInstance().getSettingsManager().getMessageOverflow()),
    INCORRECT_LINK(AdvancedStreams.getInstance().getSettingsManager().getMessageIncorrectLink()),
    UNDEFINED(AdvancedStreams.getInstance().getSettingsManager().getMessageUndefined());
    private final String message;
    ResponseStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
