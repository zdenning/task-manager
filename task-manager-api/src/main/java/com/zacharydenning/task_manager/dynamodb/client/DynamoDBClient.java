package com.zacharydenning.task_manager.dynamodb.client;

import com.zacharydenning.model.v1.Task;
import com.zacharydenning.task_manager.dynamodb.mapper.TaskItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DynamoDBClient
{
    /** The class's logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamoDBClient.class);

    /** The client */
    private final DynamoDbEnhancedClient enhancedClient;

    private static final String TASK_TABLE = "task";


    /**
     * Constructor
     */
    public DynamoDBClient()
    {
        DynamoDbClient client = DynamoDbClient.builder()
                .region(Region.US_EAST_1)
                .build();
        enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(client)
                .build();

    }

    public List<TaskItem> getTasks()
    {
        final DynamoDbTable<TaskItem> taskTable =
                enhancedClient.table(TASK_TABLE, TableSchema.fromBean(TaskItem.class));
        return taskTable.scan().items().stream().collect(Collectors.toList());
    }

    public TaskItem getTask(final String id)
    {
        final DynamoDbTable<TaskItem> taskTable =
                enhancedClient.table(TASK_TABLE, TableSchema.fromBean(TaskItem.class));
        return taskTable.getItem(Key.builder().partitionValue(id).build());
    }

    public TaskItem addTask(final TaskItem taskItem)
    {
        final DynamoDbTable<TaskItem> taskTable =
                enhancedClient.table(TASK_TABLE, TableSchema.fromBean(TaskItem.class));
        taskTable.putItem(taskItem);

        return getTask(taskItem.getId());
    }

    public TaskItem updateTask(final TaskItem taskItem)
    {
        final DynamoDbTable<TaskItem> taskTable =
                enhancedClient.table(TASK_TABLE, TableSchema.fromBean(TaskItem.class));
        taskTable.updateItem(taskItem);

        return getTask(taskItem.getId());

    }

    public void deleteTask(final String id)
    {
        final DynamoDbTable<TaskItem> taskTable =
                enhancedClient.table(TASK_TABLE, TableSchema.fromBean(TaskItem.class));
        taskTable.deleteItem(Key.builder().partitionValue(id).build());
    }
}
