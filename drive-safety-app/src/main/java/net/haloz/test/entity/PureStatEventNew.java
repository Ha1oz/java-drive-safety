package net.haloz.test.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class PureStatEventNew {
    @Id
    private Long idStatEvent;
    private Double latitude;
    private Double longitude;
    private Double altitude;
    private String magnetometerData;
    private String accelerometerData;
    private Double speed;
    private Long datetime;
    private Long userid;
    private Double calibratedheadangleyaw;

    public PureStatEventNew() {
    }
}
