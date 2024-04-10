package net.haloz.test.service;

import lombok.AllArgsConstructor;
import net.haloz.test.entity.MagnetometerTest;
import net.haloz.test.entity.PureStatEventNew;
import net.haloz.test.geomag.TSAGeoMag;
import net.haloz.test.mapper.PureStatEventNewMapper;
import net.haloz.test.repo.MagnetometerTestRepo;
import net.haloz.test.repo.PureStatEventNewRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.GregorianCalendar;
import java.util.List;

@Service
@AllArgsConstructor
public class PureStatEventNewService {
    private final Logger LOGGER = LoggerFactory.getLogger(PureStatEventNewService.class);
    private PureStatEventNewRepo pureStatEventNewRepo;
    private MagnetometerTestRepo magnetometerTestRepo;

    public PureStatEventNew getPureStatEventDataByDatetime(long datetime) {
        LOGGER.info("PureStatEventData: datetime=" + datetime);
        return pureStatEventNewRepo.getPureStatEventNewByDatetime(datetime);
    }
    public List<PureStatEventNew> getPureStatEventDataBetween(long start, long end, long userid) {
        LOGGER.info("PureStatEventDataBetween: start=" + start + ", end=" + end);
        return pureStatEventNewRepo.getPureStatEventNewsByDatetimeBetweenAndUserid(start, end, userid);
    }
    public Boolean addMagnetometerDataBetween(long start, long end, long userid){
        LOGGER.info("PureStatEventDataBetween: start=" + start + ", end=" + end + ", userid=" + userid);

        try {
            magnetometerTestRepo.saveAll(getMagnetometerTestList(start, end, userid));
        } catch (Exception e){
            return false;
        }

        return true;
    }

    public PureStatEventNew getPureStatEventDataById(long id) {
        LOGGER.info("PureStatEventDataBetween: datetime=" + id);
        return pureStatEventNewRepo.getPureStatEventNewByIdStatEvent(id);
    }

    public List<MagnetometerTest> getMagnetometerTestList(long start, long end, long userid) {
        LOGGER.info("PureStatEventDataBetween: start=" + start + ", end=" + end + ", userid=" + userid);
        List<PureStatEventNew> psenList = pureStatEventNewRepo
                .getPureStatEventNewsByDatetimeBetweenAndUseridOrderByDatetimeAsc(start,end,userid);

        double magneticDeclaration = calculateMagneticDeclaration(
                psenList.get(0).getLatitude(),
                psenList.get(0).getLongitude(),
                psenList.get(0).getAltitude());

        return psenList.stream().map(e -> calculateMagnetometerTestData(e, magneticDeclaration)).toList();
    }

    private MagnetometerTest calculateMagnetometerTestData(PureStatEventNew curr, double magneticDeclaration){


        double[] magnXYZ = PureStatEventNewMapper.parseData(curr.getMagnetometerData());
        double[] accelXYZ = PureStatEventNewMapper.parseData(curr.getAccelerometerData());

        MagnetometerTest result = new MagnetometerTest();

        result.setId(curr.getIdStatEvent());
        result.setDatetime(curr.getDatetime());
        result.setUserId(curr.getUserid());

        result.setMagnetometer_x(magnXYZ[0]);
        result.setMagnetometer_y(magnXYZ[1]);
        result.setMagnetometer_z(magnXYZ[2]);

        result.setAccelerometer_x(accelXYZ[0]);
        result.setAccelerometer_y(accelXYZ[1]);
        result.setAccelerometer_z(accelXYZ[2]);

        result.setCalibratedheadangleyaw(curr.getCalibratedheadangleyaw());
        result.setSpeed(curr.getSpeed());


        result.setAngle(calculateAngle(magnXYZ, accelXYZ) + magneticDeclaration);

        return result;
    }

    private double calculateAngle(double[] magnXYZ, double[] accelXYZ) {
        double Ax = magnXYZ[0], Ay = magnXYZ[1], Az = magnXYZ[2];
        double Ex = accelXYZ[0], Ey = accelXYZ[1], Ez = accelXYZ[2];


        //cross product of the magnetic field vector and the gravity vector
        double Hx = Ey * Az - Ez * Ay;
        double Hy = Ez * Ax - Ex * Az;
        double Hz = Ex * Ay - Ey * Ax;

        //normalize the values of resulting vector
        final double invH = 1.0 / Math.sqrt(Hx * Hx + Hy * Hy + Hz * Hz);
        Hx *= invH;
        Hy *= invH;
        Hz *= invH;

        //normalize the values of gravity vector
        final double invA = 1.0f / Math.sqrt(Ax * Ax + Ay * Ay + Az * Az);
        Ax *= invA;
        Ay *= invA;
        Az *= invA;

        //cross product of the gravity vector and the new vector H
        final double Mx = Ay * Hz - Az * Hy;
        final double My = Az * Hx - Ax * Hz;
        final double Mz = Ax * Hy - Ay * Hx;

        double degrees = Math.toDegrees(Math.atan2(Hy, My));

        return  (degrees + 360) % 360;
    }

    private double calculateMagneticDeclaration(double latitude, double longitude, double altitude) {
        TSAGeoMag geoMag = new TSAGeoMag();
        return  geoMag.getDeclination(
                latitude,
                longitude,
                geoMag.decimalYear(new GregorianCalendar()),
                altitude);
    }

}
