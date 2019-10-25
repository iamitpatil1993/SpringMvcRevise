package com.example.mvc.revise.dao;

import com.example.mvc.revise.dto.Spittle;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Repository
public class SpittleRepository {

    public List<Spittle> findAll() {
        final List<Spittle> spittles = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            spittles.add(new Spittle(UUID.randomUUID().toString(), "Foo Bar Message " + UUID.randomUUID().toString(), Calendar.getInstance()));
        }
        return spittles;
    }


}
