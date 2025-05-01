package com.zacharydenning.task_manager.api;

import com.zacharydenning.model.v1.Task;
import com.zacharydenning.model.v1.TaskCreateRequest;
import com.zacharydenning.model.v1.TaskUpdateRequest;
import com.zacharydenning.task_manager.service.TaskService;
import org.apache.coyote.Response;
import org.openapitools.api.DefaultApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TaskApi implements DefaultApi
{
    @Autowired
    private TaskService taskService;

    @Override
    public ResponseEntity<List<Task>> getTasks()
    {
        return ResponseEntity.ok(taskService.getTasks());
    }

    @Override
    public ResponseEntity<Task> getTaskById(final String id)
    {
        return ResponseEntity.ok(taskService.getTask(id));
    }

    @Override
    public ResponseEntity<Task> createTask(final TaskCreateRequest request)
    {
        final Task task = taskService.createTask(request.getDescription(), request.getTitle(), request.getDueDate());
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<Task> updateTask(final String id, final TaskUpdateRequest request)
    {
        final Task task = taskService.updateTask(id, request);
        return ResponseEntity.ok(task);
    }

    @Override
    public ResponseEntity<Void> deleteTask(final String id)
    {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }


}
