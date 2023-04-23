package com.event.backevents.api.model.input;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum KmOrMiles {
    KM, MILES;

    public Integer toNumber() {
        if(this.equals(KmOrMiles.KM))
            return 6371;
        return 3959;
    }
}
