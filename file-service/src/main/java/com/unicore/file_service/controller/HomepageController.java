package com.unicore.file_service.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import com.unicore.file_service.dto.FileItemDTO;
import com.unicore.file_service.dto.MessageDTO;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/home")
public class HomepageController {
    private static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);

    @Value("${google.oauth.callback.uri}")
    private  String CALLBACK_URI;

    @Value("${google.secret.key.path}")
    private Resource gdSecretKeys;

    @Value("${google.credentials.folder.path}")
    private Resource credentialsFolder;

    private String getAuthenticatedUsername() {
        // return SecurityContextHolder.getContext().getAuthentication().getName();
        return "hello";
    }

    private GoogleAuthorizationCodeFlow flow;

    @PostConstruct
    public void init() throws IOException {
        GoogleClientSecrets secrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(gdSecretKeys.getInputStream()));
        flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, secrets, SCOPES)
            .setDataStoreFactory(new FileDataStoreFactory(credentialsFolder.getFile())).build();
    }

    @GetMapping
    public String homepage() throws IOException {
        boolean isUserAuthenticated = false;

        Credential credential = flow.loadCredential(getAuthenticatedUsername());
        if (credential != null) {
            boolean tokenValid = credential.refreshToken();
            if (tokenValid) {
                isUserAuthenticated = true;
            }
        }

        return isUserAuthenticated ? "dashboard.html" : "index.html";
    }
    

    @PostMapping("/clearTokens")
    public @ResponseBody String clearTokens() throws IOException {
        java.io.File credentialsDir = credentialsFolder.getFile(); // Get the credentials folder

        if (credentialsDir.exists() && credentialsDir.isDirectory()) {
            for (java.io.File file : credentialsDir.listFiles()) {
                file.delete(); // Delete each file inside the credentials folder
            }
            return "All OAuth tokens have been cleared.";
        }

        return "No tokens found to clear.";
    }

    @GetMapping("/googlesignin")
    public @ResponseBody String googleSignin(String email) throws IOException {
        GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
        return url.setRedirectUri(CALLBACK_URI)
            .setAccessType("offline")
            .build();
    }
    
    @GetMapping("/auth")
    public @ResponseBody MessageDTO saveAuthorizationCode(HttpServletRequest request) throws IOException {
        String code = request.getParameter("code");
        if (code != null && !code.isEmpty()) {
            try {
                saveToken(code); // Exchange code for tokens and store them
                return new MessageDTO("Authorization successful. Token saved.");
            } catch (Exception e) {
                return new MessageDTO("Authorization failed. " + e.getMessage());
            }
        }
        return new MessageDTO("Authorization failed. Missing or invalid code.");
    }

    private void saveToken(String code) throws IOException {
        GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(CALLBACK_URI).execute();
        flow.createAndStoreCredential(response, getAuthenticatedUsername());
    }
    
    @PostMapping("/create")
    public @ResponseBody List<MessageDTO> createFiles(@RequestParam("files") MultipartFile[] files) throws IOException {
        Credential cred = flow.loadCredential(getAuthenticatedUsername());

        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
            .setApplicationName("Unicore")
            .build();

        List<MessageDTO> responses = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue; // Skip empty files

            File driveFile = new File();
            driveFile.setName(file.getOriginalFilename());

            java.io.File tempFile = java.io.File.createTempFile("upload-", file.getOriginalFilename());
            file.transferTo(tempFile);

            FileContent fileContent = new FileContent(file.getContentType(), tempFile);
            File uploadedFile = drive.files().create(driveFile, fileContent).setFields("id").execute();

            tempFile.delete(); // Clean up temp file

            responses.add(new MessageDTO(String.format("File uploaded successfully with ID: %s", uploadedFile.getId())));
        }

        return responses;
}

    
    @GetMapping(value={"/listfiles"}, produces={"application/json"})
    public @ResponseBody List<FileItemDTO> listFiles() throws IOException {
        Credential cred = flow.loadCredential(getAuthenticatedUsername());

        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
            .setApplicationName("Unicore")
            .build();

        List<FileItemDTO> responseList = new ArrayList<>();
        FileList fileList = drive.files().list().setFields("files(id,name,thumbnailLink)").execute();

        for (File file : fileList.getFiles()) {
            FileItemDTO item = FileItemDTO.builder()
                .id(file.getId())
                .name(file.getName())
                .thumbnailLink(file.getThumbnailLink())
                .build();
            
            responseList.add(item);
        }

        return responseList;
    }
    
    @DeleteMapping("/deletefile/{fileId}")
    public @ResponseBody MessageDTO deleteFile(@PathVariable String fileId) throws IOException {
        Credential cred = flow.loadCredential(getAuthenticatedUsername());

        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
            .setApplicationName("Unicore")
            .build();
        
        drive.files().delete(fileId).execute();

        return new MessageDTO("File " + fileId + " has been deleted.");
    }

    @GetMapping("/createfolder/{folderName}")
    public @ResponseBody MessageDTO createFolder(@PathVariable String folderName) throws IOException {
        Credential cred = flow.loadCredential(getAuthenticatedUsername());

        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
            .setApplicationName("Unicore")
            .build();

        File file = new File();
        file.setName(folderName);
        file.setMimeType("application/vnd.google-apps.folder");

        drive.files().create(file).execute();

        return new MessageDTO("Folder has been created successfully.");
    }
    
    
    @PostMapping("/uploadinfolder")
    public @ResponseBody MessageDTO uploadInFolder(@RequestParam("file") MultipartFile file,
                                                @RequestParam("folderId") String folderId) throws IOException {
        Credential cred = flow.loadCredential(getAuthenticatedUsername());

        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
            .setApplicationName("Unicore")
            .build();

        File driveFile = new File();
        driveFile.setName(file.getOriginalFilename());
        driveFile.setParents(Collections.singletonList(folderId));

        java.io.File tempFile = java.io.File.createTempFile("upload-", file.getOriginalFilename());
        file.transferTo(tempFile);

        FileContent fileContent = new FileContent(file.getContentType(), tempFile);
        File uploadedFile = drive.files().create(driveFile, fileContent).setFields("id").execute();

        tempFile.delete();

        makePublic(uploadedFile.getId());

        return new MessageDTO(String.format("File uploaded to folder successfully with ID: %s", uploadedFile.getId()));
    }

    @PostMapping(value={"/makepublic/{fileid}"}, produces={"application/json"})
    public MessageDTO makePublic(@PathVariable String fileid) throws IOException {
        Credential cred = flow.loadCredential(getAuthenticatedUsername());

        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
            .setApplicationName("Unicore")
            .build();
        
        Permission permission = new Permission();
        permission.setType("anyone");
        permission.setRole("reader");

        drive.permissions().create(fileid, permission).execute();

        return new MessageDTO("Public file successfully.");
    }
    
}
