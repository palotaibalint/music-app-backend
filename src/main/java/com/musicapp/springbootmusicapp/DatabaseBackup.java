package com.musicapp.springbootmusicapp;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DatabaseBackup {

    public void createDatabaseBackupNow() {
        try {
            Process process = Runtime.getRuntime().exec("pg_dump -U postgres -d music -f backup.sql");
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Database backup completed successfully.");
            } else {
                System.err.println("Database backup failed.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}


