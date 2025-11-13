package ru.krezd.comfortsofttask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.krezd.comfortsofttask.service.TaskService;

@RestController()
@RequestMapping("/api")
public class TaskController
{
    @Autowired
    private TaskService taskService;

    @GetMapping("/")
    public ResponseEntity<?> getNumber(@RequestParam("pathToFile") String pathToFile, @RequestParam("N") int N) throws Exception
    {
        try
        {
            return new ResponseEntity<>(taskService.findNumber(pathToFile, N), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
