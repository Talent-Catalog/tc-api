package org.tctalent.anonymization.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * This encapsulates information about a TC server partner.
 * <p/>
 * It is the subset of the full partner data needed in order to support this public api server.
 */
@Getter
@Setter
public class Partner {

    /**
     * Name of partner
     */
    private String name;

    /**
     * Id of partner on TC server
     */
    private long partnerId;

    /**
     * List public api authority strings
     */
    private List<String> publicApiAuthorities;
}
