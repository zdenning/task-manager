package com.zacharydenning.task_manager.dynamodb.mapper;


import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.OffsetDateTime;

@DynamoDbBean
public class TaskItem
{
    private String id;

    private String title;

    private String description;

    private Boolean completed;

    private OffsetDateTime dueDate;

    private static final DynamoDbEnhancedClient DDB_ENHANCED_CLIENT = DynamoDbEnhancedClient.create();

    private static final DynamoDbTable<TaskItem> TASK_TABLE =
            DDB_ENHANCED_CLIENT.table("task", TableSchema.fromBean(TaskItem.class));


    public static TaskItem load(TaskItem taskItem)
    {
        return TASK_TABLE.getItem(taskItem);
    }

    public void save()
    {
        TASK_TABLE.putItem(this);
    }

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public OffsetDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(OffsetDateTime dueDate) {
        this.dueDate = dueDate;
    }
}
