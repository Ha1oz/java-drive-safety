package net.haloz.test.dao;

import lombok.Data;

@Data
public class PureStatEventNewDao {
    private Long id;
    private Double magnetometerDataX;
    private Double magnetometerDataY;
    private Double magnetometerDataZ;
    private Double accelerometerDataX;
    private Double accelerometerDataY;
    private Double accelerometerDataZ;
    private Double speed;
    private Long datetime;
    private Long userid;
    private Double calibratedheadangleyaw;

    public String combineMagnetometerData(){
        return "[" + magnetometerDataX
                + ", " + magnetometerDataY
                + ", " + magnetometerDataZ + "]";
    }
    public String combineAccelerometerData(){
        return "[" + accelerometerDataX
                + ", " + accelerometerDataY
                + ", " + accelerometerDataZ + "]";
    }
}
