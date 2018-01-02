package cz.vlasimsky.springboot.statisticsMicroservice.services;

import cz.vlasimsky.springboot.statisticsMicroservice.daos.StatisticsDao;
import cz.vlasimsky.springboot.statisticsMicroservice.entities.Statistics;
import cz.vlasimsky.springboot.statisticsMicroservice.utils.JsonResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {


    @Autowired
    StatisticsDao statisticsDao;

    @Override
    public List<Statistics> getStatistics(String jwt, String email) {
        List<LinkedHashMap> todos = getNewDataFromTodoMicroservice(jwt);
        String statisticsDescription = "No statistics available";
        if (todos != null && todos.size() > 0) {
            int lowPriorityTodos = 0;
            int highPriorityTodos = 0;
            for (LinkedHashMap todo : todos) {
                String priority = (String) todo.get("priority");
                if ("low".equals(priority)) lowPriorityTodos++;
                if ("high".equals(priority)) highPriorityTodos++;
            }
            statisticsDescription = "You have <b> " + lowPriorityTodos + " low priority</b> todos and <b>"  + highPriorityTodos + " high priority</b> todos";

        }
        List<Statistics> statistics = statisticsDao.getLastStatistics(email);
        if (statistics.size() > 0) {
            Date now = new Date();
            long diff = now.getTime() - statistics.get(0).getDate().getTime();
            long days = diff/(24 * 60 * 1000);
            if (days > 1) {
                statistics.add(statisticsDao.save(new Statistics(null, statisticsDescription, new Date(), email)));
            }
        }
        return statistics;
    }

    @Override
    public List<LinkedHashMap> getNewDataFromTodoMicroservice(String jwt) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("jwt", jwt);
        HttpEntity request = new HttpEntity(String.class, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<JsonResponseBody> responseEntity = restTemplate.exchange("http://localhost:8080/showTodos", HttpMethod.POST, request, JsonResponseBody.class);

        List<LinkedHashMap> todoList = (List) responseEntity.getBody().getResponse();

        return todoList;
    }


}
