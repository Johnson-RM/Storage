import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.queue.CloudQueue;
import com.microsoft.azure.storage.queue.CloudQueueClient;
import com.microsoft.azure.storage.queue.CloudQueueMessage;
import com.microsoft.azure.storage.queue.MessageUpdateFields;

import java.util.EnumSet;

/**
 * Created by johnson on 2017/8/21.
 */
public class AzureQueue {
    public static final String storageConnectionString =
            "DefaultEndpointsProtocol=https;"+"AccountName=cysforstorage;"+"AccountKey=3kSbAcxBDWIlHfPGiG2UwBHeo2Wh2Kb38x3R4MXYRdsn7kRyQgP97np6EUfaI/fOU9Z2RU0iLIXyEF0k5/FJqw==;EndpointSuffix=core.chinacloudapi.cn";
    public static void main(String[] args) {
    //createQueue(storageConnectionString);
        //insertMessage(storageConnectionString);
        //peekMessage(storageConnectionString);
        //peekAllMessage(storageConnectionString);
        //updateQueue(storageConnectionString);
        //getCount(storageConnectionString);
        //deleteMessage(storageConnectionString);
        //showAllQueueName(storageConnectionString);
        deleteQueue(storageConnectionString);
    }
    public static void createQueue(String storageConnectionString){
        try
        {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(storageConnectionString);

            // Create the queue client.
            CloudQueueClient queueClient = storageAccount.createCloudQueueClient();

            // Retrieve a reference to a queue.
            CloudQueue queue = queueClient.getQueueReference("myqueue");

            // Create the queue if it doesn't already exist.
            queue.createIfNotExists();
        }
        catch (Exception e)
        {
            // Output the stack trace.
            e.printStackTrace();
        }

    }
    public static void insertMessage(String storageConnectionString){
        try
        {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(storageConnectionString);

            // Create the queue client.
            CloudQueueClient queueClient = storageAccount.createCloudQueueClient();

            // Retrieve a reference to a queue.
            CloudQueue queue = queueClient.getQueueReference("myqueue");

            // Create the queue if it doesn't already exist.
            queue.createIfNotExists();

            // Create a message and add it to the queue.
            CloudQueueMessage message = new CloudQueueMessage("Happy");
            queue.addMessage(message);
        }
        catch (Exception e)
        {
            // Output the stack trace.
            e.printStackTrace();
        }


    }
    public static void peekMessage(String s){//只取第一条消息，而且不删除，所以不能做遍历
        try
        {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(storageConnectionString);

            // Create the queue client.
            CloudQueueClient queueClient = storageAccount.createCloudQueueClient();

            // Retrieve a reference to a queue.
            CloudQueue queue = queueClient.getQueueReference("myqueue");

            // Peek at the next message.
            CloudQueueMessage peekedMessage = queue.peekMessage();
            while (peekedMessage!=null) {
                // Output the message value.
                if (peekedMessage != null) {
                    System.out.println(peekedMessage.getMessageContentAsString());
                }
                peekedMessage = queue.peekMessage();
            }
        }
        catch (Exception e)
        {
            // Output the stack trace.
            e.printStackTrace();
        }

    }
    public static void peekAllMessage(String storageConnectionString){
        try
        {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(storageConnectionString);

            // Create the queue client.
            CloudQueueClient queueClient = storageAccount.createCloudQueueClient();

            // Retrieve a reference to a queue.
            CloudQueue queue = queueClient.getQueueReference("myqueue");
            CloudQueue queue_copy=queueClient.getQueueReference("myqueuecopy");
            queue_copy.createIfNotExists();
            // Peek at the next message.
            CloudQueueMessage peekedMessage = queue.retrieveMessage();
            while (peekedMessage!=null) {
                // Output the message value.
                if (peekedMessage != null) {
                    System.out.println(peekedMessage.getMessageContentAsString());
                    queue_copy.addMessage(peekedMessage);
                }
                peekedMessage = queue.retrieveMessage();
            }
        }
        catch (Exception e)
        {
            // Output the stack trace.
            e.printStackTrace();
        }

    }
    public static void updateQueue(String storageConnectionString){
        try
        {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(storageConnectionString);

            // Create the queue client.
            CloudQueueClient queueClient = storageAccount.createCloudQueueClient();

            // Retrieve a reference to a queue.
            CloudQueue queue = queueClient.getQueueReference("myqueue");

            // The maximum number of messages that can be retrieved is 32.
            final int MAX_NUMBER_OF_MESSAGES_TO_PEEK = 32;

            // Loop through the messages in the queue.
            for (CloudQueueMessage message : queue.retrieveMessages(MAX_NUMBER_OF_MESSAGES_TO_PEEK,1,null,null))
            {
                // Check for a specific string.
                if (message.getMessageContentAsString().equals("Hello, World"))
                {
                    // Modify the content of the first matching message.
                    message.setMessageContent("Updated contents.");
                    // Set it to be visible in 30 seconds.
                    EnumSet<MessageUpdateFields> updateFields =
                            EnumSet.of(MessageUpdateFields.CONTENT,
                                    MessageUpdateFields.VISIBILITY);
                    // Update the message.
                    queue.updateMessage(message, 30, updateFields, null, null);
                    break;
                }
            }
        }
        catch (Exception e)
        {
            // Output the stack trace.
            e.printStackTrace();
        }
    }
    public static void getCount(String storageConnectionString){
        try
        {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(storageConnectionString);

            // Create the queue client.
            CloudQueueClient queueClient = storageAccount.createCloudQueueClient();

            // Retrieve a reference to a queue.
            CloudQueue queue = queueClient.getQueueReference("myqueue");

            // Download the approximate message count from the server.
            queue.downloadAttributes();

            // Retrieve the newly cached approximate message count.
            long cachedMessageCount = queue.getApproximateMessageCount();

            // Display the queue length.
            System.out.println(String.format("Queue length: %d", cachedMessageCount));
        }
        catch (Exception e)
        {
            // Output the stack trace.
            e.printStackTrace();
        }
    }
    public static void deleteMessage(String storageConnectionString)
    {
        try
        {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(storageConnectionString);

            // Create the queue client.
            CloudQueueClient queueClient = storageAccount.createCloudQueueClient();

            // Retrieve a reference to a queue.
            CloudQueue queue = queueClient.getQueueReference("myqueue");

            // Retrieve the first visible message in the queue.
            CloudQueueMessage retrievedMessage = queue.retrieveMessage();

            if (retrievedMessage != null)
            {
                // Process the message in less than 30 seconds, and then delete the message.
                queue.deleteMessage(retrievedMessage);
            }
        }
        catch (Exception e)
        {
            // Output the stack trace.
            e.printStackTrace();
        }
    }
    public static void showAllQueueName(String storageConnectionString){
        try
        {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(storageConnectionString);

            // Create the queue client.
            CloudQueueClient queueClient =
                    storageAccount.createCloudQueueClient();

            // Loop through the collection of queues.
            for (CloudQueue queue : queueClient.listQueues())
            {
                // Output each queue name.
                System.out.println(queue.getName());
            }
        }
        catch (Exception e)
        {
            // Output the stack trace.
            e.printStackTrace();
        }

    }
    public static void deleteQueue(String storageConnectionString){
        try
        {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(storageConnectionString);

            // Create the queue client.
            CloudQueueClient queueClient = storageAccount.createCloudQueueClient();

            // Retrieve a reference to a queue.
            CloudQueue queue = queueClient.getQueueReference("myqueue");

            // Delete the queue if it exists.
            queue.deleteIfExists();
        }
        catch (Exception e)
        {
            // Output the stack trace.
            e.printStackTrace();
        }
    }

}
