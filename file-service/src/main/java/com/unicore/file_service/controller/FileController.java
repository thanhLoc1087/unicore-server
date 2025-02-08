package com.unicore.file_service.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
import com.google.api.services.drive.model.Permission;
import com.unicore.file_service.dto.ApiResponse;
import com.unicore.file_service.dto.AuthResponse;
import com.unicore.file_service.dto.FileItemDTO;
import com.unicore.file_service.dto.MessageDTO;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class FileController {
    private static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);

    @Value("${google.oauth.callback.uri}")
    private  String CALLBACK_URI;

    @Value("${google.secret.key.path}")
    private Resource gdSecretKeys;

    @Value("${google.credentials.folder.path}")
    private Resource credentialsFolder;

    private final String folderName = "Unicore";

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

    @GetMapping("/check-auth/{userEmail}")
    public ApiResponse<AuthResponse> checkAuth(@PathVariable String userEmail)  
    throws IOException {
        boolean isUserAuthenticated = false;

        Credential credential = flow.loadCredential(userEmail);
        if (credential != null) {
            boolean tokenValid = credential.refreshToken();
            if (tokenValid) {
                isUserAuthenticated = true;
            }
        }

        return ApiResponse.<AuthResponse>builder()
            .data(new AuthResponse(
                isUserAuthenticated, 
                isUserAuthenticated ? null :
                    googleSigninUrl(userEmail)
            ))
            .message(isUserAuthenticated ? "User is authed" : "User is not authed")
            .status(HttpStatus.OK.value())
            .time(LocalDateTime.now())
            .build();

    }
    

    @PostMapping("/clearTokens")
    public String clearTokens() throws IOException {
        java.io.File credentialsDir = credentialsFolder.getFile(); // Get the credentials folder

        if (credentialsDir.exists() && credentialsDir.isDirectory()) {
            for (java.io.File file : credentialsDir.listFiles()) {
                file.delete(); // Delete each file inside the credentials folder
            }
            return "All OAuth tokens have been cleared.";
        }

        return "No tokens found to clear.";
    }

    private String googleSigninUrl(String email) {
        GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
        
        return url.setRedirectUri(CALLBACK_URI)
            .setAccessType("offline")
            .setApprovalPrompt("force")
            .setState(Base64.getEncoder().encodeToString(email.getBytes()))
            .build();
    }
    
    @GetMapping("/auth")
    public ApiResponse<String> saveAuthorizationCode(HttpServletRequest request) throws IOException {
        String code = request.getParameter("code");
        String state = request.getParameter("state"); // Encoded email
        String userEmail = new String(Base64.getDecoder().decode(state)); // Decode email
        log.info("User code: " + code);
        log.info("User email: " + userEmail);
        if (code != null && !code.isEmpty()) {
            try {
                saveToken(code, userEmail); // Exchange code for tokens and store them
                return ApiResponse.<String>builder()
                    .data("Authorization successful. Token saved.")
                    .status(HttpStatus.OK.value())
                    .time(LocalDateTime.now())
                    .build();
            } catch (Exception e) {
                return ApiResponse.<String>builder()
                    .data("Authorization failed. " + e.getMessage())
                    .status(HttpStatus.BAD_REQUEST.value())
                    .time(LocalDateTime.now())
                    .build();
            }
        }
        return ApiResponse.<String>builder()
            .data("Authorization failed. Missing or invalid code.")
            .status(HttpStatus.BAD_REQUEST.value())
            .time(LocalDateTime.now())
            .build();
    }

    private void saveToken(String code, String userEmail) throws IOException {
        GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(CALLBACK_URI).execute();
        flow.createAndStoreCredential(response, userEmail);
    }
    
    @PostMapping("/save")
    public List<FileItemDTO> createFiles(
        @RequestParam("files") MultipartFile[] files,
        @RequestParam("userEmail") String userEmail
    ) throws IOException {
        Credential cred = flow.loadCredential(userEmail);

        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
            .setApplicationName("Unicore")
            .build();

        List<FileItemDTO> responses = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue; // Skip empty files

            File driveFile = new File();
            driveFile.setName(file.getOriginalFilename());

            java.io.File tempFile = java.io.File.createTempFile("upload-", file.getOriginalFilename());
            file.transferTo(tempFile);

            FileContent fileContent = new FileContent(file.getContentType(), tempFile);
            File uploadedFile = drive.files().create(driveFile, fileContent).setFields("id, name, webContentLink, webViewLink, thumbnailLink").execute();

            tempFile.delete(); // Clean up temp file

            makePublic(uploadedFile.getId());

            responses.add(new FileItemDTO(uploadedFile));
        }

        return responses;
    }
    
    @DeleteMapping("/deletefile/{fileId}")
    public MessageDTO deleteFile(@PathVariable String fileId) throws IOException {
        Credential cred = flow.loadCredential(getAuthenticatedUsername());

        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
            .setApplicationName("Unicore")
            .build();
        
        drive.files().delete(fileId).execute();

        return new MessageDTO("File " + fileId + " has been deleted.");
    }

    private String createFolder(String userEmail) throws IOException {
        Credential cred = flow.loadCredential(userEmail);

        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
            .setApplicationName("Unicore")
            .build();

        File file = new File();
        file.setName(folderName);
        file.setMimeType("application/vnd.google-apps.folder");

        File createdFolder = drive.files().create(file).execute();

        return createdFolder.getId();
    }
    
    
    @PostMapping("/uploadinfolder")
    private FileItemDTO uploadInFolder(@RequestParam("file") MultipartFile file,
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

        return new FileItemDTO(uploadedFile);
    }

    private void makePublic(String fileid) throws IOException {
        Credential cred = flow.loadCredential(getAuthenticatedUsername());

        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
            .setApplicationName("Unicore")
            .build();
        
        Permission permission = new Permission();
        permission.setType("anyone");
        permission.setRole("reader");

        drive.permissions().create(fileid, permission).execute();
    }
    

}
