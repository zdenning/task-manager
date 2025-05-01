package com.zacharydenning.task_manager.transformer;

import com.zacharydenning.model.v1.Task;
import com.zacharydenning.task_manager.dynamodb.mapper.TaskItem;

/**
 * Transform between DynamoDB object and API object.
 */
public class TaskTransformer
{

    /**
     * Transform API object -> DynamoDB object
     *
     * @param task
     *              the API task object
     * @return the DynamoDB table item object
     */
    public static TaskItem transformToTaskItem(final Task task)
    {
        final TaskItem taskItem = new TaskItem();
        taskItem.setCompleted(task.getCompleted());
        taskItem.setTitle(task.getTitle());
        taskItem.setDescription(task.getDescription());
        taskItem.setCompleted(task.getCompleted());
        taskItem.setDueDate(task.getDueDate());
        return taskItem;
    }

    /**
     * DynamoDB object -> Transform API object
     *
     * @param taskItem
     *              the DynamoDB table item object
     * @return the API task object
     */
    public static Task transformToTask(final TaskItem taskItem)
    {
        final Task task = new Task();
        task.setCompleted(taskItem.getCompleted());
        task.setTitle(taskItem.getTitle());
        task.setDescription(taskItem.getDescription());
        task.setId(taskItem.getId());
        task.setDueDate(taskItem.getDueDate());
        return task;
    }
}
