package pn.app_wish;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.slf4j.simple.SimpleLogger;
import pn.app_wish.constant.GUIConstants;
import pn.cg.app_system.AppSystem;
import pn.cg.app_system.code_generation.model.CompilationJob;
import pn.cg.datastorage.DataStorage;
import pn.cg.datastorage.ThreadPoolMaster;


import java.io.IOException;
import java.util.Objects;

//TODO: Add Strings to XML or Constant Class
public class AppWish extends Application {
    @Override
    public void init() throws Exception {

 super.init();

    }

    @Override
    public void stop() throws Exception {
         System.out.println("Exiting app");
        ThreadPoolMaster.getInstance().TerminateThreads();
        Platform.exit();
        super.stop();

    }



    @FXML
    public TextField tf_input;

    @FXML
    public Label output_label;

    @FXML
    public Button btn_create_application;

    @FXML
    public Button btn_run_application;

    @FXML
    public BorderPane bp_main;
    private String javaExecutablePath;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getClassLoader().getResource(GUIConstants.DEFAULT_FXML_FILE)));


        primaryStage.setTitle(GUIConstants.DEFAULT_STAGE_TITLE);


        Scene scene = new Scene(root);


  
        primaryStage.setScene(scene);

        primaryStage.show();
 
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");


    }

    public void onAppWish(ActionEvent ae) {
        
        
        DataStorage.getInstance().setCompilationJob(new CompilationJob(GUIConstants.DEFAULT_STAGE_TITLE));
        btn_run_application.setVisible(false);
        output_label.setText("Generating code...");
       
        ThreadPoolMaster.getInstance().getExecutor().execute(new Runnable() {
            @Override
                public void run() {
                 
               
      
        if (tf_input != null) {


 
                    AppSystem.StartCodeGenerator(tf_input.getText());
                    while(DataStorage.getInstance().getCompilationJob().isResult() == false){}
                    if (DataStorage.getInstance().getCompilationJob().isResult()) {
                    
                        javaExecutablePath =DataStorage.getInstance().getJavaExecutionPath();
                    
                      
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if (DataStorage.getInstance().getJavaExecutionPath() != null) {
                        output_label.setText("Completed Successfully");
                
                        btn_run_application.setVisible(true);
                    
                    }
                    else {
                        output_label.setText("Error");
                    }
                    
                }
            });

                    } 
                } }
            });


         




 }
        
    

    public void onRunJavaApp(ActionEvent ae){


        if (javaExecutablePath != null) {

            System.out.println("Executing java app on path -> "+javaExecutablePath);
            try {
                new ProcessBuilder("/bin/bash", "-c","java "+javaExecutablePath).inheritIO().start().waitFor();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException while starting Java executable");
                throw new RuntimeException(e);
            } catch (IOException e) {
                System.out.println("RuntimeException while starting Java executable");
                throw new RuntimeException(e);
            }


        }
    }



}
