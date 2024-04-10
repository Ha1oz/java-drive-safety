package net.haloz.test.controller;

import lombok.AllArgsConstructor;
import net.haloz.test.dao.PureStatEventNewDao;
import net.haloz.test.entity.MagnetometerTest;
import net.haloz.test.entity.PureStatEventNew;
import net.haloz.test.mapper.PureStatEventNewMapper;
import net.haloz.test.service.PureStatEventNewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/stat")
@AllArgsConstructor
public class PureStatEventDataNewController {
    private PureStatEventNewService pureStatEventNewService;

    @GetMapping("/datetime")
    private ResponseEntity<?> getDataTestByDatetime(@RequestParam Long datetime){
        PureStatEventNewDao dao = PureStatEventNewMapper.toDao(pureStatEventNewService.getPureStatEventDataByDatetime(datetime));

        return new ResponseEntity<>(dao, HttpStatus.OK);
    }
    @GetMapping("/datetime/between")
    private ResponseEntity<?> getDataTestBetweenDatetime(@RequestParam Long start, @RequestParam Long end, @RequestParam Long userId){
        List<PureStatEventNew> list = new ArrayList<>();
        try {
            list = pureStatEventNewService.getPureStatEventDataBetween(start, end, userId);
        }
        catch (Exception e) {
            return new ResponseEntity<>(list, HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    private ResponseEntity<?> getDataTestById(@PathVariable Long id){
        PureStatEventNewDao dao = PureStatEventNewMapper.toDao(pureStatEventNewService.getPureStatEventDataById(id));

        return new ResponseEntity<>(dao, HttpStatus.OK);
    }
}
