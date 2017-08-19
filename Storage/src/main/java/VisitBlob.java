import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.*;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class VisitBlob {
    public static void main(String[] args) {
        final String storageConnectionString =
                "DefaultEndpointsProtocol=https;AccountName=storagecys;AccountKey=7GtHjH29vvx73uK7lc5htyAJ0hsU7H8K6C8K+ZkXlRBvCnFoeLwyupHDqMGLRvYhvNQ4Ts8/0Um9BU6zGj4q3A==;EndpointSuffix=core.chinacloudapi.cn";
        // TODO Auto-generated method stub
        //createBlob(storageConnectionString);
        //upLoad(storageConnectionString);
        //listBlob(storageConnectionString);
        //downLoad(storageConnectionString);
        //deleteBlob(storageConnectionString);
        deleteContainer(storageConnectionString);
    }

    public static void createBlob(String storageConnectionString) {
        try {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

            // Create the blob client.
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            // Get a reference to a container.
            // The container name must be lower case
            CloudBlobContainer container = blobClient.getContainerReference("huahua");

            // Create the container if it does not exist.
            container.createIfNotExists();
        } catch (Exception e) {
            // Output the stack trace.
            e.printStackTrace();
        }
    }

    public static void upLoad(String storageConnectionString) {
        try {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);//存储账号

            // Create the blob client.
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();//创建这个对象只是用来操作，并不属于存储中的某个结构

            // Retrieve reference to a previously created container.
            CloudBlobContainer container = blobClient.getContainerReference("mycontainer");//容器

            // Define the path to a local file.
            final String filePath = "C:\\Users\\johnson\\Desktop\\123.txt";

            // Create or overwrite the "myimage.jpg" blob with contents from a local file.
            CloudBlockBlob blob = container.getBlockBlobReference("123.txt");//blob集
            File source = new File(filePath);
            blob.upload(new FileInputStream(source), source.length());
        } catch (Exception e) {
            // Output the stack trace.
            e.printStackTrace();
        }
    }

    public static void listBlob(String storageConnectionString) {
        try {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

            // Create the blob client.
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            // Retrieve reference to a previously created container.
            CloudBlobContainer container = blobClient.getContainerReference("mycontainer");

            // Loop over blobs within the container and output the URI to each of them.
            for (ListBlobItem blobItem : container.listBlobs()) {
                System.out.println(blobItem.getUri());
            }
        } catch (Exception e) {
            // Output the stack trace.
            e.printStackTrace();
        }
    }

    public static void downLoad(String storageConnectionString) {
        try {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

            // Create the blob client.
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            // Retrieve reference to a previously created container.
            CloudBlobContainer container = blobClient.getContainerReference("mycontainer");

            // Loop through each blob item in the container.
            for (ListBlobItem blobItem : container.listBlobs()) {
                // If the item is a blob, not a virtual directory.
                if (blobItem instanceof CloudBlob) {
                    // Download the item and save it to a file with the same name.
                    CloudBlob blob = (CloudBlob) blobItem;
                    blob.download(new FileOutputStream("C:\\Users\\johnson\\Desktop\\mydownloads\\" + blob.getName()));
                }
            }
        } catch (Exception e) {
            // Output the stack trace.
            e.printStackTrace();
        }
    }

    public static void deleteBlob(String storageConnectionString) {
        try {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

            // Create the blob client.
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            // Retrieve reference to a previously created container.
            CloudBlobContainer container = blobClient.getContainerReference("mycontainer");

            // Retrieve reference to a blob named "myimage.jpg".
            CloudBlockBlob blob = container.getBlockBlobReference("myimage.jpg");

            // Delete the blob.
            blob.deleteIfExists();
        } catch (Exception e) {
            // Output the stack trace.
            e.printStackTrace();
        }
    }

    public static void deleteContainer(String storageConnectionString) {
        try {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

            // Create the blob client.
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            // Retrieve reference to a previously created container.
            CloudBlobContainer container = blobClient.getContainerReference("mycontainer");

            // Delete the blob container.
            container.deleteIfExists();
        } catch (Exception e) {
            // Output the stack trace.
            e.printStackTrace();
        }
    }


}
