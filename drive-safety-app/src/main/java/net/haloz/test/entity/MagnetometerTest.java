package net.haloz.test.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class MagnetometerTest {
    @Id
    private Long id;
    private Long datetime;
    private Long userId;
    private Double magnetometer_x;
    private Double magnetometer_y;
    private Double magnetometer_z;
    private Double accelerometer_x;
    private Double accelerometer_y;
    private Double accelerometer_z;
    private Double speed;
    private Double angle;
    private Double calibratedheadangleyaw;


    public MagnetometerTest() {
    }
}
