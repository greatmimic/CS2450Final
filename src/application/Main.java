package application;

import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;


//@@@@TODO@@@@
//Create self scrolling news highlight bar with main, entertainment, sports, finance buttons
//create tabs for entertainment, sports, auto, finance, food, fashion
//create fake ad banner in between search bar and news grid
//create a chart simulation of billboard top 100 somewhere
//create a single area weather forecast (one city)
//get proper icons for nav buttons
//create login interface

public class Main extends Application {

	private Image loadImage(String imagePath) {
	    try {
	        InputStream imageStream = getClass().getResourceAsStream(imagePath);
	        if (imageStream == null) {
	            throw new FileNotFoundException("Resource not found: " + imagePath);
	        }
	        return new Image(imageStream);
	    } catch (FileNotFoundException e) {
	        System.err.println(e.getMessage());
	        return null;
	    }
	}

	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		
		// Header section with Yahoo logo and search bar
		HBox header = new HBox();
		header.setPadding(new Insets(20, 0, 0, 0));
		header.setAlignment(Pos.CENTER);
		header.setSpacing(0);

		ImageView yahooLogo = new ImageView(loadImage("/Images/yahoo_icon.png"));
		yahooLogo.setFitWidth(30);
		yahooLogo.setFitHeight(30);
		Label yahooText = new Label("Yahoo");
		yahooText.getStyleClass().add("yahoo-text");

		TextField searchBar = new TextField();
		searchBar.setPrefWidth(600);
		searchBar.setPrefHeight(30);
		searchBar.setPromptText("Search for news, stocks, and more");
		searchBar.setFocusTraversable(false); // remove default focus
		searchBar.setOnMouseClicked(event -> {
			searchBar.requestFocus();
		});


		//search button
		ImageView searchIcon = new ImageView(loadImage("/Images/search_icon.png"));
		searchIcon.setFitWidth(20);
		searchIcon.setFitHeight(20);
		Button searchButton = new Button("", searchIcon);
		searchButton.setPrefHeight(16);
		searchButton.setPrefWidth(16);
		searchButton.getStyleClass().add("search-button");


		//search box
		HBox searchBox = new HBox(searchBar, searchButton);
		searchBox.setAlignment(Pos.CENTER);
		searchBox.getStyleClass().add("search-box");
		HBox.setMargin(searchBox, new Insets(0, 0, 0, 10)); // Add left margin of 10 pixels

		header.getChildren().addAll(yahooLogo, yahooText, searchBox);
		


		//tab pane entertainment, sports, auto, finance, recipes, fashion, 
		//TabPane newsPane = new TabPane();



		//news companies
		String[][] newsCompanies = {
				{"CNN", "/Images/cnn_icon.png"},
				{"BBC", "/Images/bbc_icon.png"},
				//{"FOX", "./Images/fox_icon.png"}, //need fox news icon
				{"Aol.","/Images/aol_icon.png"} 

		};

		//table of news grid pane
		GridPane newsGrid = new GridPane();
		newsGrid.setHgap(20);
		newsGrid.setVgap(20);
		newsGrid.setAlignment(Pos.CENTER);

		// Step 3: Iterate through the news companies and create a VBox for each
		for (int i = 0; i < newsCompanies.length; i++) {
			
			String currentNewsCompany = newsCompanies[i][0];
			ImageView logo = new ImageView(loadImage(newsCompanies[i][1]));
			logo.setFitWidth(50);
			logo.setFitHeight(50);

			Button logoButton = new Button("", logo);
			logoButton.getStyleClass().add("logo-button");
			
			

		    VBox newsCompanyBox = new VBox(logoButton); // Use logoButton instead of logo
		    newsCompanyBox.setAlignment(Pos.CENTER);

		    // Add this code snippet inside the loop where you create the logoButton
		    logoButton.setOnAction(event -> {
		        System.out.println("Clicked on " + currentNewsCompany); // Use the local variable here
		        // Add your desired action here
		    });

			// Step 4: Add each VBox to the GridPane
			newsGrid.add(newsCompanyBox, i % 3, i / 3); // Change '3' to the desired number of columns
		}


		
		// Navigation bar section with buttons
		HBox navBox = new HBox();
		navBox.setPadding(new Insets(10, 0, 10, 0));
		navBox.setAlignment(Pos.CENTER);
		navBox.setSpacing(20);

		Button mailButton = new Button("Mail");
		mailButton.getStyleClass().add("nav-button");
		Button newsButton = new Button("News");
		newsButton.getStyleClass().add("nav-button");
		Button sportsButton = new Button("Sports");
		sportsButton.getStyleClass().add("nav-button");
		Button financeButton = new Button("Finance");
		financeButton.getStyleClass().add("nav-button");
		Button entertainmentButton = new Button("Entertainment");
		entertainmentButton.getStyleClass().add("nav-button");

		navBox.getChildren().addAll(mailButton, newsButton, sportsButton, financeButton, entertainmentButton);

		VBox topSection = new VBox();
		topSection.getChildren().addAll(header, navBox);
		
		root.setTop(topSection);
		root.setCenter(newsGrid);
		


		// Handle mouse click events on the BorderPane to remove focus from search bar
		root.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> {
			searchBar.getParent().requestFocus(); // Remove focus from search bar
		});
		





		Scene scene = new Scene(root, 1200, 800);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

		primaryStage.setTitle("Yahoo");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
