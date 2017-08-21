import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.file.CloudFile;
import com.microsoft.azure.storage.file.CloudFileClient;
import com.microsoft.azure.storage.file.CloudFileDirectory;
import com.microsoft.azure.storage.file.CloudFileShare;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

/**
 * Created by johnson on 2017/8/21.
 */
public class FileStorage {

    public static final String storageConnectionString =
            "DefaultEndpointsProtocol=https;"+"AccountName=cysforstorage;"+"AccountKey=3kSbAcxBDWIlHfPGiG2UwBHeo2Wh2Kb38x3R4MXYRdsn7kRyQgP97np6EUfaI/fOU9Z2RU0iLIXyEF0k5/FJqw==;EndpointSuffix=core.chinacloudapi.cn";
    public static void main(String[] args) {
            //createFile(storageConnectionString);
            // deleteFile(storageConnectionString);
            //mkdir(storageConnectionString);
            //deleteDir(storageConnectionString);
            //upLoadFile(storageConnectionString);
            downLoad(storageConnectionString);
    }
    public static void createFile(String storageConnectionString){
        // Use the CloudStorageAccount object to connect to your storage account
        try {
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
            // Create the Azure File storage client.
            CloudFileClient fileClient = storageAccount.createCloudFileClient();
            // Get a reference to the file share
            CloudFileShare share = fileClient.getShareReference("sampleshare");
            if (share.createIfNotExists()) {
                System.out.println("New share created");
            }
        } catch (InvalidKeyException invalidKey) {
            // Handle the exception
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        }

    }
    public static void deleteFile(String storageConnectionString){
        try
        {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

            // Create the file client.
            CloudFileClient fileClient = storageAccount.createCloudFileClient();

            // Get a reference to the file share
            CloudFileShare share = fileClient.getShareReference("sampleshare");

            if (share.deleteIfExists()) {
                System.out.println("sampleshare deleted");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void mkdir(String storageConnectionString){
        // Use the CloudStorageAccount object to connect to your storage account
        try {
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
            // Create the Azure File storage client.
            CloudFileClient fileClient = storageAccount.createCloudFileClient();
            // Get a reference to the file share
            CloudFileShare share = fileClient.getShareReference("sampleshare");
            if (share.createIfNotExists()) {
                System.out.println("New share created");
            }
            //Get a reference to the root directory for the share.
            CloudFileDirectory rootDir = share.getRootDirectoryReference();

           //Get a reference to the sampledir directory
            CloudFileDirectory sampleDir = rootDir.getDirectoryReference("sampledir");

            if (sampleDir.createIfNotExists()) {
                System.out.println("sampledir created");
            } else {
                System.out.println("sampledir already exists");
            }
        } catch (InvalidKeyException invalidKey) {
            // Handle the exception
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        }
    }
    public static void deleteDir(String storageConnectionString){
        try {
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
            // Create the Azure File storage client.
            CloudFileClient fileClient = storageAccount.createCloudFileClient();
            // Get a reference to the file share
            CloudFileShare share = fileClient.getShareReference("sampleshare");

            // Get a reference to the root directory for the share.
            CloudFileDirectory rootDir = share.getRootDirectoryReference();

// Get a reference to the directory you want to delete
            CloudFileDirectory containerDir = rootDir.getDirectoryReference("sampledir");


// Delete the directory
            if (containerDir.deleteIfExists() ) {
                System.out.println("Directory deleted");
            }
        } catch (InvalidKeyException invalidKey) {
            // Handle the exception
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        }
    }
    public static void upLoadFile(String storageConnectionString){
        CloudStorageAccount storageAccount = null;
        try {
            storageAccount = CloudStorageAccount.parse(storageConnectionString);
            // Create the Azure File storage client.
            CloudFileClient fileClient = storageAccount.createCloudFileClient();
            // Get a reference to the file share
            CloudFileShare share = fileClient.getShareReference("sampleshare");
            if (share.createIfNotExists()) {
                System.out.println("New share created");
            }
            //Get a reference to the root directory for the share.
            CloudFileDirectory rootDir = share.getRootDirectoryReference();
            // Define the path to a local file.
            final String filePath = "C:\\Users\\johnson\\Desktop\\temp\\Readme.txt";

            CloudFile cloudFile = rootDir.getFileReference("Readme.txt");
            cloudFile.uploadFromFile(filePath);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void downLoad(String storageConnectionString){
        CloudStorageAccount storageAccount = null;
        try {
            storageAccount = CloudStorageAccount.parse(storageConnectionString);
            // Create the Azure File storage client.
            CloudFileClient fileClient = storageAccount.createCloudFileClient();
            // Get a reference to the file share
            CloudFileShare share = fileClient.getShareReference("sampleshare");
            //Get a reference to the root directory for the share.
            CloudFileDirectory rootDir = share.getRootDirectoryReference();

//Get a reference to the directory that contains the file
            //CloudFileDirectory sampleDir = rootDir.getDirectoryReference("sampledir");

//Get a reference to the file you want to download
            CloudFile file = rootDir.getFileReference("Readme.txt");
//Write the contents of the file to the console.
            //写入本地文件
            FileOutputStream fos=new FileOutputStream("C:\\Users\\johnson\\Desktop\\sampledir\\Readme.txt");
            OutputStreamWriter osw=new OutputStreamWriter(fos);
            BufferedWriter bw=new BufferedWriter(osw);
            bw.write(file.downloadText());//返回String
            bw.close();

            System.out.println(file.downloadText());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
