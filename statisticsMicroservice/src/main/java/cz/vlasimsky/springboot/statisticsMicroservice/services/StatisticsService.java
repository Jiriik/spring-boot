package cz.vlasimsky.springboot.statisticsMicroservice.services;

import cz.vlasimsky.springboot.statisticsMicroservice.entities.Statistics;

import java.util.LinkedHashMap;
import java.util.List;

public interface StatisticsService {

    List<Statistics> getStatistics(String jwt, String email);

    List<LinkedHashMap> getNewDataFromTodoMicroservice(String jwt);
}
