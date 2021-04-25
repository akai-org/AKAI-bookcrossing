package pl.akai.bookcrossing.ebook;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import pl.akai.bookcrossing.model.Ebook;

import java.io.IOException;
import java.util.*;

@Slf4j
@AllArgsConstructor
public class GoogleDriveService {

    private final Drive drive;
    private final String dataSource;
    private final EbookDaoMapper ebookDao;

    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void synchronizedWithGoogleDrive() {
        log.info("Starting synchronizing database with Google Drive");
        synchronizeWithGoogleDrive();
        log.info("Database was synchronized with Google Drive");
    }

    private void synchronizeWithGoogleDrive() {
        Set<String> googleIdsInDatabase = ebookDao.getGoogleIds();
        Queue<String> googleFoldersToProcess = new LinkedList<>(Collections.singletonList(dataSource));
        while (!googleFoldersToProcess.isEmpty()) {
            String googleFolderId = googleFoldersToProcess.poll();
            List<String> newFolders = processGoogleFolder(googleFolderId, googleIdsInDatabase);
            googleFoldersToProcess.addAll(newFolders);
        }
    }

    private List<String> processGoogleFolder(String googleFolders, Set<String> googleIds) {
        String pageToken = null;
        List<String> newFolders = new LinkedList<>();
        do {
            Optional<FileList> requestOptional = getFileListFromGoogleDrive(googleFolders, pageToken);
            List<File> files = requestOptional.map(FileList::getFiles).orElse(Collections.emptyList());
            for (File file : files) {
                if (isFolder(file)) {
                    newFolders.add(file.getId());
                } else if (isFileInDatabase(googleIds, file)) {
                    Ebook ebook = Ebook.builder()
                            .googleId(file.getId())
                            .title(file.getName())
                            .url(file.getWebViewLink())
                            .build();
                    ebookDao.insertEbook(ebook);
                }
            }
            pageToken = requestOptional.map(FileList::getNextPageToken).orElse(null);
        } while (pageToken != null);
        return newFolders;
    }

    private Optional<FileList> getFileListFromGoogleDrive(String googleFolder, String pageToken) {
        FileList request = null;
        try {
            request = drive.files()
                    .list()
                    .setQ(String.format("'%s' in parents", googleFolder))
                    .setIncludeTeamDriveItems(true)
                    .setSupportsTeamDrives(true)
                    .setPageToken(pageToken)
                    .setFields("nextPageToken, files(id, name, webViewLink, mimeType)")
                    .execute();
        } catch (IOException e) {
            log.error("Unable to connect with Google Drive");
            e.printStackTrace();
        }
        return Optional.ofNullable(request);
    }

    private boolean isFolder(File file) {
        return file.getMimeType().equals("application/vnd.google-apps.folder");
    }

    private boolean isFileInDatabase(Set<String> googleIds, File file) {
        return !googleIds.contains(file.getId());
    }
}
 
