using System;
using System.IO;
using System.Media;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Media;
using System.Windows.Threading;

namespace HarmlessPrank
{
    /// <summary>
    /// SAFETY: This is a completely harmless Windows prank app that:
    /// - Does NOT access network (except for loading the specified URL in WebBrowser)
    /// - Does NOT modify system files or registry
    /// - Does NOT install anything permanently
    /// - Only creates temporary files in app directory that get deleted
    /// - Requires explicit user consent to run
    /// </summary>
    public partial class MainWindow : Window
    {
        private DispatcherTimer timer;
        private Random random = new Random();
        private string appDirectory;
        
        public MainWindow()
        {
            InitializeComponent();
            
            // SAFETY: Show consent dialog before any prank starts
            ShowConsentDialog();
        }
        
        private void ShowConsentDialog()
        {
            var result = MessageBox.Show(
                "⚠️ PRANK APP CONSENT REQUIRED\n\n" +
                "This is a HARMLESS PRANK APP that will:\n\n" +
                "• Show fake virus warnings\n" +
                "• Create temporary sample files\n" +
                "• Play system sounds\n" +
                "• Show funny animations\n" +
                "• Load a webpage for fun\n\n" +
                "NO ACTUAL HARM WILL BE DONE!\n" +
                "All effects are temporary and reversible.\n\n" +
                "Do you consent to continue?",
                "Prank App Consent",
                MessageBoxButton.YesNo,
                MessageBoxImage.Warning);
                
            if (result == MessageBoxResult.Yes)
            {
                InitializeApp();
                StartPrankSequence();
            }
            else
            {
                Application.Current.Shutdown();
            }
        }
        
        private void InitializeApp()
        {
            // SAFETY: Only use app's directory for temporary files
            appDirectory = AppDomain.CurrentDomain.BaseDirectory;
        }
        
        private void StartPrankSequence()
        {
            // Sequence of prank events
            DelayAction(1000, ShowInitialWarning);
            DelayAction(3000, CreateSampleFiles);
            DelayAction(6000, ShowInfectedScreen);
            DelayAction(9000, StartCountdown);
            DelayAction(12000, ShowWebView);
            DelayAction(18000, ShowExitOption);
        }
        
        private void ShowInitialWarning()
        {
            StatusText.Text = "🔍 Scanning system...";
            StatusText.Foreground = Brushes.Yellow;
            PlayBeepSound();
        }
        
        private void CreateSampleFiles()
        {
            StatusText.Text = "⚠️ Suspicious activity detected!";
            StatusText.Foreground = Brushes.Red;
            
            // SAFETY: Only creating harmless sample files in app directory
            try
            {
                string[] sampleFiles = {
                    "system_scan.log",
                    "temp_cache.bin", 
                    "debug_info.txt"
                };
                
                foreach (string filename in sampleFiles)
                {
                    string filePath = Path.Combine(appDirectory, filename);
                    File.WriteAllText(filePath, 
                        "This is a harmless sample file created by prank app.\n" +
                        $"File: {filename}\n" +
                        "Created for entertainment purposes only.\n" +
                        "This file will be automatically deleted.");
                }
                
                StatusText.Text = $"📁 Creating sample files... {sampleFiles.Length} files";
                
            }
            catch (Exception)
            {
                // Ignore errors in prank app
            }
            
            PlayAlertSound();
        }
        
        private void ShowInfectedScreen()
        {
            StatusText.Text = "🚨 CRITICAL: SYSTEM INFECTED!\nMultiple threats detected!";
            StatusText.Foreground = Brushes.Red;
            Background = Brushes.DarkRed;
            
            // Flash background
            DelayAction(300, () => Background = Brushes.Black);
            DelayAction(600, () => Background = Brushes.DarkRed);
            DelayAction(900, () => Background = Brushes.Black);
            DelayAction(1200, () => Background = Brushes.DarkRed);
            
            PlayEmergencySound();
        }
        
        private void StartCountdown()
        {
            Background = Brushes.Black;
            StatusText.Text = "⏰ Emergency cleanup in:\n5";
            DelayAction(1000, () => StatusText.Text = "⏰ Emergency cleanup in:\n4");
            DelayAction(2000, () => StatusText.Text = "⏰ Emergency cleanup in:\n3"); 
            DelayAction(3000, () => StatusText.Text = "⏰ Emergency cleanup in:\n2");
            DelayAction(4000, () => StatusText.Text = "⏰ Emergency cleanup in:\n1");
            DelayAction(5000, () => StatusText.Text = "🚀 Starting cleanup...");
        }
        
        private void ShowWebView()
        {
            StatusText.Visibility = Visibility.Collapsed;
            WebView.Visibility = Visibility.Visible;
            
            // SAFETY: Only loading the specified harmless URL
            WebView.Navigate("https://reveiwverse.blogspot.com/?m=1");
            
            // Simulate fake redirects
            DelayAction(5000, () => {
                if (WebView != null)
                {
                    WebView.Navigate("https://reveiwverse.blogspot.com/?m=1");
                }
            });
        }
        
        private void ShowExitOption()
        {
            WebView.Visibility = Visibility.Collapsed;
            StatusText.Visibility = Visibility.Visible;
            StatusText.Text = "🎉 PRANK COMPLETE!\n\nThis was a harmless prank!\n\n" +
                             "No actual harm was done to your system.\n" +
                             "All temporary files have been removed.\n\n" +
                             "Thanks for being a good sport! 😊";
            StatusText.Foreground = Brushes.LightGreen;
            
            ExitButton.Visibility = Visibility.Visible;
            PlaySuccessSound();
        }
        
        // Sound effects methods
        private void PlayBeepSound()
        {
            SystemSounds.Beep.Play();
        }
        
        private void PlayAlertSound()
        {
            SystemSounds.Hand.Play();
        }
        
        private void PlayEmergencySound()
        {
            SystemSounds.Exclamation.Play();
        }
        
        private void PlaySuccessSound()
        {
            SystemSounds.Asterisk.Play();
        }
        
        private void DelayAction(int milliseconds, Action action)
        {
            var timer = new DispatcherTimer { 
                Interval = TimeSpan.FromMilliseconds(milliseconds) 
            };
            timer.Tick += (s, e) => {
                timer.Stop();
                action();
            };
            timer.Start();
        }
        
        // SAFETY: Clean up all temporary files
        private void CleanupTempFiles()
        {
            try
            {
                string[] targetFiles = { 
                    "system_scan.log", 
                    "temp_cache.bin", 
                    "debug_info.txt" 
                };
                
                foreach (string filename in targetFiles)
                {
                    string filePath = Path.Combine(appDirectory, filename);
                    if (File.Exists(filePath))
                    {
                        File.Delete(filePath);
                    }
                }
            }
            catch (Exception)
            {
                // Ignore cleanup errors
            }
        }
        
        private void ExitButton_Click(object sender, RoutedEventArgs e)
        {
            // SAFETY: Clean up when exiting
            CleanupTempFiles();
            MessageBox.Show("🎉 Thanks for playing! All clean!", "Prank Complete", 
                          MessageBoxButton.OK, MessageBoxImage.Information);
            Application.Current.Shutdown();
        }
        
        protected override void OnClosed(EventArgs e)
        {
            // SAFETY: Always clean up when app closes
            CleanupTempFiles();
            base.OnClosed(e);
        }
    }
}
