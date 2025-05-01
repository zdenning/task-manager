package com.zacharydenning.task_manager.service;

import com.zacharydenning.model.v1.Task;
import com.zacharydenning.model.v1.TaskUpdateRequest;
import com.zacharydenning.task_manager.dynamodb.client.DynamoDBClient;
import com.zacharydenning.task_manager.dynamodb.mapper.TaskItem;
import com.zacharydenning.task_manager.transformer.TaskTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class TaskService
{
    @Autowired
    private DynamoDBClient dynamoDBClient;

    public List<Task> getTasks()
    {
        final List<Task> tasks = new ArrayList<>();

        dynamoDBClient.getTasks().forEach(taskItem -> {
            final Task task = new Task();
            task.setCompleted(taskItem.getCompleted());
            task.setDescription(taskItem.getDescription());
            task.setId(taskItem.getId());
            task.setTitle(taskItem.getTitle());
            task.setDueDate(taskItem.getDueDate());
            tasks.add(task);
        });

        return tasks;
    }

    public Task getTask(final String id)
    {
        return TaskTransformer.transformToTask(dynamoDBClient.getTask(id));
    }


    public Task createTask(final String description, final String title, final OffsetDateTime dueDate)
    {
        final TaskItem taskItem = new TaskItem();
        taskItem.setCompleted(false);
        taskItem.setDescription(description);
        taskItem.setDueDate(dueDate);
        taskItem.setTitle(title);
        taskItem.setId(UUID.randomUUID().toString());

        return TaskTransformer.transformToTask(dynamoDBClient.addTask(taskItem));
    }

    public Task updateTask(final String id, final TaskUpdateRequest request)
    {
        final TaskItem taskItem = new TaskItem();
        taskItem.setId(id);
        taskItem.setDueDate(request.getDueDate());
        taskItem.setCompleted(request.getCompleted());
        taskItem.setDescription(request.getDescription());
        taskItem.setTitle(request.getTitle());

        return TaskTransformer.transformToTask(dynamoDBClient.updateTask(taskItem));
    }

    public void deleteTask(final String id)
    {
        dynamoDBClient.deleteTask(id);
    }

}
