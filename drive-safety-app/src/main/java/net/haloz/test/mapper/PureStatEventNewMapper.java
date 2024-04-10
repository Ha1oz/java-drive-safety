package net.haloz.test.mapper;

import net.haloz.test.dao.PureStatEventNewDao;
import net.haloz.test.entity.MagnetometerTest;
import net.haloz.test.entity.PureStatEventNew;

import java.util.Arrays;

public class PureStatEventNewMapper {
    public static PureStatEventNewDao toDao(PureStatEventNew pureStatEventNew){
        PureStatEventNewDao dao = new PureStatEventNewDao();

        dao.setId(pureStatEventNew.getIdStatEvent());
        dao.setSpeed(pureStatEventNew.getSpeed());
        dao.setDatetime(pureStatEventNew.getDatetime());
        dao.setUserid(pureStatEventNew.getUserid());
        dao.setCalibratedheadangleyaw(pureStatEventNew.getCalibratedheadangleyaw());

        double[] xyz = parseData(pureStatEventNew.getMagnetometerData());

        dao.setMagnetometerDataX(xyz[0]);
        dao.setMagnetometerDataY(xyz[1]);
        dao.setMagnetometerDataZ(xyz[2]);

        xyz = parseData(pureStatEventNew.getAccelerometerData());

        dao.setAccelerometerDataX(xyz[0]);
        dao.setAccelerometerDataY(xyz[1]);
        dao.setAccelerometerDataZ(xyz[2]);

        return dao;
    }
    public static PureStatEventNew toPureStatNewEvent(PureStatEventNewDao dao){
        PureStatEventNew pureStatEventNew = new PureStatEventNew();

        pureStatEventNew.setIdStatEvent(dao.getId());
        pureStatEventNew.setSpeed(dao.getSpeed());
        pureStatEventNew.setDatetime(dao.getDatetime());
        pureStatEventNew.setMagnetometerData(dao.combineMagnetometerData());
        pureStatEventNew.setAccelerometerData(dao.combineAccelerometerData());
        pureStatEventNew.setUserid(dao.getUserid());
        pureStatEventNew.setCalibratedheadangleyaw(dao.getCalibratedheadangleyaw());


        return pureStatEventNew;
    }
//    public static MagnetometerTest toMagnetometerExperimentsFromDao(PureStatEventNewDao dao) {
//        MagnetometerTest magnetometerTest = new MagnetometerTest();
//
//        magnetometerTest.setId(dao.getId());
//        magnetometerTest.setDatetime(dao.getDatetime());
//        magnetometerTest.setMagnetometer_x(dao.getMagnetometerDataX());
//        magnetometerTest.setMagnetometer_y(dao.getMagnetometerDataY());
//        magnetometerTest.setMagnetometer_z(dao.getMagnetometerDataZ());
//        magnetometerTest.setUserId(dao.getUserid());
//        magnetometerTest.setSpeed(dao.getSpeed());
//        magnetometerTest.setAngle(calculateAngleFromMagnetometerData(dao));
//
//        return magnetometerTest;
//    }
//    public static MagnetometerTest toMagnetometerExperiments(PureStatEventNew psen) {
//        MagnetometerTest magnetometerTest = new MagnetometerTest();
//
//        PureStatEventNewDao dao = new PureStatEventNewDao();
//        double[] xyz = parseMagnetometerData(psen.getMagnetometerData());
//        dao.setMagnetometerDataX(xyz[0]);
//        dao.setMagnetometerDataY(xyz[1]);
//        dao.setMagnetometerDataZ(xyz[2]);
//
//        magnetometerTest.setId(psen.getIdStatEvent());
//        magnetometerTest.setDatetime(psen.getDatetime());
//        magnetometerTest.setMagnetometer_x(xyz[0]);
//        magnetometerTest.setMagnetometer_y(xyz[1]);
//        magnetometerTest.setMagnetometer_z(xyz[2]);
//        magnetometerTest.setUserId(psen.getUserid());
//        magnetometerTest.setSpeed(psen.getSpeed());
//        magnetometerTest.setCalibratedheadangleyaw(psen.getCalibratedheadangleyaw());
//        magnetometerTest.setAngle(calculateAngleFromMagnetometerData(dao));
//
//        return magnetometerTest;
//    }

    public static double[] parseData(String data) {
        String[] params = data.split(",");

        params[0] = params[0].replace("[","");
        params[2] = params[2].replace("]","");

        double[] xyz = Arrays.stream(params).mapToDouble(Double::parseDouble).toArray();

        return xyz;
    }
//    private static long calculateAngleFromMagnetometerData(PureStatEventNewDao dao) {
//        return Math.round(Math.toDegrees(Math.atan2(dao.getMagnetometerDataY(), dao.getMagnetometerDataX())));
//    }
}
