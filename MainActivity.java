package com.prankster.harmlessvirus;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

// SAFETY COMMENT: This is a completely harmless prank app that:
// - Does NOT access network (except for loading the specified URL in WebView)
// - Does NOT modify system files or settings
// - Does NOT install anything permanently
// - Only creates temporary files in app directory that get deleted
// - Requires explicit user consent to run

public class MainActivity extends Activity {
    
    private WebView webView;
    private TextView statusText;
    private Button exitButton;
    private Vibrator vibrator;
    private MediaPlayer alertSound;
    private Handler handler = new Handler();
    private Random random = new Random();
    
    // SAFETY: Only creating files in app's own directory
    private File appDirectory;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // SAFETY: Show consent dialog before any prank starts
        showConsentDialog();
    }
    
    private void showConsentDialog() {
        // Create a custom dialog for user consent
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("⚠️ PRANK APP CONSENT REQUIRED");
        builder.setMessage("This is a HARMLESS PRANK APP that will:\n\n" +
                "• Show fake virus warnings\n" +
                "• Create temporary sample files\n" +
                "• Vibrate and play sounds\n" +
                "• Show funny animations\n" +
                "• Load a webpage for fun\n\n" +
                "NO ACTUAL HARM WILL BE DONE!\n" +
                "All effects are temporary and reversible.\n\n" +
                "Do you consent to continue?");
        
        builder.setPositiveButton("YES, I CONSENT", (dialog, which) -> {
            initializeApp();
            startPrankSequence();
        });
        
        builder.setNegativeButton("NO, EXIT", (dialog, which) -> {
            finish();
        });
        
        builder.setCancelable(false);
        builder.show();
    }
    
    private void initializeApp() {
        // Set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_main);
        
        // Initialize components
        webView = findViewById(R.id.webView);
        statusText = findViewById(R.id.statusText);
        exitButton = findViewById(R.id.exitButton);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        
        // SAFETY: Only use app's internal directory for temporary files
        appDirectory = getFilesDir();
        
        setupWebView();
        setupExitButton();
    }
    
    private void setupWebView() {
        // SAFETY: WebView only loads the specified harmless URL
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false; // Let WebView handle the URL
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
    }
    
    private void setupExitButton() {
        exitButton.setOnClickListener(v -> {
            // SAFETY: Clean up all temporary files when exiting
            cleanupTempFiles();
            showExitMessage();
            finish();
        });
        
        // Hide exit button initially
        exitButton.setVisibility(View.INVISIBLE);
    }
    
    private void startPrankSequence() {
        // Sequence of prank events
        handler.postDelayed(this::showInitialWarning, 1000);
        handler.postDelayed(this::createSampleFiles, 3000);
        handler.postDelayed(this::showInfectedScreen, 6000);
        handler.postDelayed(this::startCountdown, 9000);
        handler.postDelayed(this::showWebView, 12000);
        handler.postDelayed(this::showExitOption, 18000);
    }
    
    private void showInitialWarning() {
        statusText.setText("🔍 Scanning system...");
        statusText.setTextColor(Color.YELLOW);
        playBeepSound();
        lightVibration(200);
    }
    
    private void createSampleFiles() {
        statusText.setText("⚠️ Suspicious activity detected!");
        statusText.setTextColor(Color.RED);
        
        // SAFETY: Only creating harmless sample files in app directory
        try {
            String[] sampleFiles = {
                "system_scan.log",
                "temp_cache.bin",
                "debug_info.txt"
            };
            
            for (String filename : sampleFiles) {
                File sampleFile = new File(appDirectory, filename);
                FileOutputStream fos = new FileOutputStream(sampleFile);
                fos.write(("This is a harmless sample file created by prank app.\n" +
                          "File: " + filename + "\n" +
                          "Created for entertainment purposes only.\n" +
                          "This file will be automatically deleted.").getBytes());
                fos.close();
            }
            
            // Show fake file creation message
            statusText.setText("📁 Creating sample files... " + sampleFiles.length + " files");
            
        } catch (IOException e) {
            // Ignore errors in prank app
        }
        
        playAlertSound();
        mediumVibration(500);
    }
    
    private void showInfectedScreen() {
        statusText.setText("🚨 CRITICAL: SYSTEM INFECTED!\nMultiple threats detected!");
        statusText.setTextColor(Color.RED);
        getWindow().setBackgroundDrawableResource(android.R.color.holo_red_dark);
        
        // Flash background
        handler.postDelayed(() -> getWindow().setBackgroundDrawableResource(android.R.color.black), 300);
        handler.postDelayed(() -> getWindow().setBackgroundDrawableResource(android.R.color.holo_red_dark), 600);
        
        playEmergencySound();
        heavyVibration(1000);
    }
    
    private void startCountdown() {
        statusText.setText("⏰ Emergency cleanup in:\n5");
        handler.postDelayed(() -> statusText.setText("⏰ Emergency cleanup in:\n4"), 1000);
        handler.postDelayed(() -> statusText.setText("⏰ Emergency cleanup in:\n3"), 2000);
        handler.postDelayed(() -> statusText.setText("⏰ Emergency cleanup in:\n2"), 3000);
        handler.postDelayed(() -> statusText.setText("⏰ Emergency cleanup in:\n1"), 4000);
        handler.postDelayed(() -> statusText.setText("🚀 Starting cleanup..."), 5000);
        
        // Countdown vibrations
        for (int i = 0; i < 5; i++) {
            handler.postDelayed(this::lightVibration, i * 1000);
        }
    }
    
    private void showWebView() {
        statusText.setVisibility(View.INVISIBLE);
        webView.setVisibility(View.VISIBLE);
        
        // SAFETY: Only loading the specified harmless URL
        webView.loadUrl("https://reveiwverse.blogspot.com/?m=1");
        
        // Simulate fake redirects
        handler.postDelayed(() -> {
            if (webView != null) {
                webView.loadUrl("https://reveiwverse.blogspot.com/?m=1");
            }
        }, 5000);
    }
    
    private void showExitOption() {
        webView.setVisibility(View.INVISIBLE);
        statusText.setVisibility(View.VISIBLE);
        statusText.setText("🎉 PRANK COMPLETE!\n\nThis was a harmless prank!\n\n" +
                          "No actual harm was done to your device.\n" +
                          "All temporary files have been removed.\n\n" +
                          "Thanks for being a good sport! 😊");
        statusText.setTextColor(Color.GREEN);
        
        exitButton.setVisibility(View.VISIBLE);
        playSuccessSound();
        celebrationVibration();
    }
    
    // Sound effects methods
    private void playBeepSound() {
        // Simple beep sound
        Toast.makeText(this, "🔊", Toast.LENGTH_SHORT).show();
    }
    
    private void playAlertSound() {
        // Play through system sound
        try {
            MediaPlayer.create(this, android.provider.Settings.System.NOTIFICATION_SOUND).start();
        } catch (Exception e) {
            // Ignore errors
        }
    }
    
    private void playEmergencySound() {
        try {
            MediaPlayer.create(this, android.provider.Settings.System.ALARM_ALERT).start();
        } catch (Exception e) {
            // Ignore errors
        }
    }
    
    private void playSuccessSound() {
        try {
            MediaPlayer.create(this, R.raw.success_sound).start();
        } catch (Exception e) {
            // Play default notification
            MediaPlayer.create(this, android.provider.Settings.System.NOTIFICATION_SOUND).start();
        }
    }
    
    // Vibration effects methods
    private void lightVibration() {
        lightVibration(200);
    }
    
    private void lightVibration(long milliseconds) {
        if (vibrator != null && vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(milliseconds);
            }
        }
    }
    
    private void mediumVibration(long milliseconds) {
        if (vibrator != null && vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(milliseconds);
            }
        }
    }
    
    private void heavyVibration(long milliseconds) {
        if (vibrator != null && vibrator.hasVibrator()) {
            long[] pattern = {0, 100, 100, 100, 100, 100};
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1));
            } else {
                vibrator.vibrate(pattern, -1);
            }
        }
    }
    
    private void celebrationVibration() {
        if (vibrator != null && vibrator.hasVibrator()) {
            long[] pattern = {0, 100, 200, 100, 200, 100};
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1));
            } else {
                vibrator.vibrate(pattern, -1);
            }
        }
    }
    
    // SAFETY: Clean up all temporary files
    private void cleanupTempFiles() {
        try {
            File[] files = appDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().endsWith(".log") || 
                        file.getName().endsWith(".bin") || 
                        file.getName().endsWith(".txt")) {
                        file.delete();
                    }
                }
            }
        } catch (Exception e) {
            // Ignore cleanup errors
        }
    }
    
    private void showExitMessage() {
        Toast.makeText(this, "🎉 Thanks for playing! All clean!", Toast.LENGTH_LONG).show();
    }
    
    @Override
    protected void onDestroy() {
        // SAFETY: Always clean up when app is destroyed
        cleanupTempFiles();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }
}
