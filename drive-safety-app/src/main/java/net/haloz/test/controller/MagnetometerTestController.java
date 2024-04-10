package net.haloz.test.controller;

import lombok.AllArgsConstructor;
import net.haloz.test.entity.MagnetometerTest;
import net.haloz.test.entity.PureStatEventNew;
import net.haloz.test.service.PureStatEventNewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/magnetometer")
@AllArgsConstructor
public class MagnetometerTestController {
    private PureStatEventNewService pureStatEventNewService;

    @GetMapping("/datetime/between")
    private ResponseEntity<?> getMagnetometer(@RequestParam Long start, @RequestParam Long end, @RequestParam Long userId){
        List<PureStatEventNew> list = new ArrayList<>();
        try {
            list = pureStatEventNewService.getPureStatEventDataBetween(start, end, userId);
        }
        catch (Exception e) {
            return new ResponseEntity<>(list, HttpStatus.CONFLICT);
        }

        List<MagnetometerTest> listMagnetometerExperiments = pureStatEventNewService
                .getMagnetometerTestList(start, end, userId);

        return new ResponseEntity<>(listMagnetometerExperiments, HttpStatus.OK);
    }
    @PostMapping("/datetime/between")
    private ResponseEntity<?> postMagnetometerData(@RequestParam Long start, @RequestParam Long end, @RequestParam Long userId){
        boolean isPosted = pureStatEventNewService.addMagnetometerDataBetween(start, end, userId);

        return isPosted ? new ResponseEntity<>("Posted", HttpStatus.OK)
                : new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    }
}
