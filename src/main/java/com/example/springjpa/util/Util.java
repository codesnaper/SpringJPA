package com.example.springjpa.util;

import com.example.springjpa.dao.BookDao;
import com.example.springjpa.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Util {
    private static Logger logger = LoggerFactory.getLogger(Util.class);

    public static void printRow(List<? extends Object> persons){
        logger.info("............... Table Data ..........");
        int cnt =1;
        for(Object person:persons){
            logger.info("Row {} >> {}",cnt,person.toString());
            cnt++;
        }
        logger.info("........... END .......................");
    }
}
